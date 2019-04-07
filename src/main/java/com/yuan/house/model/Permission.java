package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private int permissionId;
    private int parentId;
    private String name;
    private int type;
    private String permissionValue;
    private String url;
    private String icon;
    private int status;
    private int orders;
}
