package com.yui.tool.monitor.email.service.impl;

import com.alibaba.fastjson.JSON;
import com.yui.tool.monitor.email.dto.ReceiveEmailEntity;
import com.yui.tool.monitor.email.dto.SendEmailEntity;
import com.yui.tool.monitor.email.dto.FileEntity;
import com.yui.tool.monitor.email.dto.ImageEntity;
import com.yui.tool.monitor.email.service.JavaMailService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
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
    public boolean sendEmail(JavaMailSender mailSender, SendEmailEntity sendEmailEntity) {
        // 设置发送人和发送密码
        ((JavaMailSenderImpl)mailSender).setUsername(sendEmailEntity.getFromUser());
        ((JavaMailSenderImpl)mailSender).setPassword(sendEmailEntity.getPassWord());
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sendEmailEntity.getFromUser());
            helper.setTo(sendEmailEntity.getToUser());
            for (String s : sendEmailEntity.getCopyTo()) {
                helper.addCc(s);
            }
            helper.setSubject(sendEmailEntity.getSubject());
            helper.setText(sendEmailEntity.getContent(), true);
            // 插入图片
            Set<ImageEntity> images = sendEmailEntity.getImages();
            if (images != null) {
                for (ImageEntity image : images) {
                    helper.addInline(image.getCid(), new FileSystemResource(new File(image.getSrc())));
                }
            }
            // 插入附件
            Set<FileEntity> files = sendEmailEntity.getFiles();
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

    @Override
    public void receiveEmail(JavaMailSender mailSender, ReceiveEmailEntity receiveEmailEntity){
        final JavaMailSenderImpl realMailSender = (JavaMailSenderImpl) mailSender;
        try {
            /*
            使用 InstallCert 去生成安全证书 jssecacerts，并放到 %JAVA_HOME%\jre\lib\security 目录下
            */
            final Store store = realMailSender.getSession().getStore();
            store.connect(receiveEmailEntity.getUsername(), receiveEmailEntity.getPassword());
            final Folder defaultFolder = store.getDefaultFolder();
            System.out.println("defaultFolder name:" + defaultFolder.getName());
            final Folder[] allDefalutFolder = defaultFolder.list();
            for (Folder folder : allDefalutFolder) {
                System.out.println("\tfolader name: " + folder.getName());
                folder.open(Folder.READ_ONLY);
                for (Message message : folder.getMessages()) {
                    System.out.println("\t\tmessage:" + message.getSubject());
                    System.out.println("\t\tfrom: " + JSON.toJSON(message.getFrom()));
                    System.out.println("\t\treplyTo: " + JSON.toJSON(message.getReplyTo()));
                    System.out.println("\t\tcontent: " + message.getContent().toString());
                    System.out.println("=========================================");
                }
                folder.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
