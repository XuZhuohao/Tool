package com.yui.tool.monitor.email.service.impl;


import com.yui.tool.monitor.email.MonitorEmailApplicationTest;
import com.yui.tool.monitor.email.dto.*;
import com.yui.tool.monitor.email.service.JavaMailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class JavaMailServiceImplTest extends MonitorEmailApplicationTest {
    @Autowired
    JavaMailService javaMailService;

    @Test
    public void test() {
        MailSenderDto mailSenderDto = new MailSenderDto();
        mailSenderDto.setEncoding("utf-8").setHost("xxx").setPort(25).setProtocol("smtp");
        final String key = JavaMailSenderService.addJavaMailSender(mailSenderDto);
        SendEmailEntity sendEmailEntity = new SendEmailEntity();
        sendEmailEntity.setContent("内容：email 测试<image src=\"cid:111\"/>").setFromUser("xxx@xxx.com.cn")
                .setPassWord("password").setSubject("主题：测试")
                .setToUser(new String[]{"xuxianxue@bluemoon.com.cn","huangshijie2@bluemoon.com.cn"});

        Set<FileEntity> files = new HashSet<>(16);
        files.add(new FileEntity().setFileName("附件1.jpg").setPath("D:\\data\\023 (4).jpg"));
        sendEmailEntity.setFiles(files);

        Set<ImageEntity> images = new HashSet<>(16);
        images.add(new ImageEntity().setCid("111").setSrc("D:\\data\\023 (4).jpg"));
        sendEmailEntity.setImages(images);

        sendEmailEntity.setCopyTo(new String[]{"t1@xxx.com.cn","t2@xxx.com.cn"});

        final boolean b = javaMailService.sendEmail(JavaMailSenderService.getJavaMailSender(key), sendEmailEntity);
        System.out.println("发送结果：" + b);
    }

    @Test
    public void testReceive(){
        MailSenderDto mailSenderDto = new MailSenderDto();
        // imps imp pop3 pop3s  s -> ssl
        mailSenderDto.setEncoding("utf-8").setHost("xxx").setPort(101).setProtocol("imaps");
        final String key = JavaMailSenderService.addJavaMailSender(mailSenderDto);
        ReceiveEmailEntity entity = new ReceiveEmailEntity();
        entity.setUsername("name@xxx.com.cn").setPassword("password");
        javaMailService.receiveEmail(JavaMailSenderService.getJavaMailSender(key), entity);

    }
}
