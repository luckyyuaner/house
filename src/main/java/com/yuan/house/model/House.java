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
    private String name;
    private String doorplate;
    private int orders;
    private int type;
    private String kind;
    private double money;
    private int cycle;
    private double area;
    private String floor;
    private int elevator;
    private String orientation;
    private String address;
    private String description;
    private String words;
    private String urls;
    private double grade;
    private double longitude;
    private double latitude;
}
