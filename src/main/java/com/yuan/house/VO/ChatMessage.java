package com.yuan.house.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

  /**
   * 发送者
   */
  private String name;
  /**
   * 内容
   */
  private String content;
  /**
   * 接收者
   */
  private String receiver;
  /**
   * 时间
   */
  private String date;
}
