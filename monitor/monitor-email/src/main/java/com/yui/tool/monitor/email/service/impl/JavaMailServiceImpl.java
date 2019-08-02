package com.yui.tool.monitor.email.service.impl;

import com.yui.tool.monitor.email.dto.EmailEntity;
import com.yui.tool.monitor.email.dto.FileEntity;
import com.yui.tool.monitor.email.dto.ImageEntity;
import com.yui.tool.monitor.email.service.JavaMailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Set;

/**
 * @author XuZhuohao
 * @date 2019/8/1
 */
@Service
public class JavaMailServiceImpl implements JavaMailService {
    @Override
    public boolean sendEmail(JavaMailSender mailSender, EmailEntity emailEntity) {
        // 设置发送人和发送密码
        ((JavaMailSenderImpl)mailSender).setUsername(emailEntity.getFromUser());
        ((JavaMailSenderImpl)mailSender).setPassword(emailEntity.getPassWord());
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailEntity.getFromUser());
            helper.setTo(emailEntity.getToUser());
            for (String s : emailEntity.getCopyTo()) {
                helper.addCc(s);
            }
            helper.setSubject(emailEntity.getSubject());
            helper.setText(emailEntity.getContent(), true);
            // 插入图片
            Set<ImageEntity> images = emailEntity.getImages();
            if (images != null) {
                for (ImageEntity image : images) {
                    helper.addInline(image.getCid(), new FileSystemResource(new File(image.getSrc())));
                }
            }
            // 插入附件
            Set<FileEntity> files = emailEntity.getFiles();
            if (files != null) {
                for (FileEntity file : files) {
                    helper.addAttachment(file.getFileName(), new FileSystemResource(new File(file.getPath())));
                }
            }
            // 发送邮件
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
