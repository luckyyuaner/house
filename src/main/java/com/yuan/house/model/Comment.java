package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long commentId;
    private Long userId;
    private Long contractId;
    private double userGrade;
    private double houseGrade;
    private String info;
    private String url;
    private Timestamp ctime;
}
