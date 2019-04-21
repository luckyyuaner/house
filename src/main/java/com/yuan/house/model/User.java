package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private int userType;
    private int status;
    private Timestamp ctime;
    private Timestamp utime;
    private String head;
    private double money;
    private String phone;
    private String idcard;
    private String photo;
    private Date birth;
    private int sex;
    private double reputation;
}
