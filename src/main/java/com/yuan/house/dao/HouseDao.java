package com.yuan.house.dao;

import com.yuan.house.model.House;
import org.apache.ibatis.annotations.Param;


public interface HouseDao {

	/**
	 * 添加房源
	 * @param object
	 * @return
	 */
	int addHouse(@Param("object") House object);
}
