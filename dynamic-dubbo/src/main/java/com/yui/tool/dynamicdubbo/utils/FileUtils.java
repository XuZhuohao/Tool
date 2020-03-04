package com.yui.tool.dynamicdubbo.utils;

import java.io.File;

/**
 * @author XuZhuohao
 * @date 2020-03-04 15:15
 */
public class FileUtils {
    public synchronized static String getNewDirectory(String mainPath){
        String newPath;
        if (mainPath.endsWith(File.separator)){
            newPath = mainPath + System.currentTimeMillis();
        } else {
            newPath = mainPath + File.separator + System.currentTimeMillis();
        }
        new File(newPath).mkdir();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return newPath;

    }
}
