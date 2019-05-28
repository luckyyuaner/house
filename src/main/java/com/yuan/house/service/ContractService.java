package com.yuan.house.service;


import com.yuan.house.model.Contract;

public interface ContractService {
    /**
     * 根据id查询合同
     * @param cid
     * @return
     */
    Contract queryContractById(Long cid);

}
