package com.yuan.house.service.impl;

import com.yuan.house.POJO.TenantSearchPOJO;
import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.constants.Constants;
import com.yuan.house.dao.HouseDao;
import com.yuan.house.model.House;
import com.yuan.house.model.User;
import com.yuan.house.service.CommonService;
import com.yuan.house.service.HouseService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class HouseServiceImpl implements HouseService {

	@Autowired
    private HouseDao houseDao;

    @Autowired
    WebSocketConfig webSocketConfig;

    @Autowired
    private CommonService commonService;

    @Override
    public void addHouse(House object) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        object.setUserId(user.getUserId());
        object.setGrade(5.0);
        houseDao.addHouse(object);
    }

    @Override
    public List<House> getHousesByUser() {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        String key = "houses_user_" + user.getUserId();
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<House>)rs;
        }
        List<House> houses = houseDao.queryHousesByUserId(user.getUserId());
        commonService.insertRedis(key, houses);
        return houses;
    }

    @Override
    public House queryHouseById(Long id) {
        String key = "house_" + id;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (House)rs;
        }
        House h = houseDao.queryHouseById(id);
        commonService.insertRedis(key, h);
        return h;
    }

    @Override
    public int updateHouse(House object) {
        String key = "house_" + object.getHouseId();
        commonService.deleteRedis(key);
        commonService.deleteByPrex("houses");
        return houseDao.updateHouse(object);
    }

    @Override
    public int deleteHouse(Long id) {
        String key = "house_" + id;
        commonService.deleteRedis(key);
        commonService.deleteByPrex("houses");
        return houseDao.deleteHouse(id);
    }

    @Override
    public List<House> queryHousesLikeMsg(TenantSearchPOJO ts) {
        String key = "houses_like_" + ts.toString();
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<House>)rs;
        }
        List<House> houses = houseDao.queryHousesLikeMsg(ts);
        commonService.insertRedis(key, ts.toString());
        return null;
    }
}
