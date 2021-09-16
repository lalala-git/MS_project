package com.yyds.controller;

import com.yyds.domain.StudentBaseInfoPage;
import com.yyds.domain.UserBaseInfo;
import com.yyds.service.BaseInfoService;
import com.yyds.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping("/studentInfo")
    public String studentInfo(@RequestParam(value = "pageNo", required = false) Integer pageNo, Model model) {
        if(pageNo == null) pageNo = 1;
        if(pageNo == 0) pageNo = 1;
        StudentBaseInfoPage studentBaseInfoPage = studentInfoService.getStudentBaseInfo(pageNo);
        model.addAttribute("studentBaseInfoPage", studentBaseInfoPage);

        return "studentMsgPage";
    }

    @RequestMapping("/addStudent")
    public String addTeacher() {
        System.out.println("添加学生信息");
        return "addStudentPage";
    }

    @RequestMapping("/uploadStudent")
    public String uploadStudent(@RequestParam("email") String email,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone) {
        System.out.println("向数据库中添加学生数据");
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setEmail(email);
        userBaseInfo.setName(name);
        userBaseInfo.setPhone(phone);
        userBaseInfo.setPassword("63a9f0ea7bb98050796b649e85481845");
        userBaseInfo.setPosition(2);
        userBaseInfo.setThumb("https://syimg.3dmgame.com/uploadimg/upload/image/20210413/20210413154821_24226.jpg");
        boolean result = baseInfoService.insertBaseInfo(userBaseInfo);

        return "addStudentPage";
    }

    @RequestMapping("/deleteStudent")
    public String deleteStudent(@RequestParam("userId") Long userId, @RequestParam("nowPage") Integer nowPage) {
        //删除老师教学表中的相关学科信息，
        baseInfoService.deleteUserBaseInfo(userId);

        return "forward:studentInfo?pageNo=" + nowPage;
    }

    @RequestMapping("/changeStudent")
    public String changeStudent(@RequestParam("userId") Long userId,
                                @RequestParam("email") String email,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone,
                                Model model) {
        System.out.println("获取修改学生信息页面");
        model.addAttribute("userId", userId);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);

        return "changeStudentPage";
    }

    @RequestMapping("/reloadStudent")
    public String reloadStudent(@RequestParam("userId") Long userId,
                                @RequestParam("name") String name,
                                @RequestParam("phone") String phone) {
        System.out.println("更改学生信息");
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setUserId(userId);
        userBaseInfo.setName(name);
        userBaseInfo.setPhone(phone);
        baseInfoService.updateUserInfo(userBaseInfo);

        return "forward:studentInfo?pageNo=1";
    }

}
