package com.example.clgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ParentLogin extends AppCompatActivity {
    CardView attendance,leaveNote,marks;
    String value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_parent_login);

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            value = extras.getString("rollno");
        }

        marks=findViewById(R.id.cardView);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marks=new Intent(ParentLogin.this,RetriveMarks.class);
                marks.putExtra("rollno",value);
                startActivity(marks);
            }
        });

        attendance=findViewById(R.id.cardView3);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(ParentLogin.this,RetriveAttendance.class);
                attendance.putExtra("rollno",value);
                startActivity(attendance);
            }
        });

        leaveNote=findViewById(R.id.cardView2);
        leaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parentNotice=new Intent(ParentLogin.this,RetriveParentNotice.class);
                parentNotice.putExtra("rollno",value);
                startActivity(parentNotice);
            }
        });
    }
}