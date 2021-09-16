package com.yyds.service;

import cn.hutool.crypto.SecureUtil;
import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.UserBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    public UserBaseInfo loginProcess(String email, String password) {
        if(email == null) {
            return null;
        }
        if(password == null) {
            return null;
        }

        String securePassword = SecureUtil.md5(password);
        UserBaseInfo userBaseInfo = userBaseInfoDao.selectUserByEmail(email);
        System.out.println("LoginService获取用户信息：" + userBaseInfo);

        if(userBaseInfo == null) {
            return null;
        }
        if(!securePassword.equals(userBaseInfo.getPassword())) {
            return null;
        }

        return userBaseInfo;
    }

    public UserBaseInfo checkEmailAndPassword(String email, String password) {
        UserBaseInfo userBaseInfo = userBaseInfoDao.selectUserByEmail(email);
        if(userBaseInfo != null && password.equals(userBaseInfo.getPassword())) {
            return userBaseInfo;
        }
        return null;
    }
}
