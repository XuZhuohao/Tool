package com.yui.tool.monitor.email.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 接收邮件实体
 *
 * @author XuZhuohao
 * @date 2019/9/3
 */
@Setter
@Getter
@Accessors(chain = true)
public class ReceiveEmailEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 文件夹
     */
    private String folder;
}
