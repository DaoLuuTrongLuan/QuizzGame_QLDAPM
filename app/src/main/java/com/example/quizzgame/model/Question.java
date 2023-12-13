package com.example.quizzgame.model;

public class Question {
    private String question;
    private String ans;
    private String o1;
    private String o2;
    private String o3;
    private String o4;

    public Question(){

    }

    public Question(String question, String ans, String o1, String o2, String o3, String o4) {
        this.question = question;
        this.ans = ans;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO4() {
        return o4;
    }

    public void setO4(String o4) {
        this.o4 = o4;
    }
}
