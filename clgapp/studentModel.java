package com.example.clgapp;

public class studentModel {
    //private String Roll_no;
    private String Stream;
    private String Year;
    private String Roll_no,Student_Name,Student_Password;

    public studentModel()
    {

    }

    public studentModel(String stream, String year, String roll_no, String student_Name, String student_Password) {
        Stream = stream;
        Year = year;
        Roll_no = roll_no;
        Student_Name = student_Name;
        Student_Password = student_Password;
    }

    public String getStream() {
        return Stream;
    }

    public void setStream(String stream) {
        Stream = stream;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRoll_no() {
        return Roll_no;
    }

    public void setRoll_no(String roll_no) {
        Roll_no = roll_no;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getStudent_Password() {
        return Student_Password;
    }

    public void setStudent_Password(String student_Password) {
        Student_Password = student_Password;
    }

}
