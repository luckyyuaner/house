package com.yuan.house.POJO;

import com.yuan.house.model.Comment;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantContractPOJO {
    private Contract contract;
    private House house;
    private User landlord;
    private User tenant;
    private List<Comment> comments;
}
