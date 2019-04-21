package com.yuan.house.service;

/**
 * 公共Service
 */
public interface CommonService {

    /**
     * redis查询操作
     * @param key
     * @return
     */
    Object queryRedis(String key);

    /**
     * redis删除操作
     * @param key
     * @return
     */
    void deleteRedis(String key);

    /**
     * redis添加操作
     * @param key
     * @param object
     * @return
     */
    void insertRedis(String key, Object object);
}
