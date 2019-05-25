package com.yuan.house.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapHouseVO implements Serializable {
    private static final long serialVersionUID = 6027905188809974960L;
    private Long houseId;
    private String name;
    private int type;
    private double money;
    private double area;
    private double grade;
    private double longitude;
    private double latitude;
}
