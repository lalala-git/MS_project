package com.yyds.controller;

import com.yyds.domain.StudentSubjectPage;
import com.yyds.domain.SubjectDetail;
import com.yyds.domain.UserBaseInfo;
import com.yyds.service.SubjectDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    private SubjectDetailService subjectDetailService;

    @RequestMapping("/querySubject")
    public String querySubject(HttpSession session, Model model) {
        System.out.println("查看老师所授学科");
        UserBaseInfo userBaseInfo = (UserBaseInfo) session.getAttribute("user");
        Long userId = userBaseInfo.getUserId();

        List<SubjectDetail> subjectDetails = subjectDetailService.querySubjectDetail(userId);
        model.addAttribute("subjectDetails", subjectDetails);

        return "querySubjectPage";
    }

    @RequestMapping("/detailSubject")
    public String detailSubject(HttpSession session,
                                Model model,
                                @RequestParam("subject") String subject,
                                @RequestParam(value = "pageNo",required = false) Integer pageNo) {
        System.out.println("查看学生信息");
        if(pageNo == null) pageNo = 1;
        if(pageNo == 0) pageNo = 1;

        UserBaseInfo user = (UserBaseInfo) session.getAttribute("user");
        Long userId = user.getUserId();
        StudentSubjectPage studentSubjectPage = subjectDetailService.querySubjectStudent(userId, subject, pageNo);
        model.addAttribute("studentSubjectPage", studentSubjectPage);
        model.addAttribute("subject", subject);

        return "detailSubjectPage";
    }

    @RequestMapping("/addStudentSubject")
    public String addStudentSubject(@RequestParam("subject") String subject, Model model) {
        System.out.println("添加学生");
        model.addAttribute("subject", subject);
        return "addStudentSubject";
    }

    @RequestMapping("/subjectAdd")
    public String subjectAdd(@RequestParam("subject") String subject,
                             @RequestParam("email") String email,
                             Model model,
                             HttpSession session) {
        System.out.println("老师添加学生");
        UserBaseInfo userBaseInfo = (UserBaseInfo) session.getAttribute("user");
        subjectDetailService.addSubjectForTeacher( userBaseInfo.getUserId(), subject, email);

        model.addAttribute("subject", subject);
        return "forward:detailSubject";
    }

    @RequestMapping("/deleteStudentSubject")
    public String deleteStudentSubject(@RequestParam("userId") Long userId,
                                       @RequestParam("subject") String subject,
                                       Model model) {
        subjectDetailService.deleteSubjectFor(userId, subject);

        model.addAttribute("subject", subject);
        return "forward:detailSubject";
    }

    @RequestMapping("/changeStudentSubject")
    public String changeStudentSubject(@RequestParam("userId") Long userId,
                                       @RequestParam("subject") String subject,
                                       @RequestParam(value = "score") Integer score,
                                       Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("subject", subject);
        model.addAttribute("score", score);

        return "xhgSubjectPage";
    }

    @RequestMapping("/toXhgScore")
    public String toXhgScore(@RequestParam("userId") Long userId,
                             @RequestParam("subject") String subject,
                             @RequestParam("score") Integer score) {
        subjectDetailService.updateScoreFor(userId, subject, score);

        return "forward:detailSubject";
    }
}
