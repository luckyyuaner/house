package com.yuan.house.service.impl;

import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.dao.ContractDao;
import com.yuan.house.model.Contract;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    WebSocketConfig webSocketConfig;

    @Autowired
    private CommonService commonService;

    @Override
    public Contract queryContractById(Long cid) {
        String key = "contract_" + cid;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (Contract)rs;
        }
        Contract c = contractDao.queryContractById(cid);
        commonService.insertRedis(key, c);
        return c;
    }

}
