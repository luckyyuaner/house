package com.yuan.house.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantSearchPOJO {
    private int type;
    private List<String> msg;
    private String kind;
    private String orientation;
    private String order;
}
