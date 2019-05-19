package com.yuan.house.dao;

import com.yuan.house.model.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface HouseDao {

	/**
	 * 添加房源
	 * @param object
	 * @return
	 */
	int addHouse(@Param("object") House object);

    /**
     * 根据用户id获取该用户所有房源
     * @param userId
     * @return
     */
	List<House> queryHousesByUserId(@Param("uid") Long userId);

    /**
     * 根据id查询house
     * @param id
     * @return
     */
	House queryHouseById(@Param("id") Long id);

    /**
     * 更新房源
     * @param object
     * @return
     */
    int updateHouse(@Param("object") House object);

    /**
     * 删除房源
     * @param id
     * @return
     */
    int deleteHouse(@Param("id") Long id);
}
