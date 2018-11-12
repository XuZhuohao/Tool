package com.yui.tool.imgremove.util;

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
}
