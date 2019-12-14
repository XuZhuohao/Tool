package com.yui.tool.zhihu.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yui.tool.zhihu.utils.HttpUtils;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XuZhuohao
 * @date 2019-12-14 0:20
 */
public class ImageService {
    private List<String> imgUrl = new ArrayList<>(200);
    /**
     * <img src=\"https://pic1.zhimg.com/50/v2-98785721d03ba12f5381365bc1a84dad_hd.jpg\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-87a4b44b3d480948546db7fdd87ba3fc_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"3024\" data-original=\"https://pic1.zhimg.com/v2-98785721d03ba12f5381365bc1a84dad_r.jpg\"/>
     */
    private static Pattern pattern = Pattern.compile("<img src=\"(https:[^\"]*)\"");
    private static final String QUESTION_URL = "https://www.zhihu.com/api/v4/questions/${problemNo}/";
    private static final String ANSWERS_API = "answers?include=data[*].is_normal,content&limit=${limit}&offset=${offset}&sort_by=default";
    private static String dir = "src/main/image/";
    private String problemNo = "";
    private long sortNo = 1L;

    private void login(String loginId, String password) {
//        HttpUtils.createJsonRequestBody()
    }

    /**
     * 下载问题下面的图片
     *
     * @param problemNo 问题编号
     * @param theTotal  默认排序，遍历问题数。 -1 时 表示为所有回答
     * @param offset    开始偏移值
     * @throws IOException
     */
    private void downloadImage(String problemNo, int theTotal, int offset) throws IOException {
        File file = new File(dir + problemNo);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.problemNo = problemNo;
        this.sortNo = offset;
        /**
         * ${problemNo}
         * ${limit}
         * ${offset}
         */
        String urlPrefix = QUESTION_URL.replace("${problemNo}", problemNo);
        // 一次抓10条
        int limit = 10;
        // 从 0 偏移开始
//        int offset = 0;
        // 定义初始总记录数为 999999
        int total = 999999;
        if (theTotal != -1) {
            total = theTotal;
            if (limit > total) {
                limit = total;
            }
        }
        while (offset < total) {
            //
            String url = urlPrefix + ANSWERS_API.replace("${limit}", limit + "")
                    .replace("${offset}", offset + "");
            Response response = HttpUtils.doSyncGet(url, null, null, null);
            if (response.code() != 200) {
                System.out.println(url);
                System.err.println(response.message());
                System.err.println(response.body().string());
                return;
            }
            JSONObject result = JSONObject.parseObject(response.body().string());
            if (total == 999999) {
                System.out.println(url);
                total = result.getJSONObject("paging").getInteger("totals");
                System.out.println(total);
            }
            offset += limit;

            //处理业务
            JSONArray data = result.getJSONArray("data");
            initImageUrl(data);
            System.out.println("==========================begin offset:" + offset);
        }

    }

