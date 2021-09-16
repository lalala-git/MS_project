package com.yyds.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentBaseInfoPage {

    private List<StudentBaseInfo> studentBaseInfos;

    private Boolean isFirstPage;

    private Boolean isLastPage;

    private Integer nowPage;

    private Integer prePage;

    private Integer nextPage;

    private Integer lastPage;

}
