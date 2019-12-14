package com.yui.tool.zhihu.enums;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author XuZhuohao
 * @date 2019-12-14 0:22
 */
public enum ZhiHuHttpUrl {
    /**
     *
     */
    LOGIN("https://www.zhihu.com/api/v3/oauth/sign_in", "登录", RequestMethod.POST)
    ;
    private String url;
    private String into;
    private RequestMethod requestMethod;

    ZhiHuHttpUrl(String url, String into, RequestMethod requestMethod) {
        this.url = url;
        this.into = into;
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getInto() {
        return into;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }
}
