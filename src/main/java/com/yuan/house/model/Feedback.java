package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Long feedbackId;
    private Long createId;
    private Long roleId;
    private Long operateId;
    private int type;
    private int status;
    private String info;
    private String url;
    private Timestamp utime;
    private Timestamp ctime;
}
