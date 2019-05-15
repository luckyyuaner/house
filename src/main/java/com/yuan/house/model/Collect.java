package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect {
    private Long collectId;
    private Long userId;
    private Long houseId;
    private Timestamp ctime;
}
