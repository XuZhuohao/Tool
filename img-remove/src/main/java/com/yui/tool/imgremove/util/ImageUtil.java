package com.yui.tool.imgremove.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片处理
 *
 * @author XuZhuohao
 * @date 2018/10/15
 */
public class ImageUtil {
    static int COUNT = 0;
    /**
     * 图片下载
     *
     * @param url      下载url
     * @param filePath 保持磁盘地址
     * @throws IOException io异常
     */
    public static Map<String, Object> downloadImage(String url, String filePath) throws IOException {
        System.out.println("正在下载：" + url);
        Map<String, Object> result = new HashMap<>(16);
        // 检查文件目录是否存在
        mkdirDirectory(filePath);
        File tempFile = new File(filePath);
        if(tempFile.length() > 0) {
            COUNT++;
            System.out.println("第" + COUNT + "个文件已经存在，跳过下载,size: " +  tempFile.length());
            result.put("size", tempFile.length());
            return result;
        }
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(filePath));
        HttpClient httpClient = new HttpClient();
        // 设置http连接主机服务超时时间：15000毫秒
        // 先获取连接管理器对象，再获取参数对象,再进行参数的赋值
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建一个Get方法实例对象
        GetMethod getMethod = new GetMethod(url);
        // 设置get请求超时为60000毫秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        // 设置请求重试机制，默认重试次数：3次，参数设置为true，重试机制可用，false相反
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
        // 执行Get方法
        int statusCode = httpClient.executeMethod(getMethod);
        if (statusCode != HttpStatus.SC_OK) {
            // 如果状态码返回的不是ok,说明失败了,打印错误信息
            System.err.println("Method faild: " + getMethod.getStatusLine());
            throw new RuntimeException("Method faild: " + getMethod.getStatusLine());
        } else {
            // https://raw.githubusercontent.com/XuZhuohao/studyNote-git-markdown-File-img/master/Comic/htt01.jpg
            // https:///raw.githubusercontent.com/XuZhuohao/studyNote-git-markdown-File-img/blob/master/spring%20boot/1.install/testCLI.png
            // 通过getMethod实例，获取远程的一个输入流
            InputStream is = getMethod.getResponseBodyAsStream();
            // 往文件里面写数据
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                imageOutput.write(buffer, 0, length);
            }
            result.put("size", imageOutput.length());
            imageOutput.close();
            is.close();
        }
        return result;
    }

    /**
     * 检查文件目录，如果不存在，创建该目录
     *
     * @param filePath
     */
    private static void mkdirDirectory(String filePath) {
        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("创建文件夹：" + file + " - " + file.mkdirs());
        }
    }
}
