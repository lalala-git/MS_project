package com.yyds.controller;

import com.yyds.domain.UserBaseInfo;
import com.yyds.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private BaseInfoService baseInfoService;

    @RequestMapping("/main")
    public String main() {
        System.out.println("MainController获取首页");
        return "mainPage";
    }

    @RequestMapping("/baseInfo")
    public String baseInfo() {
        System.out.println("MainController获取个人基本信息");
        return "baseInfoPage";
    }

    @RequestMapping("/changeInfo")
    public String changeInfo() {
        System.out.println("MainController更改个人基本信息");
        return "changeInfoPage";
    }

    @RequestMapping("/toChangeInfo")
    public String toChangeInfo(HttpSession session,
                               @RequestParam("thumb") String thumb,
                               @RequestParam("name") String name,
                               @RequestParam("age") Integer age,
                               @RequestParam("sex") String sex,
                               @RequestParam("bir") String bir,
                               @RequestParam("constellation") String constellation,
                               @RequestParam("phone") String phone) {
        System.out.println("MainController更改数据库中个人基本信息");
        /*获取此时的email*/
        UserBaseInfo user = (UserBaseInfo) session.getAttribute("user");
        String email = user.getEmail();
        /*更改database中的值*/
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        userBaseInfo.setEmail(email);
        userBaseInfo.setName(name);
        userBaseInfo.setThumb(thumb);
        userBaseInfo.setAge(age);
        userBaseInfo.setSex(sex);
        System.out.println(bir);
        userBaseInfo.setBir(bir);
        userBaseInfo.setConstellation(constellation);
        userBaseInfo.setPhone(phone);
        baseInfoService.updateBaseInfo(userBaseInfo);
        /*更改session中的值*/
        user.setThumb(thumb);
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        user.setBir(bir);
        user.setConstellation(constellation);
        user.setPhone(phone);

        return "baseInfoPage";
    }

}
