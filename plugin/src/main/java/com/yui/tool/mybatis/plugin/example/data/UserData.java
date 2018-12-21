package com.yui.tool.mybatis.plugin.example.data;

import com.yui.tool.mybatis.plugin.Runtime.AbstractCreateBy;

/**
 * @author XuZhuohao
 * @date 2018/12/21
 */
public class UserData extends AbstractCreateBy<Long> {
    @Override
    public Long getUser() {
        return System.currentTimeMillis();
    }
}
