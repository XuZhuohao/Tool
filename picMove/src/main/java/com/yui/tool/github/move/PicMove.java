package com.yui.tool.github.move;

import com.yui.tool.github.domain.MdUrlObject;
import com.yui.tool.github.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片移动
 *
 * @author XuZhuohao
 * @date 2018/10/15
 */
public class PicMove {
    public static void picMove(String path){
        File parentFile = new File(path);
        List<File> files = FileUtil.getFiles(parentFile);
        //files.forEach(file -> picMove(file));
    }

    public static void picMove(File file) throws Exception {
        //1.读取文件的url数据
        List<MdUrlObject> mdUrlObjects = getUrlsInFile(file);
        //2.获取图片

    }

    /**
     *
     * @param file
     */
    private static List<MdUrlObject> getUrlsInFile(File file) throws Exception {
        List<MdUrlObject> mdUrlObjects = new ArrayList<>(16);
        //(![](()))  group 1 -> ![]()   group2 -> ()
        String patternStr = "([!][\\[][^]]*[]][(]([^)]*)[)])";
        // 编译正则表达式规则
        Pattern pattern = Pattern.compile(patternStr);
        String text = getStringOfFile(file);
        // 进行匹配
        Matcher matcher = pattern.matcher(text);
        // 循环获取结果
        while (matcher.find()) {
            // group(1) ![...](...www.xx.com....)
            // group(2)  www.xx.com
            MdUrlObject mdUrlObject = new MdUrlObject();
            mdUrlObject.setFile(file);
            mdUrlObject.setMdTag(matcher.group(1));
            mdUrlObject.setUrl(matcher.group(2));
            mdUrlObjects.add(mdUrlObject);
        }
        return mdUrlObjects;
    }

    /**
     * file to String
     * @param file 文件对象
     * @return String
     * @throws Exception  FileNotFoundException, IOException
     */
    private static String getStringOfFile(File file) throws Exception {
        StringBuilder result = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        byte[] cache = new byte[1024];
        while(fis.read(cache) != -1 ){
            result.append(new String(cache));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        File file = new File("D:\\File\\Github\\StudySummarize\\java\\Frame\\Spring\\SpringBoot\\2.开发第一个应用程序.md");
        try {
            getUrlsInFile(file).forEach(mdUrlObject -> System.out.println(mdUrlObject.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }
/*        Properties properties = System.getProperties();
        //properties.stringPropertyNames().forEach(System.out::println);
        properties.stringPropertyNames().forEach(temp -> System.out.println(temp + ":" + properties.get(temp)));
        System.out.println(System.getProperty("user.dir"));*/
    }
}
