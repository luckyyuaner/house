package com.yuan.house.service.impl;

import com.yuan.house.POJO.TenantContractPOJO;
import com.yuan.house.dao.CommentDao;
import com.yuan.house.model.Comment;
import com.yuan.house.model.Contract;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.HouseService;
import com.yuan.house.service.UserService;
import com.yuan.house.util.LoggerUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentDao commentDao;

    @Override
    public Object queryRedis(String key) {
        boolean exist = redisTemplate.hasKey(key);
        if(exist) {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            LoggerUtil.info("redis缓存中存在{}", key);
            return operations.get(key);
        }
        LoggerUtil.info("redis缓存中不存在{}", key);
        return null;
    }

    @Override
    public void deleteRedis(String key) {
        boolean exist = redisTemplate.hasKey(key);
        if(exist) {
            LoggerUtil.info("redis缓存中删除{}", key);
            redisTemplate.delete(key);
        }
    }

    @Override
    public void deleteByPrex(String prex) {
        String key = prex+"**";
        Set<String> keys = redisTemplate.keys(key);
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void insertRedis(String key, Object object) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, object, 10, TimeUnit.SECONDS);
        LoggerUtil.info("redis缓存中添加{}", key);
    }

    @Override
    public TenantContractPOJO createTenantContractPOJO(Contract contract) {
        TenantContractPOJO tenantContractPOJO = new TenantContractPOJO();
        tenantContractPOJO.setContract(contract);
        House house = houseService.queryHouseById(contract.getHouseId());
        tenantContractPOJO.setHouse(house);
        User landlord = houseService.queryLandlordByHouse(house.getHouseId());
        tenantContractPOJO.setLandlord(landlord);
        User tenant = userService.queryUserById(contract.getUserId());
        tenantContractPOJO.setTenant(tenant);
        List<Comment> comments = commentDao.queryCommentsByContract(contract.getContractId());
        tenantContractPOJO.setComments(comments);
        return tenantContractPOJO;
    }
}
