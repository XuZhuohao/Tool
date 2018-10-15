package com.yui.tool.github.move;

import com.yui.tool.github.util.FileUtil;

import java.io.File;
import java.util.List;

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
        files.forEach(file -> picMove(file));
    }

    private static void picMove(File file) {
        //1.读取文件的url数据

    }
}
