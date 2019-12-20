/*
package jhh.reportCenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jhh.reportCenter.convert.Converter;
import jhh.reportCenter.convert.ConverterFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testJson() throws IOException {
       // File file = new File("C:\\Users\\tang\\Desktop\\风控\\30技术管理\\03代码实现\\RC原始报文样本\\聚信立.json");
        File file = new File("D:\\Users\\muguangyu\\git-project\\risk_cloud\\src\\test\\java\\jhh\\risk\\cloud\\json原文\\baiQiShi_json.json");
        FileInputStream in = new FileInputStream(file);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        in.read(filecontent);
        in.close();
        String str = new String(filecontent, "UTF-8");

        JSONObject obj = JSON.parseObject(str);
        Assert.isTrue(obj != null, "ok");
        //1 聚信立测试
        */
/*JxlMapping jxl = JSON.toJavaObject((JSONObject) obj.get("report_data"), JxlMapping.class);
        Assert.isTrue(jxl != null, "ok");
        Converter converter = ConverterFactory.getConverter("jxl");
        JxlMapping mapping = (JxlMapping) converter.convert(str);*//*

        //2 同盾测试
        */
/*
        TdMapping td = JSON.toJavaObject(obj ,TdMapping.class);
        Assert.isTrue(td != null ,"ok");
        Converter converter = ConverterFactory.getConverter("td");
        TdMapping mapping = (TdMapping) converter.convert(str);*//*

        // 3 芝麻测试
        */
/*ZmMapping zm = JSON.toJavaObject(obj,ZmMapping.class);
        Assert.isTrue(zm!=null,"ok");
        Converter converter = ConverterFactory.getConverter("zm");
        ZmMapping mapping = (ZmMapping) converter.convert(str);*//*

        //4 宜信测试
        */
/*YXShareMapping yxShare = JSON.toJavaObject(obj,YXShareMapping.class);
        Assert.isTrue(yxShare != null,"ok");
        Converter converter = ConverterFactory.getConverter("yxShare");
        YXShareMapping mapping = (YXShareMapping) converter.convert(str);*//*

        //5 上海资信测试
        */
/*VCisMapping vCis = JSON.toJavaObject(obj,VCisMapping.class);
        Assert.isTrue(vCis!=null,"ok");
        Converter converter = ConverterFactory.getConverter("vCis");
        VCisMapping mapping = (VCisMapping) converter.convert(str);*//*

        // 6 白骑士测试
        BaiQiShiMapping baiQiShi = JSON.toJavaObject(obj,BaiQiShiMapping.class);
        Assert.isTrue(baiQiShi!=null,"ok");
        Converter converter = ConverterFactory.getConverter("baiQiShi");
        BaiQiShiMapping mapping = (BaiQiShiMapping) converter.convert(str);
        Assert.isTrue(mapping != null, "ok");
    }

//    @Test
//    public void test() throws InterruptedException {
//        ActorRef idxActor = actorSystem.actorOf(props.props("StoreIndex"), "searcher");
//        //ActorRef daoActor = actorSystem.actorOf(props.props("StoreSource"),"storer");
//        JxlMapping mapping = new JxlMapping();
//        idxActor.tell(mapping, ActorRef.noSender());
////        Report report = new Report();
////        report.setId("111");
////        report.setSource("xxx");
////        report.setInfo(new ReportInfo());
////        daoActor.tell(report, ActorRef.noSender());
//
//        FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
//        String str = "is_ok";
//        Future<Object> future = Patterns.ask(idxActor, str, Timeout.durationToTimeout(duration));
//        try {
//            Object obj = Await.result(future, duration);
//            Assert.isTrue(obj != null, "ok");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        Thread.sleep(10000);
//    }

//
}
*/
