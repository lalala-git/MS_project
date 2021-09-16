package com.yyds.dao;

import com.yyds.domain.TeacherSubjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherSubjectInfoDao {

    List<TeacherSubjectInfo> selectSubjectByUser(@Param("userId") Long userId) ;

    int deleteSubject(@Param("userId") Long userId, @Param("subject") String subject);

    int insertSubject(@Param("userId") Long userId, @Param("subject") String subject);
}
