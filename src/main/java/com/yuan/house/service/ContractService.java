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

    int updateContractByTenant2(Contract contract);

    int updateContractByTenant4(Contract contract);

    int updateContractByLandlordWithAgree(Contract contract);

    int updateContractByLandlordWithAgree2(Contract contract);

    int updateContractByLandlordWithRefuse(Contract contract);

    int updateContractByLandlordWithRefuse2(Contract contract);

    int updateContractByManagerWithAgree(Contract contract);

    int updateContractByManagerWithAgree4(Contract contract);

    int updateContractByManagerWithRefuse(Contract contract);

    int updateContractByManagerWithRefuse2(Contract contract);

    int updateContractByManagerWithRefuse4(Contract contract);

    int updateContractByManagerWithAgree2(Contract contract);

    int updateContractByLandlord2(Contract contract);

    int payMoneyContract(Long cid);

    int getMoneyContract(Long cid);

    int checkMoneyContract(Long cid);
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
    List<Contract> queryContractsByLandlord(int number, int sta);

    List<Contract> queryAllContract(int number);
    
    int deleteContractById(Long cid);
}
