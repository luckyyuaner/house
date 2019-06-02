package com.yuan.house.service.impl;

import com.yuan.house.POJO.TenantSearchPOJO;
import com.yuan.house.VO.HouseManagerVO;
import com.yuan.house.VO.MapHouseVO;
import com.yuan.house.config.websocket.WebSocketConfig;
import com.yuan.house.constants.Constants;
import com.yuan.house.dao.CollectDao;
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
    private CollectDao collectDao;

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
    public int updateHouseStatus(Long hid, int status) {
        String key = "house_" + hid;
        commonService.deleteRedis(key);
        commonService.deleteByPrex("houses");
        return houseDao.updateHouseStatus(hid, status);
    }

    @Override
    public int deleteHouse(Long id) {
        String key = "house_" + id;
        commonService.deleteRedis(key);
        commonService.deleteByPrex("houses");
        return houseDao.deleteHouse(id);
    }

    @Override
    public List<House> queryHousesLikeMsg(TenantSearchPOJO ts, int number) {
        String key = "houses_like_" + ts.toString()+"_number_"+number;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<House>)rs;
        }
        List<House> houses = houseDao.queryHousesLikeMsg(ts);
        commonService.insertRedis(key, houses);
        return houses;
    }

    @Override
    public List<MapHouseVO> queryHousesByCity(String city) {
        String key = "houses_map_like_" + city;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<MapHouseVO>)rs;
        }
        List<MapHouseVO> houses = houseDao.queryHousesByCity(city);
        commonService.insertRedis(key, houses);
        return houses;
    }

    @Override
    public User queryLandlordByHouse(Long hid) {
        String key = "user_house_" + hid;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (User)rs;
        }
        User u = houseDao.queryLandlordByHouse(hid);
        commonService.insertRedis(key, u);
        return u;
    }

    @Override
    public void addHouseCollect(Long hid, Long uid) {
        Long cid = collectDao.queryCollectByUserAndHouse(hid, uid);
        if(cid == null) {
            collectDao.addHouseCollect(hid, uid);
        }
    }

    @Override
    public int deleteHouseCollect(Long hid, Long uid) {
        return collectDao.deleteHouseCollect(hid, uid);
    }

    @Override
    public List<Long> queryCollectHouseIdsByUser(Long uid) {
        String key = "houses_collect_hids_" + uid;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<Long>)rs;
        }
        List<Long> cids = collectDao.queryCollectHouseIdsByUser(uid);
        commonService.insertRedis(key, cids);
        return cids;
    }

    @Override
    public List<House> queryHousesByCollect() {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        String key = "houses_collect_houses_" + user.getUserId();
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<House>)rs;
        }
        List<House> houses = collectDao.queryHousesByCollect(user.getUserId());
        commonService.insertRedis(key, houses);
        return houses;
    }

    @Override
    public List<HouseManagerVO> getAllHouses(int number) {
        String key = "houses_user_number_"+number;
        Object rs = commonService.queryRedis(key);
        if(null != rs) {
            return (List<HouseManagerVO>)rs;
        }
        List<HouseManagerVO> houses = houseDao.getAllHouses();
        commonService.insertRedis(key, houses);
        return houses;
    }
}
