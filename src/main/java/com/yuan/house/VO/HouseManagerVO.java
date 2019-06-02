package com.yuan.house.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseManagerVO implements Serializable{
    private static final long serialVersionUID = 3220150778376873386L;
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
    private int status;

    private String username;
    private String head;
    private String phone;
    private int sex;
    private double reputation;
    private String email;
}
