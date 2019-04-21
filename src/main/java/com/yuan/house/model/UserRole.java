package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    private static final long serialVersionUID = -1521979715527692108L;
    private Long userRoleId;
    private Long userId;
    private Long roleId;
}
