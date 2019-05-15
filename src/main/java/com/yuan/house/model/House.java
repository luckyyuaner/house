package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {
    private Long houseId;
    private Long userId;
    private Long adId;
    private String name;
    private int order;
    private int type;
    private String kind;
    private double money;
    private int cycle;
    private double area;
    private int floor;
    private int elevator;
    private String orientation;
    private String address;
    private String description;
    private String keys;
    private String title;
    private String url;
    private double grade;
}
