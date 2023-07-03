package com.example.clgapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UploadAttendance extends AppCompatActivity {
    Button btnUpload;
    EditText rollNo,subject,totClass,attClass,month;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_attendance);

        rollNo=findViewById(R.id.rollNo);
        subject=findViewById(R.id.subject);
        totClass=findViewById(R.id.totalClasses);
        attClass=findViewById(R.id.classAttended);
        month=findViewById(R.id.month);

        btnUpload=findViewById(R.id.btnUpload);

        rootRef= FirebaseDatabase.getInstance().getReference();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rollNo.getText().toString().equals("")) {
                    Toast.makeText(UploadAttendance.this, "Enter RollNo", Toast.LENGTH_SHORT).show();
                } else if (subject.getText().toString().equals("")) {
                    Toast.makeText(UploadAttendance.this, "Enter Subject", Toast.LENGTH_SHORT).show();
                } else if (totClass.getText().toString().equals("")) {
                    Toast.makeText(UploadAttendance.this, "Enter Total Classes", Toast.LENGTH_SHORT).show();
                } else if (attClass.getText().toString().equals("")) {
                    Toast.makeText(UploadAttendance.this, "Enter Attended Classes", Toast.LENGTH_SHORT).show();
                } else if (month.getText().toString().equals("")) {
                    Toast.makeText(UploadAttendance.this, "Enter Month", Toast.LENGTH_SHORT).show();
                } else
                {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("Roll_no", rollNo.getText().toString());
                    reg_entry.put("Subject", subject.getText().toString());
                    reg_entry.put("Total_Class", totClass.getText().toString());
                    reg_entry.put("Attended_Class",attClass.getText().toString());
                    reg_entry.put("Month",month.getText().toString());

                    rootRef.child("Attendance").child(month.getText().toString()).child(rollNo.getText().toString()).child(subject.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(UploadAttendance.this, "Successfully Submitted", Toast.LENGTH_LONG).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadAttendance.this, "UnSuccessful", Toast.LENGTH_LONG).show();
                                    Log.d("Error", e.getMessage());
                                }
                            });
                }
            }
        });

    }
}