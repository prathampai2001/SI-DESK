package com.example.clgapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    CheckBox showPassword,showPassword1;
    EditText password,orgPassword;
    Button reset;
    EditText userName;
    ProgressDialog loader;
    FirebaseFirestore db;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    String name,stream,year;

    String passwordPattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[<>/+-@#$])(?=\\S+$).{8,}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Spinner spinner =findViewById(R.id.usertypespinner3);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(ForgotPassword.this,R.array.usertype, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        userName=findViewById(R.id.editTextUserName);
        password=findViewById(R.id.Password2);
        showPassword=findViewById(R.id.showPassword4);
        showPassword1=findViewById(R.id.showPassword1);
        orgPassword=findViewById(R.id.organizationPassword11);
        db= FirebaseFirestore.getInstance();

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        showPassword1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    orgPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    orgPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        reset=findViewById(R.id.resetButton);

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("client").document();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")) {
                    Toast.makeText(ForgotPassword.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(ForgotPassword.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!(password.getText().toString().length() >= 8 || password.getText().toString().matches(passwordPattern))) {
                    Toast.makeText(ForgotPassword.this, "Password is invalid", Toast.LENGTH_SHORT).show();
                } else if (orgPassword.getText().toString().equals("")) {
                    Toast.makeText(ForgotPassword.this, "Please Enter Organization Password", Toast.LENGTH_SHORT).show();
                } else if (!(orgPassword.getText().toString().equals("MGMcollege"))) {
                    Toast.makeText(ForgotPassword.this, "Wrong Organization Password", Toast.LENGTH_SHORT).show();
                }
               else if (spinner.getSelectedItem().equals("Student")) {
   loader.setMessage("Log in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student").child(userName.getText().toString());
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Log.d("Student", snapshot.getValue().toString());
                                    studentModel student = snapshot.getValue(studentModel.class);
                                    name = student.getStudent_Name();
                                    stream=student.getStream();
                                    year=student.getYear();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Map<String, Object> reg_entry = new HashMap<>();
                            reg_entry.put("Roll no", userName.getText().toString());
                            reg_entry.put("Student_Name",name);
                            reg_entry.put("Stream",stream);
                            reg_entry.put("Year", year);
                            reg_entry.put("Student Password", password.getText().toString());
                            // reg_entry.put("Organization Password",orgPassword.getText().toString());
                            firebaseFirestore.collection("client")
                                    .add(reg_entry)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(ForgotPassword.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Error", e.getMessage());
                                        }
                                    });

                            rootRef.child("Student").child(userName.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

 db.collection("client")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String a = doc.getString("Roll no");
                                            String b = doc.getString("Student Password");
                                            String a1 = userName.getText().toString().trim();
                                          //  r=a1;
                                            String b1 = password.getText().toString().trim();

                                            if (a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                                //   if (spinner.getSelectedItem().equals("Student")) {
                                                Intent intent1 = new Intent(ForgotPassword.this, StudentLogin.class);
                                                intent1.putExtra("rollno",a1);
                                                startActivity(intent1);
                                                Toast.makeText(ForgotPassword.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                                //else
                                                //   Toast.makeText(getActivity(), "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }
                                    else
                                        Toast.makeText(ForgotPassword.this, "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                    loader.dismiss();
                                }
                            });


                        }
                    });
                }
            }
        });
    }
}
