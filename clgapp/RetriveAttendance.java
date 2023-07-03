package com.example.clgapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RetriveAttendance extends AppCompatActivity {
    Button btnRetrive;
    EditText rollNo,subject,totClass,attClass,month;
    String value;
    String totalClass,attendedClass;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_attendance);

        rollNo=findViewById(R.id.rollNo);
        subject=findViewById(R.id.subject);
        totClass=findViewById(R.id.totalClasses);
        attClass=findViewById(R.id.classAttended);
       // totClass=findViewById(R.id.totalClasses);
        month=findViewById(R.id.month);

        btnRetrive=findViewById(R.id.btnRetrive);
        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            value = extras.getString("rollno");
        }
        rollNo.setText(value);

        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rollNo.getText().toString().equals(""))
                {
                    Toast.makeText(RetriveAttendance.this,"Enter Roll no.",Toast.LENGTH_SHORT).show();
                }
                else if(subject.getText().toString().equals(""))
                {
                    Toast.makeText(RetriveAttendance.this,"Enter Subject",Toast.LENGTH_SHORT).show();
                }
                else if(month.getText().toString().equals(""))
                {
                    Toast.makeText(RetriveAttendance.this,"Enter Month",Toast.LENGTH_SHORT).show();
                }
                else
                {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Attendance").child(month.getText().toString()).child(rollNo.getText().toString()).child(subject.getText().toString());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("Attendance",snapshot.getValue().toString());
                        AttendanceModel attModel=snapshot.getValue(AttendanceModel.class);
                        // c.get(stdModel.getYear());
                        totalClass=attModel.getTotal_Class();
                        totClass.setText(totalClass);

                        attendedClass=attModel.getAttended_Class();
                        attClass.setText(attendedClass);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }}
        });
    }
}