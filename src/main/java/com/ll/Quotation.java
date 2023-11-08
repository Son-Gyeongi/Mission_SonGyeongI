package com.ll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 관련 있는 사항들끼리 묶어주기
@Getter
@Setter
@AllArgsConstructor
public class Quotation {
    private int id; // 명언 번호
    private String authorName; // 작가
    private String quotation; // 명언
}
