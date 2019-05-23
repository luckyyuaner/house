package com.yuan.house.controller;

import com.yuan.house.POJO.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class ChatController {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/private")
    public void privatechat(ChatMessage message) throws Exception {
        System.out.println("执行");
        String ctx = message.getContent();
        String send = message.getName();
        String receiver = message.getReceiver();

        String date = simpleDateFormat.format(new Date());
        String content1 = date + "【" + send + "】对你说：" + ctx;
        String content2 = date + " 你对【" + receiver + "】说：" + ctx;

        template.convertAndSendToUser(send, "/topic/private", new ChatMessage(receiver, content2, receiver, date));
        Thread.sleep(1000);
        template.convertAndSendToUser(receiver, "/topic/private", new ChatMessage(send, content1, send, date));
    }
}
