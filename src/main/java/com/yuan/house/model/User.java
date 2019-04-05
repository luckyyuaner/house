package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull
    private int userId;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private int userType;
    @NonNull
    private int status;
    @NonNull
    private Timestamp ctime;
    @NonNull
    private Timestamp utime;
}