    private void initImageUrl(JSONArray data) {
        data.forEach(temp -> {
            List<String> imgUrl = new ArrayList<>(160);
            JSONObject t1 = (JSONObject) temp;
            String content = t1.getString("content");
            final Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                imgUrl.add(matcher.group(1));
            }
            System.out.println("begin a question, sortNo:" + sortNo);
            downloadImg(imgUrl);
            sortNo++;
        });
    }

    private void downloadImg(List<String> imgUrl) {
        for (String url : imgUrl) {
            try {
                String fileName = this.sortNo + "-" + getName(url);
                HttpUtils.download(dir + this.problemNo + File.separator + fileName, url);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static String getName(String url) {
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    public static void main(String[] args) throws IOException {
        ///
//        String content = "<p>更照片</p><figure data-size=\"normal\"><noscript><img src=\"https://pic1.zhimg.com/50/v2-98785721d03ba12f5381365bc1a84dad_hd.jpg\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-87a4b44b3d480948546db7fdd87ba3fc_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"3024\" data-original=\"https://pic1.zhimg.com/v2-98785721d03ba12f5381365bc1a84dad_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;3024&#39; height=&#39;3024&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-87a4b44b3d480948546db7fdd87ba3fc_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"3024\" data-original=\"https://pic1.zhimg.com/v2-98785721d03ba12f5381365bc1a84dad_r.jpg\" data-actualsrc=\"https://pic1.zhimg.com/50/v2-98785721d03ba12f5381365bc1a84dad_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><figure data-size=\"normal\"><noscript><img src=\"https://pic2.zhimg.com/50/v2-3226f20a8b51c1e06f26593a480dd354_hd.jpg\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic3.zhimg.com/50/v2-2513adede4d1849b7f95e244fca318ae_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"3024\" data-original=\"https://pic2.zhimg.com/v2-3226f20a8b51c1e06f26593a480dd354_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;3024&#39; height=&#39;3024&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic3.zhimg.com/50/v2-2513adede4d1849b7f95e244fca318ae_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"3024\" data-original=\"https://pic2.zhimg.com/v2-3226f20a8b51c1e06f26593a480dd354_r.jpg\" data-actualsrc=\"https://pic2.zhimg.com/50/v2-3226f20a8b51c1e06f26593a480dd354_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><hr/><p>都这么久了还是有人点赞(((o(*ﾟ▽ﾟ*)o)))</p><p>更两张近照</p><figure data-size=\"normal\"><noscript><img src=\"https://pic2.zhimg.com/50/v2-90fa20d46a942d4b1686a0e33f9b139d_hd.jpg\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic1.zhimg.com/50/v2-729604741f0126ed832e5f7e43b5d036_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"3024\" data-original=\"https://pic2.zhimg.com/v2-90fa20d46a942d4b1686a0e33f9b139d_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;3024&#39; height=&#39;3024&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"3024\" data-rawheight=\"3024\" data-size=\"normal\" data-default-watermark-src=\"https://pic1.zhimg.com/50/v2-729604741f0126ed832e5f7e43b5d036_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"3024\" data-original=\"https://pic2.zhimg.com/v2-90fa20d46a942d4b1686a0e33f9b139d_r.jpg\" data-actualsrc=\"https://pic2.zhimg.com/50/v2-90fa20d46a942d4b1686a0e33f9b139d_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><figure data-size=\"normal\"><noscript><img src=\"https://pic3.zhimg.com/50/v2-46e85238f50d3558ac57deb6b5b6a40c_hd.jpg\" data-rawwidth=\"1242\" data-rawheight=\"876\" data-size=\"normal\" data-default-watermark-src=\"https://pic3.zhimg.com/50/v2-e9d825cc3f3707f0cd7c829ad7128958_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"1242\" data-original=\"https://pic3.zhimg.com/v2-46e85238f50d3558ac57deb6b5b6a40c_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;1242&#39; height=&#39;876&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"1242\" data-rawheight=\"876\" data-size=\"normal\" data-default-watermark-src=\"https://pic3.zhimg.com/50/v2-e9d825cc3f3707f0cd7c829ad7128958_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"1242\" data-original=\"https://pic3.zhimg.com/v2-46e85238f50d3558ac57deb6b5b6a40c_r.jpg\" data-actualsrc=\"https://pic3.zhimg.com/50/v2-46e85238f50d3558ac57deb6b5b6a40c_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><hr/><p>受宠若惊，第二个千赞的回答（第一个之前删掉了），很抱歉私信很多无法一一回复，感谢点赞关注评论的朋友(◐‿◑)\ufeff</p><p>分割线—————————————————</p><p>感受：</p><p>买裤子永远只能买加长版</p><p>爱长款外套爱到死</p><p>显高五公分（171经常被猜身高是175以上 ）</p><p>走在路上看自己的女生比男生多</p><p>会被闺蜜…摸腿…</p><p>会因为腿长对自己身材有更多要求，所以在坚持健身</p><p>好的，上图</p><figure data-size=\"normal\"><noscript><img src=\"https://pic2.zhimg.com/50/v2-bf26e7e37ae47e754c032345e653ac58_hd.jpg\" data-rawwidth=\"961\" data-rawheight=\"1280\" data-size=\"normal\" data-default-watermark-src=\"https://pic3.zhimg.com/50/v2-e4af8c0673d53c5de84e51fee3776cc6_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"961\" data-original=\"https://pic2.zhimg.com/v2-bf26e7e37ae47e754c032345e653ac58_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;961&#39; height=&#39;1280&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"961\" data-rawheight=\"1280\" data-size=\"normal\" data-default-watermark-src=\"https://pic3.zhimg.com/50/v2-e4af8c0673d53c5de84e51fee3776cc6_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"961\" data-original=\"https://pic2.zhimg.com/v2-bf26e7e37ae47e754c032345e653ac58_r.jpg\" data-actualsrc=\"https://pic2.zhimg.com/50/v2-bf26e7e37ae47e754c032345e653ac58_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><figure data-size=\"normal\"><noscript><img src=\"https://pic1.zhimg.com/50/v2-c011d53b5501cff94e1e1acac85b4bd7_hd.jpg\" data-rawwidth=\"1350\" data-rawheight=\"1670\" data-size=\"normal\" data-default-watermark-src=\"https://pic4.zhimg.com/50/v2-9558cfd8ab1d1c95665831e782ffb3e6_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"1350\" data-original=\"https://pic1.zhimg.com/v2-c011d53b5501cff94e1e1acac85b4bd7_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;1350&#39; height=&#39;1670&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"1350\" data-rawheight=\"1670\" data-size=\"normal\" data-default-watermark-src=\"https://pic4.zhimg.com/50/v2-9558cfd8ab1d1c95665831e782ffb3e6_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"1350\" data-original=\"https://pic1.zhimg.com/v2-c011d53b5501cff94e1e1acac85b4bd7_r.jpg\" data-actualsrc=\"https://pic1.zhimg.com/50/v2-c011d53b5501cff94e1e1acac85b4bd7_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><figure data-size=\"normal\"><noscript><img src=\"https://pic4.zhimg.com/50/v2-19275200bc8fa28edc897a20cac6519b_hd.jpg\" data-rawwidth=\"1200\" data-rawheight=\"1800\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-fd892fb96950c579ee11dcc6a4fff75a_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"1200\" data-original=\"https://pic4.zhimg.com/v2-19275200bc8fa28edc897a20cac6519b_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;1200&#39; height=&#39;1800&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"1200\" data-rawheight=\"1800\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-fd892fb96950c579ee11dcc6a4fff75a_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"1200\" data-original=\"https://pic4.zhimg.com/v2-19275200bc8fa28edc897a20cac6519b_r.jpg\" data-actualsrc=\"https://pic4.zhimg.com/50/v2-19275200bc8fa28edc897a20cac6519b_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><figure data-size=\"normal\"><noscript><img src=\"https://pic3.zhimg.com/50/v2-bbfa318b941c279dc416ac26663446da_hd.jpg\" data-rawwidth=\"2160\" data-rawheight=\"3840\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-fa1cde6537b3e7359372890ae32c0143_hd.jpg\" class=\"origin_image zh-lightbox-thumb\" width=\"2160\" data-original=\"https://pic3.zhimg.com/v2-bbfa318b941c279dc416ac26663446da_r.jpg\"/></noscript><img src=\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;2160&#39; height=&#39;3840&#39;&gt;&lt;/svg&gt;\" data-rawwidth=\"2160\" data-rawheight=\"3840\" data-size=\"normal\" data-default-watermark-src=\"https://pic2.zhimg.com/50/v2-fa1cde6537b3e7359372890ae32c0143_hd.jpg\" class=\"origin_image zh-lightbox-thumb lazy\" width=\"2160\" data-original=\"https://pic3.zhimg.com/v2-bbfa318b941c279dc416ac26663446da_r.jpg\" data-actualsrc=\"https://pic3.zhimg.com/50/v2-bbfa318b941c279dc416ac26663446da_hd.jpg\"/></figure><p class=\"ztext-empty-paragraph\"><br/></p><f";
//        final Matcher matcher = pattern.matcher(content);
//        while (matcher.find()) {
//            System.out.println(matcher.group(1));
//        }

//        String text = "https://pic1.zhimg.com/50/v2-98785721d03ba12f5381365bc1a84dad_hd.jpg";
//        System.out.println(getName(text));

        ImageService imageService = new ImageService();
        //308457217 285321190 62972819
        imageService.downloadImage("62972819", 30, 0);
//        imageService.downloadImg(Collections.singletonList("https://pic4.zhimg.com/50/v2-5eeced557e0298a53e4e0d18e86d8e72_hd.jpg"));
    }
}
