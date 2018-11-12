package com.yui.tool.imgremove.core.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yui.tool.imgremove.core.MdFile;
import com.yui.tool.imgremove.dto.ImageDto;
import com.yui.tool.imgremove.util.ImageUtil;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * MdFileImpl测试用例
 *
 * @author XuZhuohao
 * @date 2018/11/12
 */
public class MdFileImplTest {
    MdFile mdFile = new MdFileImpl();

    @Test
    public void TestGetAllMdFile() {
        MdFileImpl mdFile = new MdFileImpl();
        List<File> result = mdFile.getAllMdFile("D:\\File\\Github\\StudySummarize");
        result.forEach(file -> System.out.println(file.getAbsolutePath()));
    }

    @Test
    public void TestGetImagesFromMdFile() {
        String path = "D:\\File\\Github\\StudySummarize";
        List<ImageDto> imagesFromMdFile = mdFile.getImagesFromMdFile(path);
        imagesFromMdFile.forEach(imageDto -> System.out.println(JSON.toJSONString(imageDto)));
        System.out.println("totall:" + imagesFromMdFile.size());
    }

    @Test
    public void testBug() {
        String jsonStr = "{\"mdFile\":\"D:\\\\File\\\\Github\\\\StudySummarize\\\\java\\\\Frame\\\\Spring\\\\SpringBoot\\\\1.准备.md\",\"name\":\"testCLI\",\"newUrl\":\"https://raw.githubusercontent.com/XuZhuohao/picture/master/java/Frame/Spring/SpringBoot/testCLI.png\",\"oldUrl\":\"https:///raw.githubusercontent.com/XuZhuohao/studyNote-git-markdown-File-img/master/spring%20boot/1.install/testCLI.png\",\"path\":\"D:\\\\File\\\\Github\\\\picture\\\\java\\\\Frame\\\\Spring\\\\SpringBoot\\\\testCLI.png\",\"suffix\":\"png\"}";
        ImageDto imageDto = JSONObject.toJavaObject(JSON.parseObject(jsonStr), ImageDto.class);
        try {
            System.out.println(imageDto.getOldUrl());
            ImageUtil.downloadImage(imageDto.getOldUrl(), imageDto.getPath());
        } catch (IOException e) {
            System.err.println("下载失败");
            e.printStackTrace();
        }
    }

    @Test
    public void testEdit() {
        File file = new File("D:\\data\\test\\1.md");
        System.out.println(file.length());
        PrintWriter printWriter = null;
        try (FileInputStream fis = new FileInputStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            StringBuffer sbf = new StringBuffer();
            // 读取封装的输入流
            String temp;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp).append("\r\n");
            }
            String edit = sbf.toString().replace("www.github.com", "www.test.com");
            //FileOutputStream fos = new FileOutputStream(file);
            System.out.println(edit);
            printWriter = new PrintWriter(file, "UTF-8");
            printWriter.write(edit);
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null)
                printWriter.close();
        }
    }

    @Test
    public void testGetImageDtoInMdFile(){
        File file = new File("D:\\File\\Github\\StudySummarize\\java\\Frame\\Spring\\SpringBoot\\2.开发第一个应用程序.md");
        System.out.println(JSON.toJSONString(MdFileImpl.getImageDtoInMdFile(file)));
    }
    @Test
    public void testSpace(){
        String url = "wwww.baidu.com/spring%20cloud/spring%20boot/1.jpg";
        System.out.println(url.indexOf("baidu"));
        System.out.println("baidu".length());
        System.out.println(url.substring(url.indexOf("baidu"),url.indexOf("baidu") + "baidu".length()));
    }
}
