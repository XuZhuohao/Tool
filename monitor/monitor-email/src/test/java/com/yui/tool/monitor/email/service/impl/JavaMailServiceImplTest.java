package com.yui.tool.monitor.email.service.impl;


import com.yui.tool.monitor.email.MonitorEmailApplicationTest;
import com.yui.tool.monitor.email.dto.EmailEntity;
import com.yui.tool.monitor.email.dto.FileEntity;
import com.yui.tool.monitor.email.dto.ImageEntity;
import com.yui.tool.monitor.email.dto.MailSenderDto;
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
        mailSenderDto.setEncoding("utf-8").setHost("xxx.xxx.xxx.xxx").setPort(25).setProtocol("smtp");
        final String key = JavaMailSenderService.addJavaMailSender(mailSenderDto);
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setContent("内容：email 测试<image src=\"cid:111\"/>").setFromUser("786725551@qq.com")
                .setPassWord("passWord").setSubject("主题：测试")
                .setToUser(new String[]{"test1@qq.com","test2@qq.com"});

        Set<FileEntity> files = new HashSet<>(16);
        files.add(new FileEntity().setFileName("附件1.jpg").setPath("D:\\data\\023 (4).jpg"));
        emailEntity.setFiles(files);

        Set<ImageEntity> images = new HashSet<>(16);
        images.add(new ImageEntity().setCid("111").setSrc("D:\\data\\023 (4).jpg"));
        emailEntity.setImages(images);

        emailEntity.setCopyTo(new String[]{"test3@qq.com","test4@qq.com"});

        final boolean b = javaMailService.sendEmail(JavaMailSenderService.getJavaMailSender(key), emailEntity);
        System.out.println("发送结果：" + b);
    }

}
