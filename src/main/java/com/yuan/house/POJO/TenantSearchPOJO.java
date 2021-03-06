package com.yuan.house.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantSearchPOJO {
    private int type;
    private String msg;
    private String kind;
    private String orientation;
    private String show;
    private String sx;
    private int elevator;
    private int cycle;
    private int area1;
    private int area2;
    private int money1;
    private int money2;
}
