package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @NonNull
    private int roleId;
    @NonNull
    private String name;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private int orders;
}
