package com.example.clgapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {
    Spinner spinner1;
    EditText stdName,rollno,stdPassword,orgPassword;
    Spinner stream;
    RadioGroup radioGroup;
    RadioButton rdOne,rdTwo,rdThree;
    String year;
    TextView txtName;
    CheckBox showPassword,showPassword1;
    TextView lectureRegistration;

    String MobilePattern = "[0-9]{10}";
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[<>/+-@#$])(?=\\S+$).{8,}$";

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_signup,container,false);

        View view = inflater.inflate(R.layout.fragment_signup,container,false);

        stdName=view.findViewById(R.id.studentName);
        rollno=view.findViewById(R.id.rollNo);
        stdPassword=view.findViewById(R.id.studentPassword);
        orgPassword=view.findViewById(R.id.organizationPassword);
        stream=view.findViewById(R.id.streamspinner);

        showPassword=view.findViewById(R.id.showPassword);
        showPassword1=view.findViewById(R.id.showPassword1);

        radioGroup=view.findViewById(R.id.radioGroup);
        rdOne=view.findViewById(R.id.rdone);
        rdTwo=view.findViewById(R.id.rdtwo);
        rdThree=view.findViewById(R.id.rdthree);

        txtName=view.findViewById(R.id.textViewName);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rdone)
                    year="1";
                else if(checkedId==R.id.rdtwo)
                    year="2";
                else if(checkedId==R.id.rdthree)
                    year="3";
            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    stdPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    stdPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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

        lectureRegistration=view.findViewById(R.id.lectureRegistration);
        lectureRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lectureRegister=new Intent(getActivity(),LectureRegistration.class);
                startActivity(lectureRegister);
            }
        });

        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("client").document();
        DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();


        Button btnregister=(Button)view.findViewById(R.id.registerbutton);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stdName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please type Student's Name", Toast.LENGTH_SHORT).show();
                }
                else if (rollno.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please type an roll no.", Toast.LENGTH_SHORT).show();
                }
                else if (stdPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Student Password", Toast.LENGTH_SHORT).show();
                } else if(!(stdPassword.getText().toString().length()>=8||stdPassword.getText().toString().matches(passwordPattern))){
                    Toast.makeText(getActivity(),"Password is invalid",Toast.LENGTH_SHORT).show();
                }
                else if (orgPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Organization Password", Toast.LENGTH_SHORT).show();
                }
                else if (!(orgPassword.getText().toString().equals("MGMcollege"))) {
                    Toast.makeText(getActivity(), "Wrong Organization Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists())
                            {
                                Toast.makeText(getActivity(), "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Map<String,Object> reg_entry=new HashMap<>();
                                reg_entry.put("Student_Name",stdName.getText().toString());
                                reg_entry.put("Roll_no",rollno.getText().toString());
                                reg_entry.put("Stream",stream.getSelectedItem().toString());
                                reg_entry.put("Year", year);
                                reg_entry.put("Student_Password",stdPassword.getText().toString());
                               // reg_entry.put("Organization Password",orgPassword.getText().toString());
                                firebaseFirestore.collection("client")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener <DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                                fr.replace(R.id.fragment_container,new LoginFragment());
                                                fr.commit();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error",e.getMessage());
                                            }
                                        });

                                rootRef.child("Student").child(rollno.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                                Map<String,Object> reg_entry1=new HashMap<>();
                                reg_entry1.put("Student Name",stdName.getText().toString());
                                reg_entry1.put("Roll no","p"+rollno.getText().toString());
                                reg_entry1.put("Stream",stream.getSelectedItem().toString());
                                reg_entry1.put("Year", year);
                                reg_entry1.put("Student Password",stdPassword.getText().toString());

                                firebaseFirestore.collection("Parent")
                                        .add(reg_entry1)
                                        .addOnSuccessListener(new OnSuccessListener <DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                            /*    Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                                fr.replace(R.id.fragment_container,new LoginFragment());
                                                fr.commit();*/
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("Error",e.getMessage());
                                            }
                                        });

                                String roll="p"+rollno.getText().toString();
                                rootRef.child("Parent").child(roll).setValue(reg_entry1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                               /* rootRef.child("Student").child(txtName.getText().toString()).setValue(stdName.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }

                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });*/

                            }
                        }
                    });
                }
            }
        });
        Spinner spinner1 = (Spinner) view.findViewById(R.id.streamspinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(getContext(),R.array.stream, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        return view;


    }
}
