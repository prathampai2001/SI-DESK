package com.example.clgapp;

public class MarksModel {
    private String Subject1,Subject2,Subject3,Subject4,Subject5,Subject6;
    //private String Month,Roll_no,Stream;

    public MarksModel(){

    }

    public MarksModel(String subject1, String subject2, String subject3, String subject4, String subject5, String subject6) {
        Subject1 = subject1;
        Subject2 = subject2;
        Subject3 = subject3;
        Subject4 = subject4;
        Subject5 = subject5;
        Subject6 = subject6;
    }

    public String getSubject1() {
        return Subject1;
    }

    public void setSubject1(String subject1) {
        Subject1 = subject1;
    }

    public String getSubject2() {
        return Subject2;
    }

    public void setSubject2(String subject2) {
        Subject2 = subject2;
    }

    public String getSubject3() {
        return Subject3;
    }

    public void setSubject3(String subject3) {
        Subject3 = subject3;
    }

    public String getSubject4() {
        return Subject4;
    }

    public void setSubject4(String subject4) {
        Subject4 = subject4;
    }

    public String getSubject5() {
        return Subject5;
    }

    public void setSubject5(String subject5) {
        Subject5 = subject5;
    }

    public String getSubject6() {
        return Subject6;
    }

    public void setSubject6(String subject6) {
        Subject6 = subject6;
    }
}
