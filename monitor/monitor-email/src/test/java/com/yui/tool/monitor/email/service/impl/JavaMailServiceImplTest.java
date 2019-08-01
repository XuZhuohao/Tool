package com.yui.tool.monitor.email.service.impl;


import com.yui.tool.monitor.email.MonitorEmailApplicationTest;
import com.yui.tool.monitor.email.dto.EmailEntity;
import com.yui.tool.monitor.email.dto.MailSenderDto;
import com.yui.tool.monitor.email.service.JavaMailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JavaMailServiceImplTest extends MonitorEmailApplicationTest {
    @Autowired
    JavaMailService javaMailService;

    @Test
    public void test(){
        MailSenderDto mailSenderDto = new MailSenderDto();
        mailSenderDto.setEncoding("utf-8").setHost("xxx.xxx.xx").setPort(25).setProtocol("smtp");
        final String key = JavaMailSenderService.addJavaMailSender(mailSenderDto);
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setContent("内容：email 测试").setFromUser("651334311@qq.com")
                .setPassWord("passwodr").setSubject("主题：测试").setToUser("t1@qq.com");
        final boolean b = javaMailService.sendEmail(JavaMailSenderService.getJavaMailSender(key), emailEntity);
        System.out.println("发送结果：" + b);
    }

}
