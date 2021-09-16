package com.yyds.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreInfo {

    /*学生Id*/
    private Long userId;

    private String subject;

    private Integer score;

    /*教师Id*/
    private Long teacherUserId;

}
