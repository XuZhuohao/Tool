package com.yui.tool.monitor.email.service.impl;

import com.yui.tool.monitor.email.dto.MailSenderDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuZhuohao
 * @date 2019/8/1
 */
public class JavaMailSenderService {
    private static Map<String, JavaMailSender> MAIL_SENDER_CACHE = new HashMap<>(16);    private JavaMailSenderService(){}

    /**
     * 添加一个email服务
     * @param mailSenderDto
     * @return
     */
    public static String addJavaMailSender(MailSenderDto mailSenderDto){
        String key = mailSenderDto.getHost() + ":" + mailSenderDto.getPort();
        final JavaMailSender temp = MAIL_SENDER_CACHE.get(key);
        if (temp != null){
            return key;
        }
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailSenderDto.getHost());
        javaMailSender.setPort(mailSenderDto.getPort());
        javaMailSender.setDefaultEncoding(mailSenderDto.getEncoding());
        javaMailSender.setProtocol(mailSenderDto.getProtocol());
        MAIL_SENDER_CACHE.put(key, javaMailSender);
        return key;
    }

    /**
     * 获取服务
     * @param key 添加时返回的
     * @return JavaMailSender
     */
    public static JavaMailSender getJavaMailSender(String key){
        return MAIL_SENDER_CACHE.get(key);
    }

    /**
     * 获取所有的服务
     * @return List<JavaMailSender>
     */
    public static List<JavaMailSender> getAllJavaMailSender(){
        return new ArrayList<>(MAIL_SENDER_CACHE.values());
    }

}
