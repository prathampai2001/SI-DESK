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

public class LeaveNote extends AppCompatActivity{
    Button btnSubmit;
    EditText rollNo,stream,reason,editdate;
    FirebaseFirestore db;
    String value,s;
   public TextView selectDate;
   // DatePicker date;
    int mYear,mMonth,mDay;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    DatabaseReference rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_note);

        rollNo=findViewById(R.id.rollNo);
        reason=findViewById(R.id.reason);
        stream=findViewById(R.id.stream);

        selectDate=findViewById(R.id.txtDate);
        editdate=findViewById(R.id.editDate);
      //  selectDate.setOnClickListener(this);

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("client").document();

        rootRef= FirebaseDatabase.getInstance().getReference();

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

                dateDialog = new DatePickerDialog(LeaveNote.this, mDateSetListener, mYear, mMonth, mDay);
                dateDialog.show();
            }

        });

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            value = extras.getString("rollno");
        }

        rollNo.setText(value);

        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reason.getText().toString().equals("")) {
                    Toast.makeText(LeaveNote.this, "Enter Reason", Toast.LENGTH_SHORT).show();
                } else {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String, Object> reg_entry = new HashMap<>();
                            reg_entry.put("Roll_no", rollNo.getText().toString());
                            reg_entry.put("Stream", stream.getText().toString());
                            reg_entry.put("Reason", reason.getText().toString());
                            reg_entry.put("Date",editdate.getText().toString());
                            firebaseFirestore.collection("leave_notice")
                                    .add(reg_entry)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(LeaveNote.this, "Successfully Submitted", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LeaveNote.this, "UnSuccessful", Toast.LENGTH_LONG).show();
                                            Log.d("Error", e.getMessage());
                                        }
                                    });

                            rootRef.child("LeaveNotice").child(editdate.getText().toString()).child(rollNo.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
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
   /* public void onClick(View v) {
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
            }
        };
        dateDialog = new DatePickerDialog(LeaveNote.this, mDateSetListener, mYear, mMonth, mDay);
        dateDialog.show();
    }*/

}