package com.yyds.service;

import com.yyds.dao.StudentScoreInfoDao;
import com.yyds.domain.StudentScoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private StudentScoreInfoDao studentScoreInfoDao;

    public List<StudentScoreInfo> qryScoreByUser(Long userId) {

        return studentScoreInfoDao.selectScoreInfoByUser(userId);
    }
}
