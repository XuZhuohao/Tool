package com.yui.tool.zhihu.utils;

import okhttp3.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

/**
 * @author XuZhuohao
 * @date 2019-12-14 0:20
 */
public class HttpUtils {

    public static void download(String filePath, String url){
        //第一步创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        // 第二步构建Request对象
        Request request = createBuilder(url, null, null, null).get().build();
        //第三步构建Call对象
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (OutputStream os = new FileOutputStream(filePath)) {
                    InputStream inputStream = response.body().byteStream();
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                    os.flush();
                    System.out.println("download: " + url);
                }
            }
        });
    }

    public static Response doSyncGet(String url,
                                     Map<String, String> params,
                                     RequestBody requestBody,
                                     Map<String, String> headers) throws IOException {
        //第一步创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        // 第二步构建Request对象
        Request request = createBuilder(url, params, requestBody, headers).get().build();
        //第三步构建Call对象
        final Call call = client.newCall(request);
        //第四步:同步get请求
        //必须子线程执行
        return call.execute();
    }

    /**
     * 构建一个 Request.Builder
     * @param url url 地址，不可为空，需带有 http:// 或 https:// 请求协议头
     * @param urlParams url 参数（主要用于 Get 请求参数），一般为空
     * @param requestBody requestBody 用于文件等二进制 body，可以为空
     * @param paramBody paramBody body 参数,不能为空，参数生成参照
     *                  {@link HttpUtils#createJsonRequestBody(java.lang.String)}
     *                  or {@link HttpUtils#createFormRequestBody}
     * @param headers 请求头
     * @return Request.Builder post or get
     */
    public static Response doSyncPost(String url,
                                      Map<String, String> urlParams,
                                      RequestBody requestBody,
                                      RequestBody paramBody,
                                      Map<String, String> headers) throws IOException {
        //第一步创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步创建RequestBody
        //第三步创建Request
        Request request = createBuilder(url, urlParams, requestBody, headers)
                .post(paramBody)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        return call.execute();
    }

    /**
     * 构建一个 Request.Builder
     * @param url url 地址，不可为空，需带有 http:// 或 https:// 请求协议头
     * @param urlParams url 参数（主要用于 Get 请求参数），可以为空
     * @param requestBody requestBody 用于文件等二进制 body，可以为空
     * @param headers 请求头
     * @return Request.Builder post or get
     */
    private static Request.Builder createBuilder(String url, Map<String, String> urlParams,
                                                 RequestBody requestBody,
                                                 Map<String, String> headers){
        /// 第一步获取okHttpClient对象
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
        //第二步构建Request对象
        final Request.Builder builder = new Request.Builder();
        // 根据入参构建 url
        final HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        if (urlParams != null && urlParams.size() > 0){
            urlParams.forEach(urlBuilder::addQueryParameter);
        }
        // 添加 body
        if (requestBody != null){
            builder.put(requestBody);
        }
        // 添加头信息
        if (headers != null && headers.size() > 0) {
            headers.forEach(builder::addHeader);
        }
        return builder.url(urlBuilder.build());
    }

    /**
     * 创建一个表单请求体
     * @param requestBodes 请求参数数据集
     * @return FormBody
     */
    public static RequestBody createFormRequestBody(Map<String, String> requestBodes){
        FormBody.Builder formBuilder = new FormBody.Builder();
        requestBodes.forEach(formBuilder::add);
        return formBuilder.build();
    }

    /**
     * 创建一个 json 请求体
     * @param jsonStr json 字符串
     * @return JSONBody
     */
    public static RequestBody createJsonRequestBody(String jsonStr){
        return RequestBody.create(jsonStr,MediaType.parse("application/json; charset=utf-8"));
    }
///
/*    public static void main(String[] args) throws Exception {
        final Response response = doSyncGet("http://www.baidu.com", null, null, null);
        System.out.println(response.body().string());
        final Response response1 = doSyncPost("http://www.baidu.com", null, null, createJsonRequestBody("{\"data\":\"data\"}"), null);
        System.out.println( response1.body().string());
    }*/

}
