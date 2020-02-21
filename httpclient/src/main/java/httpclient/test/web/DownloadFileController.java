package httpclient.test.web;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/31
 */
@Controller()
@RequestMapping("download")
public class DownloadFileController {

    @GetMapping("downloadPdf")
    public void downloadFile(String filePath, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/pdf");
        //response.setHeader("Transfer-Encoding:","b");
        //设置下载头
        //response.setHeader("Content-Disposition", "attachment;filename=" + "sample.pdf");
        try (ServletOutputStream outputStream = response.getOutputStream(); FileInputStream in = new FileInputStream(filePath);) {
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = in.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
        }
    }

    @Retryable
    public void test() {
        System.out.println("luolong");

    }
}
