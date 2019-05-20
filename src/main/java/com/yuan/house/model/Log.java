package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    private static final long serialVersionUID = 7069250564863091655L;
    private Long logId;
    private Long userId;
    private String content;
    private Timestamp time;
    private String type;
    private String target;
}
