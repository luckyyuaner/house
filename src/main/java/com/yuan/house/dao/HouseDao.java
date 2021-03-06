package com.yuan.house.dao;

import com.yuan.house.POJO.TenantSearchPOJO;
import com.yuan.house.VO.HouseManagerVO;
import com.yuan.house.VO.MapHouseVO;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
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
     * 根据条件模糊查询房源
     * @param object
     * @return
     */
	List<House> queryHousesLikeMsg(@Param("object") TenantSearchPOJO object);

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

    int updateHouseStatus(@Param("hid")Long hid, @Param("status")int status);

    /**
     * 删除房源
     * @param id
     * @return
     */
    int deleteHouse(@Param("id") Long id);

    User queryLandlordByHouse(@Param("hid") Long hid);

	List<MapHouseVO> queryHousesByCity(@Param("city") String city);

	List<HouseManagerVO> getAllHouses();
}
