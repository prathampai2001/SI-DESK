package com.example.clgapp;

public class AttendanceModel {
    private String Attended_Class=null,Total_Class=null;
    private String Month,Roll_no,Stream;

    public AttendanceModel()
    {

    }

    public AttendanceModel(String attended_Class, String total_Class, String month, String roll_no, String stream) {
        Attended_Class = attended_Class;
        Total_Class = total_Class;
        Month = month;
        Roll_no = roll_no;
        Stream = stream;
    }

    public String getAttended_Class() {
        return Attended_Class;
    }

    public void setAttended_Class(String attended_Class) {
        Attended_Class = attended_Class;
    }

    public String getTotal_Class() {
        return Total_Class;
    }

    public void setTotal_Class(String total_Class) {
        Total_Class = total_Class;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
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
}
