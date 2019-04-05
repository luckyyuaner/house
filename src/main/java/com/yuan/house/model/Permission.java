package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @NonNull
    private int permissionId;
    @NonNull
    private int parentId;
    @NonNull
    private String name;
    @NonNull
    private int type;
    @NonNull
    private String permissionValue;
    @NonNull
    private String url;
    @NonNull
    private String icon;
    @NonNull
    private int status;
    @NonNull
    private int orders;
}
