package com.yyds.dao;

import com.yyds.domain.StudentBaseInfo;
import com.yyds.domain.TeacherBaseInfo;
import com.yyds.domain.UserBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserBaseInfoDao {

    /*通过Email查询一条数据*/
    UserBaseInfo selectUserByEmail(@Param("email") String email);

    /*通过email修改密码*/
    int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    /*通过email修改所有信息*/
    int updateBaseInfoByEmail(@Param("user") UserBaseInfo userBaseInfo);

    /*查看所有老师信息*/
    List<TeacherBaseInfo> selectAllTeacher();

    /*删除教师*/
    int deleteBaseInfo(@Param("userId") Long userId);

    /*插入新增用户*/
    int insertBaseInfo(@Param("userBaseInfo") UserBaseInfo userBaseInfo);

    /*通过userId更改相关信息*/
    int updateTeacherByUserId(@Param("user") UserBaseInfo userBaseInfo);

    List<StudentBaseInfo> selectAllStudent();
}
