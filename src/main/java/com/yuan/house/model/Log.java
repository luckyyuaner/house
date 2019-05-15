package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Long logId;
    private Long userId;
    private String content;
    private Timestamp time;
    private String type;
    private String target;
}
