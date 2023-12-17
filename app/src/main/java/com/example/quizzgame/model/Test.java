<<<<<<< HEAD
package com.example.quizzgame.model;

import java.util.ArrayList;

public class Test {
    String nameTest;
    ArrayList<Question> list10question;

    public Test(){

    }


}
=======
package com.example.quizzgame.model;

public class Test {
    private String title, total_questions,id, linkImage, link;

    public Test(String title, String total_questions, String linkImage) {
        this.title = title;
        this.total_questions = total_questions;
        this.linkImage = linkImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal_questions() {
        return total_questions;
    }

    public void setTotal_questions(String total_questions) {
        this.total_questions = total_questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
>>>>>>> 8f4021b91c20e30d596a65b9e1e1acb3b1e244e0
