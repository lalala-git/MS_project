package com.yyds.controller;

import com.yyds.service.ForgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 忘记修改密码
 */
@Controller
public class ForgetController {

    @Autowired
    private ForgetService forgetService;

    //获取修改密码页面
    @RequestMapping("/forget")
    public String forget() {
        System.out.println("ForgetController获取登录页");
        return "forgetPage";
    }

    //生成链接至邮箱
    @RequestMapping("/forgetProcess")
    @ResponseBody
    public Boolean forgetProcess(@RequestParam("email") String email) {
        //生成链接发送至邮箱
        System.out.println("ForgetController发送邮件");
        return forgetService.sendEmailUrl(email);
    }

    //携带token获取修改密码页面
    @RequestMapping("/changeProcess")
    public String changeProcess(@RequestParam("token") String token, Model model) {
        System.out.println("ForgetController获取修改密码页");
        model.addAttribute("token", token);
        return "changePage";
    }

    //更改更改密码操作
    @RequestMapping("/changePassword")
    @ResponseBody
    public Integer changePassword(@RequestParam("token") String token,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password) {
        System.out.println("ForgetController更改密码");
        //更改密码操作
        return forgetService.makeNewPassword(token, email, password);
    }
}
