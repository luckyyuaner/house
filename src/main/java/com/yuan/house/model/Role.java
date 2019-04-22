package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 5061501351312674256L;
    private Long roleId;
    private String name;
    private String title;
    private String description;
    private int orders;
    private int status;
}
