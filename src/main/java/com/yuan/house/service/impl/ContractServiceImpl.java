package com.yuan.house.service.impl;

import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.constants.Constants;
import com.yuan.house.dao.ContractDao;
import com.yuan.house.model.Contract;
import com.yuan.house.model.User;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.ContractService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void createNewContractByTenant(Contract contract) {
        contract.setTenantOperation(2);
        contract.setType(0);
        contract.setStatus(0);
        contractDao.createNewContractByTenant(contract);
    }

    @Override
    public int updateContractByTenant(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setTenantOperation(2);
        contract.setType(0);
        contract.setStatus(0);
        contract.setLandlordOperation(0);
        return contractDao.updateContractByTenant(contract);
    }

    @Override
    public int updateContractByTenant2(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setTenantOperation(1);
        contract.setType(0);
        contract.setStatus(2);
        return contractDao.updateContractByTenant2(contract);
    }

    @Override
    public int updateContractByLandlordWithAgree(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setLandlordOperation(2);
        contract.setType(0);
        contract.setStatus(1);
        return contractDao.updateContractByLandlordWithAgree(contract);
    }

    @Override
    public int updateContractByLandlordWithRefuse(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setLandlordOperation(3);
        contract.setType(0);
        contract.setStatus(0);
        return contractDao.updateContractByLandlordWithRefuse(contract);
    }

    @Override
    public int updateContractByManagerWithAgree(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setLandlordOperation(0);
        contract.setTenantOperation(0);
        contract.setType(0);
        contract.setStatus(2);
        return contractDao.updateContractByManagerWithAgree(contract);
    }

    @Override
    public int updateContractByManagerWithRefuse(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setLandlordOperation(0);
        contract.setTenantOperation(0);
        contract.setType(0);
        contract.setStatus(0);
        return contractDao.updateContractByManagerWithRefuse(contract);
    }

    @Override
    public int updateContractByLandlord2(Contract contract) {
        String key = "contract_" + contract.getContractId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        contract.setLandlordOperation(1);
        contract.setType(0);
        contract.setStatus(2);
        return contractDao.updateContractByLandlord2(contract);
    }

    @Override
    public List<Contract> queryContractsByTenant(int number, int sta) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        String key = "contracts_tenant_"+user.getUserId()+"_number_" + number +"_sta_" + sta;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Contract>)rs;
        }
        List<Contract> cs = contractDao.queryContractsByTenant(user.getUserId(), sta);
        commonService.insertRedis(key, cs);
        return cs;
    }

    @Override
    public List<Contract> queryContractsByLandlord(int number, int sta) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        String key = "contracts_user_"+user.getUserId()+"_number_" + number + "_sta_" + sta;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Contract>)rs;
        }
        List<Contract> cs = contractDao.queryContractsByLandlord(user.getUserId(), sta);
        commonService.insertRedis(key, cs);
        return cs;
    }

    @Override
    public List<Contract> queryAllContract(int number) {
        String key = "contracts_all_number_" + number;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Contract>)rs;
        }
        List<Contract> cs = contractDao.queryAllContract();
        commonService.insertRedis(key, cs);
        return cs;
    }

    @Override
    public int deleteContractById(Long cid) {
        String key = "contract_" + cid;
        commonService.deleteRedis(key);
        commonService.deleteByPrex("contracts_");
        return contractDao.deleteContract(cid);
    }

}
