package com.yuan.house.service.impl;


import com.yuan.house.constants.Constants;
import com.yuan.house.service.MailService;
import com.yuan.house.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String to, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Constants.MAIL_SENDER);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        LoggerUtil.info("邮件发送成功");
    }

    @Override
    public void sendAttachmentsMail(String to, String title, String content, List<File> files) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(Constants.MAIL_SENDER);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);
            for(File file : files) {
                String fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
                helper.addAttachment(fileName, file);
            }
        }
        catch(Exception e) {
            LoggerUtil.error("邮件发送失败",e);
        }
        mailSender.send(message);
        LoggerUtil.info("邮件发送成功");
    }
}
