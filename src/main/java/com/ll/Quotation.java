package com.ll;

// 관련 있는 사항들끼리 묶어주기
public class Quotation {
    private int id; // 명언 번호
    private String authorName; // 작가
    private String quotation; // 명언

    public Quotation(int id, String authorName, String quotation) {
        this.id = id;
        this.authorName = authorName;
        this.quotation = quotation;
    }
}
