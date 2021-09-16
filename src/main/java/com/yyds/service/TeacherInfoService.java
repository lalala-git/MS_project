package com.yyds.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yyds.dao.TeacherSubjectInfoDao;
import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.TeacherBaseInfo;
import com.yyds.domain.TeacherBaseInfoPage;
import com.yyds.domain.TeacherSubjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherInfoService {

    @Value("${pageSize}")
    private Integer PAGE_SIZE;

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    @Autowired
    private TeacherSubjectInfoDao teacherSubjectInfoDao;

    public TeacherBaseInfoPage getTeacherBaseInfo(Integer pageNo) {
        PageHelper.startPage(pageNo, PAGE_SIZE);
        List<TeacherBaseInfo> teacherBaseInfos = userBaseInfoDao.selectAllTeacher();
        PageInfo<TeacherBaseInfo> pageInfo = new PageInfo<>(teacherBaseInfos);

        if(pageInfo.getPages() < pageNo) {
            PageHelper.startPage(pageInfo.getPages(), PAGE_SIZE);
            teacherBaseInfos = userBaseInfoDao.selectAllTeacher();
            pageInfo = new PageInfo<>(teacherBaseInfos);
        }
        List<TeacherBaseInfo> targetTeachers = pageInfo.getList();
        Boolean isFirstPage = pageInfo.isIsFirstPage();
        Boolean isLastPage = pageInfo.isIsLastPage();
        Integer prePage = pageInfo.getPrePage();
        Integer nowPage = pageInfo.getPageNum();
        Integer nextPage = pageInfo.getNextPage();
        Integer lasPage = pageInfo.getPages();

        TeacherBaseInfoPage teacherBaseInfoPage = new TeacherBaseInfoPage();
        teacherBaseInfoPage.setTeacherBaseInfos(targetTeachers);
        teacherBaseInfoPage.setIsFirstPage(isFirstPage);
        teacherBaseInfoPage.setIsLastPage(isLastPage);
        teacherBaseInfoPage.setNowPage(nowPage);
        teacherBaseInfoPage.setPrePage(prePage);
        teacherBaseInfoPage.setNextPage(nextPage);
        teacherBaseInfoPage.setLastPage(lasPage);
        return teacherBaseInfoPage;
    }

    public List<TeacherSubjectInfo> getTeacherSubjectInfo(Long userId) {
        //学科名，学科人数
         return teacherSubjectInfoDao.selectSubjectByUser(userId);
    }

    public void deleteTeacherSubjectInfo(Long userId, String subject) {
        teacherSubjectInfoDao.deleteSubject(userId, subject);
    }

    public boolean insertSubject(Long userId, String subject) {
        try {
            teacherSubjectInfoDao.insertSubject(userId, subject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
