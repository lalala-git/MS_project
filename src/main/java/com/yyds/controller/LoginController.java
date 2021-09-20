package com.yyds.controller;

import com.yyds.domain.UserBaseInfo;
import com.yyds.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录记住密码
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(path = {"/login", "/"})
    public String login(HttpServletRequest request) {
        System.out.println("LoginController获取登录页面");
        //首先判断是否有cookie并判断正确性
        //如果cookie存在并合法，将user存入session中，重定向到mainPage
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    String userInfo = cookie.getValue();
                    String email = userInfo.split("-")[0];
                    String password = userInfo.split("-")[1];
                    UserBaseInfo userBaseInfo = loginService.checkEmailAndPassword(email, password);
                    if (userBaseInfo != null) {
                        request.getSession().setAttribute("user", userBaseInfo);
                        return "redirect:main";
                    }
                }
            }
        }

        return "loginPage";
    }

    @RequestMapping("/loginProcess")
    public String loginProcess(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model,
                               HttpSession session,
                               HttpServletResponse response,
                               @RequestParam(name="remember", required=false) String[] remember) {
        System.out.println("LoginController处理登录数据");
        UserBaseInfo userBaseInfo = loginService.loginProcess(email, password);
        if(userBaseInfo == null) {
            model.addAttribute("flag", 9);
            return "loginPage";
        }
        session.setAttribute("user", userBaseInfo);
        //判断是否需要存储cookie
        if(remember != null && remember.length == 1) {
            Cookie cookie = new Cookie("user", userBaseInfo.getEmail() + "-" +userBaseInfo.getPassword());
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }

        return "mainPage";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("LoginController退出登录");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("user")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:login";
    }

}
