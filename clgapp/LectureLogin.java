package com.example.clgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LectureLogin extends AppCompatActivity {
    CardView attendance,marks,timetable,leaveNote,parentNotice,notice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lecture_login);


        attendance=findViewById(R.id.cardView);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(LectureLogin.this,UploadAttendance.class);
                startActivity(attendance);
            }
        });

        marks=findViewById(R.id.cardView3);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marks=new Intent(LectureLogin.this,UploadMarks.class);
                startActivity(marks);
            }
        });

        timetable=findViewById(R.id.cardView2);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt=new Intent(LectureLogin.this,UploadTimeTable.class);
                startActivity(tt);
            }
        });

        leaveNote=findViewById(R.id.cardView4);
        leaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leave=new Intent(LectureLogin.this,studentAttendance.class);
                startActivity(leave);
            }
        });

        parentNotice=findViewById(R.id.cardView5);
        parentNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parentNotice=new Intent(LectureLogin.this,UploadParentNotice.class);
                startActivity(parentNotice);
            }
        });

        notice=findViewById(R.id.cardView6);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notice=new Intent(LectureLogin.this,UploadNotice.class);
                startActivity(notice);
            }
        });

    }
}
