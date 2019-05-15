package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = -9082123468979434705L;
    private Long permissionId;
    private Long parentId;
    private String name;
    private int type;
    private String permissionValue;
    private String url;
    private String icon;
    private int status;
}
