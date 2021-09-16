package com.yyds.filter;

import com.yyds.domain.UserBaseInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取路径
        String path = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        //解析路径
        String tailURL = path.split(":")[2];

        /* 处理特殊路径为/ */
        if(tailURL.equals("8899/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }

        String afterURL = tailURL.split("/")[1];

        if("login".equals(afterURL)) {
            //请求登录页
            filterChain.doFilter(servletRequest, servletResponse);
        } else if("loginProcess".equals(afterURL)) {
            //提交登录数据
            filterChain.doFilter(servletRequest, servletResponse);
        } else if("forget".equals(afterURL)) {
            //请求忘记密码页
            filterChain.doFilter(servletRequest, servletResponse);
        } else if("forgetProcess".equals(afterURL)) {
            //提交忘记密码email并生成链接
            filterChain.doFilter(servletRequest, servletResponse);
        } else if("changeProcess".equals(afterURL)) {
            //请求修改密码页
            filterChain.doFilter(servletRequest, servletResponse);
        } else if("changePassword".equals(afterURL)) {
            //提交新密码数据
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //判断是否已登录
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            UserBaseInfo userBaseInfo = (UserBaseInfo) session.getAttribute("user");
            if(userBaseInfo == null) {
                ((HttpServletResponse) servletResponse).sendRedirect("/login");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

}
