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

    int updateContractByTenant3(@Param("object") Contract object);

    int updateContractByTenant2(@Param("object") Contract object);

    int updateContractByLandlord2(@Param("object") Contract object);

    int updateContractByLandlordWithAgree(@Param("object") Contract object);

    int updateContractByLandlordWithRefuse(@Param("object") Contract object);

    int updateContractByManagerWithAgree(@Param("object") Contract object);

    int updateContractByManagerWithAgree2(@Param("object") Contract object);

    int updateContractByManagerWithRefuse(@Param("object") Contract object);

    List<Contract> queryContractsByTenant(@Param("uid") Long uid,@Param("sta") int sta);

    List<Contract> queryContractsByLandlord(@Param("uid") Long uid,@Param("sta") int sta);

    List<Contract> queryAllContract();

    int deleteContract(@Param("id") Long id);
}
