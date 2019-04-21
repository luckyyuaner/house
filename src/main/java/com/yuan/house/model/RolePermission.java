package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission implements Serializable {
    private static final long serialVersionUID = -5815673749740319195L;
    private Long rolePermissionId;
    private Long roleId;
    private Long permissionId;
}
