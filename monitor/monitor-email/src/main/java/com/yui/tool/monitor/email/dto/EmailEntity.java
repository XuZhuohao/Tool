package com.yui.tool.monitor.email.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 邮件实体类
 *
 * @author XuZhuohao
 * @date 2018/10/16
 */
@Setter
@Getter
@Accessors(chain = true)
public class EmailEntity {
    /**
     * 发件人
     */
    private String fromUser;
    /**
     * 发件人密码
     */
    private String passWord;
    /**
     * 收件人
     */
    private String[] toUser;
    /**
     * 抄送
     */
    private String[] copyTo;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 插入图片
     */
    private Set<ImageEntity> images;
    /**
     * 附件
     */
    private Set<FileEntity> files;
}
