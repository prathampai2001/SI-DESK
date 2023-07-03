package com.example.clgapp;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class LectureRegistration extends AppCompatActivity {
    EditText lectureName, lectureUserName, lecturePassword, HODPassword;
    CheckBox showPassword, showPassword1;
    Button register;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[<>/+-@#$])(?=\\S+$).{8,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_registration);

        lectureName = findViewById(R.id.lecturerName2);
        lectureUserName = findViewById(R.id.lecturerUserName);
        lecturePassword = findViewById(R.id.lecturerPassword2);
        HODPassword = findViewById(R.id.HODPassword2);

        showPassword=findViewById(R.id.showPassword2);
        showPassword1=findViewById(R.id.showPassword3);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lecturePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    lecturePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        showPassword1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    HODPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    HODPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("Faculty").document();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        register = findViewById(R.id.registerbutton2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lectureName.getText().toString().equals("")) {
                    Toast.makeText(LectureRegistration.this, "Please Enter Lecture's Name", Toast.LENGTH_SHORT).show();
                } else if (lectureUserName.getText().toString().equals("")) {
                    Toast.makeText(LectureRegistration.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
                } else if (lecturePassword.getText().toString().equals("")) {
                    Toast.makeText(LectureRegistration.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!(lecturePassword.getText().toString().length() >= 8 || lecturePassword.getText().toString().matches(passwordPattern))) {
                    Toast.makeText(LectureRegistration.this, "Password is invalid", Toast.LENGTH_SHORT).show();
                } else if (HODPassword.getText().toString().equals("")) {
                    Toast.makeText(LectureRegistration.this, "Please Enter HOD Password", Toast.LENGTH_SHORT).show();
                } else if (!(HODPassword.getText().toString().equals("MGM@BCA"))) {
                    Toast.makeText(LectureRegistration.this, "Wrong HOD Password", Toast.LENGTH_SHORT).show();
                } else {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Toast.makeText(LectureRegistration.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Faculty_Name", lectureName.getText().toString());
                                reg_entry.put("Faculty_Username", lectureUserName.getText().toString());
                                reg_entry.put("Faculty_Password", lecturePassword.getText().toString());

                                firebaseFirestore.collection("Faculty")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(LectureRegistration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                                lectureName.setText("");
                                                lectureUserName.setText("");
                                                lecturePassword.setText("");
                                                HODPassword.setText("");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });

                                rootRef.child("Faculty").child(lectureName.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}