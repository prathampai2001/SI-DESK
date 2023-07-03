package com.example.clgapp;

public class ParentNoticeModel {
    String Parent_id,Notice;

    ParentNoticeModel(){

    }

    public ParentNoticeModel(String parent_id, String notice) {
        Parent_id = parent_id;
        Notice = notice;
    }

    public String getParent_id() {
        return Parent_id;
    }

    public void setParent_id(String parent_id) {
        Parent_id = parent_id;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String notice) {
        Notice = notice;
    }
}
