package com.yyds.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentBaseInfo {

    private Long userId;

    private String email;

    private String name;

    private String phone;

}
