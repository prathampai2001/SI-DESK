package com.example.clgapp;

public class NoticeModel {
    String Date,Notice,Title;

    public NoticeModel()
    {

    }

    public NoticeModel(String date, String notice, String title) {
        Date = date;
        Notice = notice;
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String notice) {
        Notice = notice;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
