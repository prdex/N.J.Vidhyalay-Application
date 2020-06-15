package com.njvidhyalay.myapp;

public class UserInformation {

    private String name;
    private String marks;
    private String subject;
    private String date;
    private String totalmarks;
    private String chapter;


    public UserInformation(){

    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
            return marks;
    }

    public void setMarks(String phone_num) {
        this.marks = phone_num;
    }

    public String getSubject(){

        return  subject;
    }
    public void  setSubject(String subject){
        this.subject = subject;
    }

    public void setDate(String date) {

        this.date = date;
    }
    public String getDate(){
        return date;
    }

    public String getTotalmarks(){
        return totalmarks;
    }

    public void  setTotalmarks(String ttmarks){
        this.totalmarks = ttmarks;
    }
}
