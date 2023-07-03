package com.example.clgapp;

public class Leave {
    String Roll_no,Stream,Reason,Date;

    public Leave() {
    }

    public Leave(String roll_no, String stream, String reason, String date) {
        Roll_no = roll_no;
        Stream = stream;
        Reason = reason;
        Date = date;
    }

    public String getRoll_no() {
        return Roll_no;
    }

    public void setRoll_no(String roll_no) {
        Roll_no = roll_no;
    }

    public String getStream() {
        return Stream;
    }

    public void setStream(String stream) {
        Stream = stream;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
