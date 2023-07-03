package com.example.clgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StudentLogin extends AppCompatActivity {
    CardView attendance,leaveNote,marks,timeTable;
    String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_login);

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            value = extras.getString("rollno");
        }


        marks=findViewById(R.id.cardView);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marks=new Intent(StudentLogin.this,RetriveMarks.class);
                marks.putExtra("rollno",value);
                startActivity(marks);
            }
        });

        attendance=findViewById(R.id.cardView3);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(StudentLogin.this,RetriveAttendance.class);
                attendance.putExtra("rollno",value);
                startActivity(attendance);
            }
        });

        leaveNote=findViewById(R.id.cardView2);
        leaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leavenote=new Intent(StudentLogin.this,LeaveNote.class);
                leavenote.putExtra("rollno",value);
                startActivity(leavenote);
            }
        });

        timeTable=findViewById(R.id.cardView4);
        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeTable=new Intent(StudentLogin.this,RetriveTimeTable.class);
                timeTable.putExtra("rollno",value);
                startActivity(timeTable);
            }
        });
    }

}

/*public class StudentLogin extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_student_login,container,false);
    }
}*/