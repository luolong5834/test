package com.luolong.opensource.java.test.io;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>1,</p>
 * 1,File类只能读取同一个操作系统的文件.
 * 2,可以通过UrlConnection读取远程的文件
 * @author luolong
 * @date 2018/12/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FileTest {
      private final static ExecutorService threadPool = Executors.newFixedThreadPool(10);
     @Test
     public void readRemoteFileTest(){
         String remoteFilePath = "";
    }

    /**
     * File file = new File(uri) 只能读取本地文件
     * @author long.luo
     * @date 2018/12/27
     * @param []
     * @return void
     */
    @Test
    public void readLocalFileTest() throws URISyntaxException, FileNotFoundException ,IOException{
        //URI uri = new URI("file", "172.16.11.100", "/E:/1111.txt", "");
        String filePath = "/1111.txt";
        File file = new File(filePath);
        if (file.exists()) {

            System.out.println("万事俱备，只欠东风");
        }else{
            System.out.println("本地文件不存在");
        }
        String fileResult = this.readFromFile(filePath);
        System.out.println(fileResult);
    }

    @Test
    public void renameFileTest() throws URISyntaxException, FileNotFoundException ,IOException{
        //URI uri = new URI("file", "172.16.11.100", "/E:/1111.txt", "");
        String filePath = "/1111.txt";
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("万事俱备，只欠东风");
        }else{
            System.out.println("本地文件不存在");
        }
        file.renameTo(new File("/1111.txt"));

    }

    @Test
    public void multyThreadWriteFileTest() throws Exception {
        String filePath = "/1111.txt";
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("万事俱备，只欠东风");
        }else{
            System.out.println("本地文件不存在");
        }

        //fw.write(Thread.currentThread().getName());
        ArrayList<Callable<Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable runnable = (() -> {
                try (FileWriter fw = new FileWriter(file,false);){
                    fw.write(Thread.currentThread().getName());
                    fw.write(Thread.currentThread().getName());
                    fw.write(Thread.currentThread().getName());
                    fw.write(Thread.currentThread().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0;
            });
            list.add(runnable);
        }
        List<Future<Object>> futures = threadPool.invokeAll(list);
        futures.forEach(o -> {

        });
        TimeUnit.SECONDS.sleep(10);



    }

    public String readFromFile(String address) throws FileNotFoundException, IOException {
        File file = new File(address);
        FileReader reader = new FileReader(file);
        char[] bb = new char[1024];
        StringBuffer sb = new StringBuffer();
        int n;
        while ((n = reader.read(bb)) != -1) {
            sb.append(new String(bb, 0, n));
        }

        reader.close();
        return sb.toString();
    }


}
