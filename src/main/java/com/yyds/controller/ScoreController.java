package com.yyds.controller;

import com.yyds.domain.StudentScoreInfo;
import com.yyds.domain.UserBaseInfo;
import com.yyds.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @RequestMapping("/qryScore")
    public String qryScore(HttpSession session, Model model) {
        UserBaseInfo user = (UserBaseInfo) session.getAttribute("user");
        Long userId = user.getUserId();
        List<StudentScoreInfo> studentScoreInfos = scoreService.qryScoreByUser(userId);
        model.addAttribute("studentScoreInfos", studentScoreInfos);
        return "qryScorePage";
    }
}
