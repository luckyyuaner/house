package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect implements Serializable {
    private static final long serialVersionUID = 5963019739025347757L;
    private Long collectId;
    private Long userId;
    private Long houseId;
    private Timestamp ctime;
}
