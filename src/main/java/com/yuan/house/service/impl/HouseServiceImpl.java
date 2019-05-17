package com.yuan.house.service.impl;

import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.constants.Constants;
import com.yuan.house.dao.HouseDao;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.HouseService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class HouseServiceImpl implements HouseService {

	@Autowired
    private HouseDao houseDao;

    @Autowired
    WebSocketConfig webSocketConfig;

    @Override
    public void addHouse(House object) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        object.setUserId(user.getUserId());
        object.setGrade(5.0);
        houseDao.addHouse(object);
    }
}
