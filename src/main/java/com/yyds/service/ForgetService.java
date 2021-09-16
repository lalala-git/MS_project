package com.yyds.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.mysql.cj.util.TimeUtil;
import com.yyds.dao.UserBaseInfoDao;
import com.yyds.domain.UserBaseInfo;
import io.netty.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@Service
public class ForgetService {

    @Value("${baseUrl}")
    private String BASE_URL;

    @Value("${salt}")
    private String SALT;

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送email
    public Boolean sendEmailUrl(String email) {
        if(email == null || "".equals(email)) {
            return false;
        }
        //检查email是否存在
        UserBaseInfo userBaseInfo = userBaseInfoDao.selectUserByEmail(email);
        if(userBaseInfo == null) {
            return false;
        }

        //生成链接
        String url = makeUrl(email);
        //发送链接
        sendUrl(url, email);

        return true;
    }
    private String makeUrl(String email) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_URL);
        builder.append("?token=");

        //对email+salt进行md5加密
        String md5Str = SecureUtil.md5(email + SALT);

        //将token存储到redis中，时效十分钟，保证链接只能访问一次
        redisTemplate.opsForValue().set(email, md5Str, 10, TimeUnit.MINUTES);

        //对token进行base64编码
        String base64Str = Base64.encode(md5Str);

        builder.append(base64Str);
        return builder.toString();
    }
    private void sendUrl(String url, String email) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("2741387650@qq.com");
            helper.setTo(email);
            helper.setSubject("教师管理系统修改密码链接");
            helper.setText("点击链接修改密码（链接有效时长10分钟）" + url);
            sender.send(message);
        } catch (MessagingException e) {
            System.out.println("邮件发送失败");
        }
    }

    //处理token
    public Integer makeNewPassword(String paramToken, String email, String password) {

        //email输入错误，请检查email
        paramToken = Base64.decodeStr(paramToken);
        String makeToken = SecureUtil.md5(email + SALT);
        if(!paramToken.equals(makeToken)) {
            return 1;
        }

        //链接失效，超时或已修改
        String redisToken = redisTemplate.opsForValue().get(email);
        if(redisToken == null) {
            return 2;
        }

        String finalPassword = SecureUtil.md5(password);
        int res = userBaseInfoDao.updatePasswordByEmail(email, finalPassword);
        if(res == 1) {
            //将redis中的token删除
            redisTemplate.delete(email);
            //密码修改成功
            return 3;
        }
        //密码修改失败，请重试
        return 4;
    }

}
