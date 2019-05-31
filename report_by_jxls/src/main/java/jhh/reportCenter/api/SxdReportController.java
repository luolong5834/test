package jhh.reportCenter.api;

import jhh.reportCenter.dao.BizReportSourceSxdMapper;
import jhh.reportCenter.model.BizReportSourceSxd;
import jhh.reportCenter.model.dto.BizReportSourceSxdDto;
import jhh.reportCenter.model.dto.SxdReportDto;
import jhh.reportCenter.service.SxdReportService;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * todo 后期可以吧controller逻辑写到service里面
 *
 * @author tangxd
 * @Description: TODO
 * @date 2017/11/20
 */
@Controller
@RequestMapping("/report/sxd")
@Slf4j
public class SxdReportController {
    static final String DOWNLOAD_EXCEL_PATH = "/report_sxd_template.xlsx";
    static Integer total_count = 1;
    static Integer exported_count = 0;
    static ReentrantLock lock = new ReentrantLock();
    @Autowired
    SxdReportService sxdReportService;
    @Autowired
    BizReportSourceSxdMapper bizReportSourceSxdMapper;
    private List<SxdReportDto> data = new LinkedList();
    /**
     * 线上暂时有3万左右数据
     */
    private ExecutorService pool = Executors.newFixedThreadPool(8);
    @Value("${export.report.sxd.productId}")
    @Autowired
    private String productId;

    @GetMapping("")
    public String main(Model model) {
        return "report/sxd/report_sxd";
    }

    /**
     * @param [response]
     * @return java.lang.String
     * @author long.luo
     * @date 2019/1/24
     */
    @RequestMapping("importSxdReport")
    @ResponseBody
    public String importSxdReport(HttpServletResponse response, BizReportSourceSxdDto dto) {
        log.info("开始处理导出随心贷数据请求，请求参数:{}", dto.toString());
        String result = "success";
        try {
            if (lock.isLocked()) {
                log.warn("警告：有多人同时操作导出功能!");
                return "fail:有其他人正在导出,出于性能考虑，请稍等...";
            }
            lock.lock();
            //分批次导出
            result = processByBatch(dto);
        } catch (Exception e) {
            return "fail";
        } finally {
            lock.unlock();
        }
        return result;
    }

    //
    @GetMapping("getExcel")
    public void exportBrowser(HttpServletResponse response) throws Exception {
        //指定数据生成后的文件输入流
        @Cleanup FileInputStream fileInputStream = new FileInputStream(DOWNLOAD_EXCEL_PATH);
        //导出excel文件，设置文件名
        String filename = URLEncoder.encode("随心贷风控数据.xlsx", "UTF-8");
        //设置下载头
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        @Cleanup ServletOutputStream outputStream = response.getOutputStream();
        //将文件写入浏览器
        byte[] bys = new byte[fileInputStream.available()];
        fileInputStream.read(bys);
        outputStream.write(bys);

    }

    @ResponseBody
    @GetMapping("/exportedPercent")
    public String exportedPercent() {
        String percent = "0%";
        log.info("当前导出进度{}", (percent = ((float) data.size() / total_count) * 100 + "%"));
        return percent;
    }

    private String processByBatch(BizReportSourceSxdDto dto) {
        try {
            int sizePer = 1000;//每次查询个数
            int loopFetchCount = 1;
            int restSize = 0;
            int totalCount = bizReportSourceSxdMapper.getTotalCount(dto);
            total_count = totalCount;
            log.info("开始分析数据,总共{}条数据", totalCount);
            if (totalCount == 0) {
                return "fail:没有查询到数据";
            } else {
                loopFetchCount = (totalCount % sizePer == 0 ? totalCount / sizePer : totalCount / sizePer + 1);
                restSize = totalCount % sizePer;
            }
            int offset = 0;
            List<Future<List<SxdReportDto>>> futures = new LinkedList<>();
            for (int i = 0; i < loopFetchCount; i++) {
                if (loopFetchCount - 1 == i && restSize != 0)
                    sizePer = restSize;
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                List<BizReportSourceSxd> list = bizReportSourceSxdMapper.findList(dto, offset, sizePer);
                stopWatch.stop();
                log.info("数据库获取{}条数据耗时:{}秒", sizePer, stopWatch.getTotalTimeSeconds());
                Future<List<SxdReportDto>> future = pool.submit(this.newTask(productId, list));
                futures.add(future);
                offset += sizePer;
            }
            data.clear();
            for (Future<List<SxdReportDto>> future : futures) {
                //6分钟获取不到就KO
                List<SxdReportDto> resultList = future.get(6, TimeUnit.MINUTES);
                if (resultList != null) {
                    data.addAll(resultList);
                }
            }
            exportDataToExcel();
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                log.error("线程崩溃;", e);
                return "fail:系统繁忙";
            }
            log.error("导出发生错误;", e);
            return "fail:系统繁忙";
        } finally {
            exported_count = 0;
        }
        return "success";
    }

    private Callable<List<SxdReportDto>> newTask(String productId, List<BizReportSourceSxd> list) {
        Callable<List<SxdReportDto>> callable = () -> {
            List<SxdReportDto> resultList;
            try {
                resultList = sxdReportService.findSpecialReportList(productId, list);
                return resultList;
            } catch (InterruptedException e) {
                throw new InterruptedException("一个线程中断，全部中断..");
            }
        };
        return callable;
    }

    //
    //导出
    private void exportDataToExcel() throws Exception {
        log.info("正在导出到excel ...");
        try (InputStream is = SxdReportController.class.getResourceAsStream("/excel_template/report_sxd_template.xlsx")) {
            Context context = new Context();
            context.putVar("datas", data);
            try (OutputStream os = new FileOutputStream(DOWNLOAD_EXCEL_PATH)) {
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
    }


}
