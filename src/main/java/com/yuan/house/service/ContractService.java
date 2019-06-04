package com.yuan.house.service;


import com.yuan.house.model.Contract;

import java.util.List;

public interface ContractService {
    /**
     * 根据id查询合同
     * @param cid
     * @return
     */
    Contract queryContractById(Long cid);

    /**
     *租客创建租房申请
     * @param contract
     */
    void createNewContractByTenant(Contract contract);

    int updateContractByTenant(Contract contract);

    int updateContractByLandlordWithAgree(Contract contract);

    int updateContractByLandlordWithRefuse(Contract contract);

    int updateContractByManagerWithAgree(Contract contract);

    int updateContractByManagerWithRefuse(Contract contract);

    /**
     * 分页查询租客的合同
     * @param number
     * @return
     */
    List<Contract> queryContractsByTenant(int number, int sta);

    /**
     * 分页查询房东的合同
     * @param number
     * @return
     */
    List<Contract> queryContractsByLandlord(int number);

    List<Contract> queryAllContract(int number);
    
    int deleteContractById(Long cid);
}
