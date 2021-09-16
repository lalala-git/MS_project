package com.yyds.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yyds.dao.StudentScoreInfoDao;
import com.yyds.dao.TeacherSubjectInfoDao;
import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectDetailService {

    @Autowired
    private StudentScoreInfoDao studentScoreInfoDao;

    @Autowired
    private TeacherSubjectInfoDao teacherSubjectInfoDao;

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    @Value("${pageSize}")
    private Integer PAGE_SIZE;

    public List<SubjectDetail> querySubjectDetail(Long userId) {
        //查询该老师当前所教的所有课程名
        List<TeacherSubjectInfo> subjectInfos = teacherSubjectInfoDao.selectSubjectByUser(userId);

        List<SubjectDetail> subjectDetails = new ArrayList<>();
        for(TeacherSubjectInfo subjectInfo : subjectInfos) {
            //通过课程名和用户Id查询所教人数
            Integer count = studentScoreInfoDao.selectCountBySubject(subjectInfo.getSubject(), userId);
            if(count == null) count = 0;

            SubjectDetail subjectDetail = new SubjectDetail();
            subjectDetail.setCount(count);
            subjectDetail.setSubject(subjectInfo.getSubject());
            subjectDetails.add(subjectDetail);
        }
        return subjectDetails;
    }

    public StudentSubjectPage querySubjectStudent(Long userId, String subject, Integer pageNo) {

        PageHelper.startPage(pageNo, PAGE_SIZE);
        List<StudentSubjectInfo> studentSubjectInfos = studentScoreInfoDao.selectScoreList(userId, subject);
        PageInfo<StudentSubjectInfo> pageInfo = new PageInfo<>(studentSubjectInfos);

        if(pageInfo.getPages() < pageNo) {
            PageHelper.startPage(pageInfo.getPages(), PAGE_SIZE);
            studentSubjectInfos = studentScoreInfoDao.selectScoreList(userId, subject);
            pageInfo = new PageInfo<>(studentSubjectInfos);
        }

        List<StudentSubjectInfo> targetSubjectInfo = pageInfo.getList();
        Boolean isFirstPage = pageInfo.isIsFirstPage();
        Boolean isLastPage = pageInfo.isIsLastPage();
        Integer prePage = pageInfo.getPrePage();
        Integer nowPage = pageInfo.getPageNum();
        Integer nextPage = pageInfo.getNextPage();
        Integer lasPage = pageInfo.getPages();

        StudentSubjectPage studentSubjectPage = new StudentSubjectPage();
        studentSubjectPage.setStudentSubjectInfos(targetSubjectInfo);
        studentSubjectPage.setIsFirstPage(isFirstPage);
        studentSubjectPage.setIsLastPage(isLastPage);
        studentSubjectPage.setNowPage(nowPage);
        studentSubjectPage.setPrePage(prePage);
        studentSubjectPage.setNextPage(nextPage);
        studentSubjectPage.setLastPage(lasPage);

        return studentSubjectPage;
    }

    public void addSubjectForTeacher(Long teacherUserId, String subject, String email) {
        try {
            //通过email查询useId
            UserBaseInfo userBaseInfo = userBaseInfoDao.selectUserByEmail(email);
            Long userId = userBaseInfo.getUserId();

            //插入数据userId, subject, null, teacherUserId
            StudentScoreInfo studentScoreInfo = new StudentScoreInfo(userId, subject, 0, teacherUserId);
            studentScoreInfoDao.insertSubject(studentScoreInfo);
        } catch (Exception e) {
            System.out.println("插入错误");
        }
    }

    public void deleteSubjectFor(Long userId, String subject) {

        studentScoreInfoDao.deleteSubjectFor(userId, subject);

    }

    public void updateScoreFor(Long userId, String subject, Integer score) {
        studentScoreInfoDao.updateScoreFor(userId, subject, score);
    }

}
