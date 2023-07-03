package com.example.clgapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UploadNotice extends AppCompatActivity {
    public TextView selectDate;
    int mYear,mMonth,mDay;
    EditText title,notice,editdate;
    Button post;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        title=findViewById(R.id.editTextTitle);
        notice=findViewById(R.id.editTextNotice);
        post=findViewById(R.id.btnPost);

        selectDate=findViewById(R.id.txtDate);
        editdate=findViewById(R.id.editDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog dateDialog;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        selectDate.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear).append(""));
                        editdate.setText(selectDate.getText().toString());
                    }
                };
                //  selectDate.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear).append(""));
                dateDialog = new DatePickerDialog(UploadNotice.this, mDateSetListener, mYear, mMonth, mDay);
                dateDialog.show();
            }
        });

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("Notice").document();

        rootRef= FirebaseDatabase.getInstance().getReference();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (title.getText().toString().equals("")) {
                    Toast.makeText(UploadNotice.this, "Enter Title", Toast.LENGTH_SHORT).show();
                }else if (notice.getText().toString().equals("")) {
                    Toast.makeText(UploadNotice.this, "Enter Notice", Toast.LENGTH_SHORT).show();
                }
                else if(selectDate.getText().toString().equals(""))
                {
                    Toast.makeText(UploadNotice.this, "Select Date", Toast.LENGTH_SHORT).show();

                }else {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String, Object> reg_entry = new HashMap<>();
                            reg_entry.put("Date",editdate.getText().toString());
                            reg_entry.put("Title", title.getText().toString());
                            reg_entry.put("Notice", notice.getText().toString());
                            firebaseFirestore.collection("Notice")
                                    .add(reg_entry)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(UploadNotice.this, "Successfully Posted", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UploadNotice.this, "UnSuccessful", Toast.LENGTH_LONG).show();
                                            Log.d("Error", e.getMessage());
                                        }
                                    });

                            rootRef.child("Notice").push().setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                    });
                }
            }

        });
    }
}