package com.yui.tool.github.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;

/**
 * 图片处理
 *
 * @author XuZhuohao
 * @date 2018/10/15
 */
public class ImageUtil {
    public static void downloadImage(String url, String name ) throws IOException {
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(name));
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
            // 通过getMethod实例，获取远程的一个输入流
            InputStream is = getMethod.getResponseBodyAsStream();
            // 往文件里面写数据
            byte[] buffer = new byte[1024];
            int length;
            while((length = is.read(buffer))>0){
                imageOutput.write(buffer,0,length);
            }
            imageOutput.close();
            is.close();
        }
    }
}
