package com.yuan.house.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @NonNull
    private int userRoleId;
    @NonNull
    private int userId;
    @NonNull
    private int roleId;
}
