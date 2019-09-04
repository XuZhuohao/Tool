package com.yui.tool.monitor.email.service;

import com.yui.tool.monitor.email.dto.ReceiveEmailEntity;
import com.yui.tool.monitor.email.dto.SendEmailEntity;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author XuZhuohao
 * @date 2019/8/1
 */
public interface JavaMailService {
    /**
     *@param mailSender  邮箱服务
     * @param sendEmailEntity
     * @return
     */
    boolean sendEmail(JavaMailSender mailSender, SendEmailEntity sendEmailEntity);


    void receiveEmail(JavaMailSender mailSender, ReceiveEmailEntity receiveEmailEntity);
}
