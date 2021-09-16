package com.yyds.service;

import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.UserBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseInfoService {

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    public void updateBaseInfo(UserBaseInfo userBaseInfo) {
        userBaseInfoDao.updateBaseInfoByEmail(userBaseInfo);
    }

    //删除教师信息
    public void deleteUserBaseInfo(Long userId) {
        userBaseInfoDao.deleteBaseInfo(userId);
    }

    //添加用户
    public boolean insertBaseInfo(UserBaseInfo userBaseInfo) {
        try {
            int res = userBaseInfoDao.insertBaseInfo(userBaseInfo);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //更改teacher信息
    public void updateUserInfo(UserBaseInfo userBaseInfo) {
        userBaseInfoDao.updateTeacherByUserId(userBaseInfo);
    }

}
