package com.yui.tool.github.utill;

import java.io.IOException;

/**
 * 使用PanDoc的工具类
 *
 * @author XuZhuohao
 * @date 2018/8/24
 */
public class Pandoc {
    public static void PandocChange(String fromName, String toName) throws IOException {
        // pandoc -s -o D:\File\Github\StudySummarizeWord\java\t1.docx "1.面向对象[T].md"
        CMDUtil.exec("pandoc -s -o", "\"" + toName + "\"", "\"" + fromName + "\"");
    }

/*    public static void main(String[] args) throws IOException {
        PandocChange("D:\\File\\Github\\StudySummarizeWord\\java\\t2.docx", "D:\\File\\Github\\StudySummarize\\java\\Base\\5.String.md");
    }*/
}
