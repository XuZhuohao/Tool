package com.yui.tool.github.utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * CMD操作工具类
 *
 * @author XuZhuohao
 * @date 2018/8/24
 */
public class CMDUtil {
    private static Runtime runtime = Runtime.getRuntime();

    /**
     * @param params cmd 命令
     * @return InputStream  charsetName:GB2312
     * @throws IOException
     */
    public static InputStream exec(String... params) throws IOException {
        StringBuilder command = new StringBuilder();
        for (String param : params) {
            command.append(param).append(" ");
        }
        System.out.println("CMDUtil 执行命令：" + command);
        return runtime.exec(command.toString()).getInputStream();
    }

/*    public static void main(String[] args) throws IOException {
        String[] params = {"ipconfig", "/flushdns"};
        BufferedReader br = new BufferedReader(new InputStreamReader(exec(params), "GB2312"));
        String line = null;
        StringBuffer b = new StringBuffer();
        while ((line = br.readLine()) != null) {
            b.append(line + "\n");
        }
        System.out.println(b.toString());
    }*/
}
