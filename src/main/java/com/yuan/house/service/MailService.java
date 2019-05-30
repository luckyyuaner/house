package com.yuan.house.service;

import java.io.File;
import java.util.List;

/**
 * 邮箱Service
 */
public interface MailService {

    /**
     * 发送简单邮件
     * @param to 接收者
     * @param title 邮件主题
     * @param content 邮件内容
     */
    void sendSimpleMail(String to,String title,String content);

    /**
     * 发送带文件邮件
     * @param to 接收者
     * @param title 邮件主题
     * @param content 邮件内容
     * @param files 邮件附件
     */
    void sendAttachmentsMail(String to, String title, String content, List<File> files);
}
