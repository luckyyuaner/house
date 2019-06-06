package com.yuan.house.service.impl;

import com.yuan.house.constants.Constants;
import com.yuan.house.dao.CommentDao;
import com.yuan.house.dao.ContractDao;
import com.yuan.house.dao.HouseDao;
import com.yuan.house.model.Comment;
import com.yuan.house.model.Contract;
import com.yuan.house.model.User;
import com.yuan.house.service.CommentService;
import com.yuan.house.service.CommonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private CommonService commonService;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addCommentByTenant(Comment comment) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute(Constants.SESSION_CURR_USER);
        comment.setUserId(user.getUserId());
        Contract contract = new Contract();
        contract.setContractId(comment.getContractId());
        contract.setType(0);
        contract.setStatus(4);
        contract.setTenantOperation(1);
        contractDao.updateContractByTenant3(contract);
        if(comment.getHouseGrade() != 0) {
            int count1= commentDao.queryHouseCommentCount(comment.getContractId());
            commentDao.updateHouseGrade(comment.getContractId(), comment.getHouseGrade(), (double)count1);
            Long hid = contractDao.queryHouseIDByContract(comment.getContractId());
            User landlord = houseDao.queryLandlordByHouse(hid);
            int count2 = commentDao.queryLandlordCommentCount(user.getUserId());
            commentDao.updateLandlordGrade(comment.getContractId(), comment.getHouseGrade(), (double)count2, user.getUserId());
        }
        commentDao.addCommentByTenant(comment);
    }

    @Override
    public int deleteComment(Long cid) {
        String key = "comment_" + cid;
        commonService.deleteRedis(key);
        commonService.deleteByPrex("comments");
        return commentDao.deleteComment(cid);
    }
}
