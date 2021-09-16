package com.yyds.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.StudentBaseInfo;
import com.yyds.domain.StudentBaseInfoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoService {

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    @Value("${pageSize}")
    private Integer PAGE_SIZE;

    public StudentBaseInfoPage getStudentBaseInfo(Integer pageNo) {
        PageHelper.startPage(pageNo, PAGE_SIZE);
        List<StudentBaseInfo> studentBaseInfos = userBaseInfoDao.selectAllStudent();
        PageInfo<StudentBaseInfo> pageInfo = new PageInfo<>(studentBaseInfos);

        if(pageInfo.getPages() < pageNo) {
            PageHelper.startPage(pageInfo.getPages(), PAGE_SIZE);
            studentBaseInfos = userBaseInfoDao.selectAllStudent();
            pageInfo = new PageInfo<>(studentBaseInfos);
        }

        List<StudentBaseInfo> targetStudents = pageInfo.getList();
        Boolean isFirstPage = pageInfo.isIsFirstPage();
        Boolean isLastPage = pageInfo.isIsLastPage();
        Integer prePage = pageInfo.getPrePage();
        Integer nowPage = pageInfo.getPageNum();
        Integer nextPage = pageInfo.getNextPage();
        Integer lasPage = pageInfo.getPages();

        StudentBaseInfoPage studentBaseInfoPage = new StudentBaseInfoPage();
        studentBaseInfoPage.setStudentBaseInfos(targetStudents);
        studentBaseInfoPage.setIsFirstPage(isFirstPage);
        studentBaseInfoPage.setIsLastPage(isLastPage);
        studentBaseInfoPage.setNowPage(nowPage);
        studentBaseInfoPage.setPrePage(prePage);
        studentBaseInfoPage.setNextPage(nextPage);
        studentBaseInfoPage.setLastPage(lasPage);
        return studentBaseInfoPage;
    }

}
