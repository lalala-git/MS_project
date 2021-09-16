package com.yyds.dao;

import com.yyds.domain.StudentScoreInfo;
import com.yyds.domain.StudentSubjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface StudentScoreInfoDao {

    Integer selectCountBySubject(@Param("subject") String subject, @Param("teacherUserId") Long teacherUserId);

    List<StudentSubjectInfo> selectScoreList(@Param("teacherUserId") Long userId, @Param("subject") String subject) ;

    int insertSubject(@Param("studentScoreInfo") StudentScoreInfo studentScoreInfo) ;

    int deleteSubjectFor(@Param("userId") Long userId, @Param("subject") String subject);

    int updateScoreFor(@Param("userId") Long userId, @Param("subject") String subject, @Param("score") Integer score);

    List<StudentScoreInfo> selectScoreInfoByUser(@Param("userId") Long userId);
}
