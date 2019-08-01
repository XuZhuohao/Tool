package com.yui.tool.monitor.email.service;

import com.yui.tool.monitor.email.dto.EmailEntity;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author XuZhuohao
 * @date 2019/8/1
 */
public interface JavaMailService {
    /**
     *@param mailSender  邮箱服务
     * @param emailEntity
     * @return
     */
    boolean sendEmail(JavaMailSender mailSender, EmailEntity emailEntity);
}
