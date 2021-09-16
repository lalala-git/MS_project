package com.yyds.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSubjectInfo {

    /*教师Id*/
    private Long userId;

    private String subject;

}
