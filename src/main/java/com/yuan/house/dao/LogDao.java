package com.yuan.house.dao;


import com.yuan.house.model.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogDao {

    void addLog(@Param("object") Log object);

    List<Log> queryAllLogs();
}
