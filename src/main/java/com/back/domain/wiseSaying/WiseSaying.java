package com.back.domain.wiseSaying;


import java.time.LocalDateTime;

// 첫 글자 대문자. - 파스칼 표기법
// 의미가 달라지는 부분 시작 글자 대문자. - 카멜 표기법
public class WiseSaying {
    //인스턴스 변수
    private int id;
    private String saying;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public WiseSaying(int id, String saying, String author) {
        this.id = id;
        this.saying = saying;
        this.author = author;
    }

    public boolean isNew() {
        return id == 0;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getId() {
        return id;
    }
    public String getSaying() {
        return saying;
    }
    public String getAuthor() {
        return author;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setSaying(String saying) {
        this.saying = saying;
    }

}


