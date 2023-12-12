package com.example.quizzgame.model;

public class Result {
        private String idUser;
        private String idTest;
        private int score;

    public Result() {
    }

    public Result(String idUser, String idTest, int score) {
        this.idUser = idUser;
        this.idTest = idTest;
        this.score = score;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTest() {
        return idTest;
    }

    public void setIdTest(String idTest) {
        this.idTest = idTest;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
