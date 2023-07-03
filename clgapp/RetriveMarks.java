package com.example.clgapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetriveMarks extends AppCompatActivity {
//    TextView s1, s2, s3, s4, s5, s6;
    TextView rollNo, sem;
    TextView m1, m2, m3, m4, m5, m6;
    Button btnRetive;
    String value;
    String mark1,mark2,mark3,mark4,mark5,mark6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_marks);

        rollNo = findViewById(R.id.rollNo);
        sem = findViewById(R.id.sem);
        /*s1 = findViewById(R.id.subject1);
        s2 = findViewById(R.id.subject2);
        s3 = findViewById(R.id.subject3);
        s4 = findViewById(R.id.subject4);
        s5 = findViewById(R.id.subject5);
        s6 = findViewById(R.id.subject6);*/

        m1 = findViewById(R.id.markSub1);
        m2 = findViewById(R.id.markSub2);
        m3 = findViewById(R.id.markSub3);
        m4 = findViewById(R.id.markSub4);
        m5 = findViewById(R.id.markSub5);
        m6 = findViewById(R.id.markSub6);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("rollno");
        }
        rollNo.setText(value);

        btnRetive = findViewById(R.id.btnRetriveMarks);
        btnRetive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rollNo.getText().toString().equals(""))
                {
                    Toast.makeText(RetriveMarks.this,"Enter Roll no.",Toast.LENGTH_SHORT).show();
                }
                else if(sem.getText().toString().equals(""))
                {
                    Toast.makeText(RetriveMarks.this,"Enter Semester",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Marks").child(rollNo.getText().toString()).child(sem.getText().toString());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("Marks", snapshot.getValue().toString());
                            MarksModel marksModel = snapshot.getValue(MarksModel.class);
                            mark1 = marksModel.getSubject1();
                            m1.setText(mark1);

                            mark2 = marksModel.getSubject2();
                            m2.setText(mark2);

                            mark3 = marksModel.getSubject3();
                            m3.setText(mark3);

                            mark4 = marksModel.getSubject4();
                            m4.setText(mark4);

                            mark5 = marksModel.getSubject5();
                            m5.setText(mark5);

                            mark6 = marksModel.getSubject6();
                            m6.setText(mark6);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }}
            });
        }
    }
