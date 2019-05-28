package com.yuan.house.dao;

import com.yuan.house.model.Contract;
import org.apache.ibatis.annotations.Param;

public interface ContractDao {

    /**
     * 根据id查询Contract
     * @param id
     * @return
     */
	Contract queryContractById(@Param("id") Long id);


}
