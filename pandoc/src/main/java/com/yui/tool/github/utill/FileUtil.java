package com.yui.tool.github.utill;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具
 *
 * @author XuZhuohao
 * @date 2018/8/24
 */
public class FileUtil {
    /**
     * 返回所有文件列表
     *
     * @param file 文件
     * @return
     */
    public static List<File> getFiles(File file) {
        //File file = new File(patch);
        List<File> files = new ArrayList<>();
        if (!file.isDirectory()) {
            files.add(file);
            return files;
        }
        for (File tempFile : file.listFiles()) {
            files.addAll(getFiles(tempFile));
        }
        return files;
    }

//    public static void main(String[] args) {
//        List<File> files = getFiles(new File("D:\\File\\Github\\StudySummarize\\java\\Base"));
//        files.forEach(file -> {
//            System.out.println(file.getName());
//        });
//    }
}
