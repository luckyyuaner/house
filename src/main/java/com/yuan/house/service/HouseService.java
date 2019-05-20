package com.yuan.house.service;

import com.yuan.house.model.House;

import java.util.List;


public interface HouseService {

	/**
	 * 添加房源
	 * @param object
	 * @return
	 */
	void addHouse(House object);

    /**
     * 获取当前用户所有房源
     * @return
     */
    List<House> getHousesByUser();

    /**
     * 根据房源id查询
     * @param id
     * @return
     */
    House queryHouseById(Long id);

    /**
     * 更新房源
     * @param object
     * @return
     */
    int updateHouse(House object);

    /**
     * 删除房源
     * @param id
     * @return
     */
    int deleteHouse(Long id);
}