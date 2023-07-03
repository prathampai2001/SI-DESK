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

import java.util.HashMap;
import java.util.Map;

public class UploadMarks extends AppCompatActivity {
   // EditText s1,s2,s3,s4,s5,s6;
    EditText rollNo,sem;
    EditText m1,m2,m3,m4,m5,m6;
    Button btnMarksUpload;

    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_marks);

        rollNo=findViewById(R.id.rollNo);
        sem=findViewById(R.id.sem);
       /* s1=findViewById(R.id.subject1);
        s2=findViewById(R.id.subject2);
        s3=findViewById(R.id.subject3);
        s4=findViewById(R.id.subject4);
        s5=findViewById(R.id.subject5);
        s6=findViewById(R.id.subject6);

        s1.getText().toString().equals("")||s2.getText().toString().equals("")||
                        s3.getText().toString().equals("")||s4.getText().toString().equals("")||
                        s5.getText().toString().equals("")||s6.getText().toString().equals("")||*/

        m1=findViewById(R.id.markSub1);
        m2=findViewById(R.id.markSub2);
        m3=findViewById(R.id.markSub3);
        m4=findViewById(R.id.markSub4);
        m5=findViewById(R.id.markSub5);
        m6=findViewById(R.id.markSub6);

        rootRef= FirebaseDatabase.getInstance().getReference();

        btnMarksUpload=findViewById(R.id.btnUploadMarks);
        btnMarksUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rollNo.getText().toString().equals("")) {
                    Toast.makeText(UploadMarks.this, "Enter RollNo", Toast.LENGTH_SHORT).show();
                } else if (sem.getText().toString().equals("")) {
                    Toast.makeText(UploadMarks.this, "Enter Semester", Toast.LENGTH_SHORT).show();
                } else if (m1.getText().toString().equals("")||m2.getText().toString().equals("")||
                        m3.getText().toString().equals("")||m4.getText().toString().equals("")||
                        m5.getText().toString().equals("")||m6.getText().toString().equals("")) {
                    Toast.makeText(UploadMarks.this, "Enter All The Fields", Toast.LENGTH_SHORT).show();
                } else
                {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("Subject1",m1.getText().toString());
                    reg_entry.put("Subject2",m2.getText().toString());
                    reg_entry.put("Subject3",m3.getText().toString());
                    reg_entry.put("Subject4",m4.getText().toString());
                    reg_entry.put("Subject5",m5.getText().toString());
                    reg_entry.put("Subject6",m6.getText().toString());

                    rootRef.child("Marks").child(rollNo.getText().toString()).child(sem.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(UploadMarks.this, "Successfully Submitted", Toast.LENGTH_LONG).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadMarks.this, "UnSuccessful", Toast.LENGTH_LONG).show();
                                    Log.d("Error", e.getMessage());
                                }
                            });
                }
            }
        });
    }
}