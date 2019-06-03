package com.yuan.house.dao;

import com.yuan.house.model.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractDao {

    /**
     * 根据id查询Contract
     * @param id
     * @return
     */
	Contract queryContractById(@Param("id") Long id);

	void createNewContractByTenant(@Param("object") Contract object);

    int updateContractByTenant(@Param("object") Contract object);

    List<Contract> queryContractsByTenant(@Param("uid") Long uid);

    int deleteContract(@Param("id") Long id);
}
