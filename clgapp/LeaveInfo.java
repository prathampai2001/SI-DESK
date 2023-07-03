package com.example.clgapp;

public class LeaveInfo {



    private String Roll_no;
    private String Stream;
    private String Reason;
    private String Date;

    public LeaveInfo()
    {

    }

    public LeaveInfo(String rollNo, String stream, String reason, String date) {
        this.Roll_no = rollNo;
        Stream = stream;
        Reason = reason;
        Date = date;
    }
    public String getRoll_no() {
        return Roll_no;
    }

   /* public String getRollNo() {
        return Roll_no;
    }
*/
    public void setRollNo(String rollNo) {
        this.Roll_no = rollNo;
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
