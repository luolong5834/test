package httpclient.test;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/5/31
 */

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DownloadFileController {


    // private String url = "http://localhost:8080/download/downloadPdf?filePath=d:/sample.pdf";
    private String url = "http://localhost:8080/download/downloadPdf?filePath=d:/sample.pdf";


    @Test
    public void test() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();) {
            HttpGet httpGet = new HttpGet();
            BasicHeader basicHeader = new BasicHeader("content_type", "application/pdf");
            // BasicHeader basicHeader1 = new BasicHeader("accept", "application/pdf;charset=gbk");
            // httpGet.setHeader(basicHeader);
            //httpGet.setHeader(basicHeader1);
            BufferedInputStream bin = null;
            FileWriter fw = null;
            FileOutputStream fo = null;
            httpGet.setURI(new URI(url));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int httpStatus = response.getStatusLine().getStatusCode();
            if (200 != httpStatus) {
                throw new Exception("非200状态");
            }
            fo = new FileOutputStream("d:/sample1.pdf");
            bin = new BufferedInputStream(response.getEntity().getContent());
            byte[] contents = new byte[1024];
            int n = 0;
            while ((n = bin.read(contents)) != -1) {
                fo.write(contents, 0, n);
            }
            fo.flush();
            fo.close();
            bin.close();
            System.out.println("成功");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        try (FileInputStream in = new FileInputStream("d:/sample.pdf"); FileOutputStream out = new FileOutputStream("d:/sample1.pdf")) {
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                out.write(b);
            }
        }
    }
}
