package com.yuan.house.dao;

import com.yuan.house.model.House;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectDao {

	int addHouseCollect(@Param("hid")Long hid, @Param("uid")Long uid);

    int deleteHouseCollect(@Param("hid")Long hid, @Param("uid")Long uid);

    Long queryCollectByUserAndHouse(@Param("hid")Long hid, @Param("uid")Long uid);

    List<Long> queryCollectHouseIdsByUser(@Param("uid") Long uid);

    List<House> queryHousesByCollect(@Param("uid") Long uid);
}
