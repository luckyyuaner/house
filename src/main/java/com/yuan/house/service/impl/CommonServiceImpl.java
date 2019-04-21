package com.yuan.house.service.impl;

import com.yuan.house.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object queryRedis(String key) {
        boolean exist = redisTemplate.hasKey(key);
        if(exist) {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        }
        return null;
    }

    @Override
    public void deleteRedis(String key) {
        boolean exist = redisTemplate.hasKey(key);
        if(exist) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public void insertRedis(String key, Object object) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, object, 10, TimeUnit.SECONDS);
    }
}
