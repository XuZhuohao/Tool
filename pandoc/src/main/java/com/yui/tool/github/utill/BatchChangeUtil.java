package com.yui.tool.github.utill;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 批量转换
 *
 * @author XuZhuohao
 * @date 2018/8/24
 */
public class BatchChangeUtil {
    /**
     * 批量文件格式装换，使用 Pandoc 进行文件装换
     * 根据patch循环生成对应文件，生成路径为pathc/toSufix
     *
     * @param patch     文件
     * @param fromSufix 要转换的文件原来后缀
     * @param toSufix   要转换的文件格式
     * @return 执行记录数
     */
    public static int changeFileFormat(String patch, String fromSufix, String toSufix) {
        int count = 0;
        if (patch.endsWith("\\")) {
            patch = patch.substring(0, patch.length() - 1);
        }
        List<File> files = FileUtil.getFiles(new File(patch));
        String directoryName = patch.substring(patch.lastIndexOf("\\") + 1);
        for (File file : files) {
            System.out.println(file.getAbsoluteFile());
            // 原文件后缀
            String fileSufix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (!fromSufix.toUpperCase().endsWith(fileSufix.toUpperCase())) {
                System.err.println(file.getAbsoluteFile().toString() + " is not the target file type! will continue");
                continue;
            }
            // 生成的文件路径
            String targetPath = file.getParent().replace(directoryName, directoryName + toSufix);
            File tempFile = new File(targetPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
                tempFile = null;
            }
            try {
                Pandoc.PandocChange(file.getAbsolutePath().toString(),
                        file.getAbsolutePath().replace(directoryName, directoryName + toSufix)
                                .replace("." + fileSufix, "." + toSufix));

                count++;
            } catch (IOException e) {
                System.err.println("err:------------" + file.getAbsoluteFile() + " is err");
                e.printStackTrace();
            }
        }
        System.out.println("total : " + count + " 条");
        return count;
    }

/*    public static void main(String[] args) {
        Md5ToDocxs("D:\\File\\Github\\StudySummarize");
        //System.out.println(new File("D:\\File\\Github\\StudySummarize\\README.md").getParent());
    }*/

    /**
     * 批量转换文件，由Md5转为doc
     *
     * @param patch 目标文件夹目录
     */
    public static void Md5ToDocxs(String patch) {
        changeFileFormat(patch, "md", "docx");
    }


}
