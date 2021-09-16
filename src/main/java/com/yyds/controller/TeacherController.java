package com.yyds.controller;

import com.yyds.domain.TeacherBaseInfoPage;
import com.yyds.domain.TeacherSubjectInfo;
import com.yyds.domain.UserBaseInfo;
import com.yyds.service.BaseInfoService;
import com.yyds.service.TeacherInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private TeacherInfoService teacherInfoService;

    @Autowired
    private BaseInfoService baseInfoService;

    @RequestMapping("/teacherInfo")
    public String teacherInfo(@RequestParam(value = "pageNo", required = false) Integer pageNo, Model model) {
        if(pageNo == null) pageNo = 1;
        if(pageNo == 0) pageNo = 1;

        TeacherBaseInfoPage teacherBaseInfoPage = teacherInfoService.getTeacherBaseInfo(pageNo);
        model.addAttribute("teacherBaseInfoPage", teacherBaseInfoPage);

        return "teacherInfoPage";
    }

    @RequestMapping("/deleteTeacher")
    public String deleteTeacher(@RequestParam("userId") Long userId, @RequestParam("nowPage") Integer nowPage) {
        //删除老师教学表中的相关学科信息，
        baseInfoService.deleteUserBaseInfo(userId);
        return "forward:teacherInfo?pageNo=" + nowPage;
    }

    @RequestMapping("/addTeacher")
    public String addTeacher() {
        return "addTeacherPage";
    }

    @RequestMapping("/uploadTeacher")
    public String uploadTeacher(@RequestParam("email") String email,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone) {
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setEmail(email);
        userBaseInfo.setName(name);
        userBaseInfo.setPhone(phone);
        userBaseInfo.setPassword("63a9f0ea7bb98050796b649e85481845");
        userBaseInfo.setPosition(1);
        userBaseInfo.setThumb("https://syimg.3dmgame.com/uploadimg/upload/image/20210413/20210413154821_24226.jpg");
        boolean result = baseInfoService.insertBaseInfo(userBaseInfo);

        return "addTeacherPage";
    }

    @RequestMapping("/changeTeacher")
    public String changeTeacher(@RequestParam("userId") Long userId,
                                @RequestParam("email") String email,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone,
                                Model model) {
        System.out.println("获取修改教师信息页面");
        model.addAttribute("userId", userId);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);

        return "changeTeacherPage";
    }

    @RequestMapping("/reloadTeacher")
    public String changeTeacher(@RequestParam("userId") Long userId,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone) {
        System.out.println("更改教师信息");
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setName(name);
        userBaseInfo.setPhone(phone);
        baseInfoService.updateUserInfo(userBaseInfo);

        return "forward:teacherInfo?pageNo=1";
    }

    @RequestMapping("/detailTeacher")
    public String detailTeacher(@RequestParam("userId") Long userId,
                                @RequestParam("name") String name,
                                Model model) {
        //查询所教学科和所教人数
        List<TeacherSubjectInfo> subjectInfos = teacherInfoService.getTeacherSubjectInfo(userId);
        model.addAttribute("subjectInfos", subjectInfos);
        model.addAttribute("name", name);
        model.addAttribute("userId", userId);

        return "detailTeacherPage";
    }

    @RequestMapping("/deleteSubject")
    public String deleteSubject(@RequestParam("userId") Long userId,
                                @RequestParam("subject") String subject,
                                @RequestParam("name") String name,
                                Model model) {

        teacherInfoService.deleteTeacherSubjectInfo(userId, subject);

        model.addAttribute("userId", userId);
        model.addAttribute("name", name);

        return "forward:detailTeacher";
    }

    @RequestMapping("/addSubject")
    public String addSubject(@Param("userId") Long userId, @Param("name") String name, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("name", name);
        return "addSubjectPage";
    }

    @RequestMapping("/reAddSubject")
    public String reAddSubject(@Param("userId") Long userId,
                               @Param("subject") String subject,
                               @Param("name") String name,
                               Model model) {
        Boolean result = teacherInfoService.insertSubject(userId, subject);
        if(result) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
        model.addAttribute("name", name);
        model.addAttribute("userId", userId);

        System.out.println(name);

        return "forward:detailTeacher";
    }
}
