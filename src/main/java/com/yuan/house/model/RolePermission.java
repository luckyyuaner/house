package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @NonNull
    private int rolePermissionId;
    @NonNull
    private int roleId;
    @NonNull
    private int permissionId;
    @NonNull
    private int type;
}
