package com.example.clgapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginFragment extends Fragment {
    public static final String TAG="TAG";
    EditText txtUser,txtPassword;
    FirebaseFirestore db;
    String r;
  //  TextView txt111;
    ProgressDialog loader;
    CheckBox showPassword;
    TextView forgotPassword;

    String passwordPattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[<>/+-@#$])(?=\\S+$).{8,}$";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_login,container,false);

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        db= FirebaseFirestore.getInstance();

        Spinner spinner = (Spinner) view.findViewById(R.id.usertypespinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(getContext(),R.array.usertype, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String a;
        txtUser=view.findViewById(R.id.txtuser);
        txtPassword=view.findViewById(R.id.txtpassword);
        showPassword=view.findViewById(R.id.showPassword);
        loader=new ProgressDialog(getActivity());
        forgotPassword=view.findViewById(R.id.textForgotPassword);

 forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forogtPass = new Intent(getActivity(), ForgotPassword.class);
                startActivity(forogtPass);
            }
        });


        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Button buttonlogin=(Button) view.findViewById(R.id.btnlogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtUser.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!(txtPassword.getText().toString().length() >= 8 || txtPassword.getText().toString().matches(passwordPattern))) {
                    Toast.makeText(getActivity(), "Password is invalid", Toast.LENGTH_SHORT).show();
                }
                if (spinner.getSelectedItem().equals("Student")) {
                    loader.setMessage("Log in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    db.collection("client")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String a = doc.getString("Roll no");
                                            String b = doc.getString("Student Password");
                                            String a1 = txtUser.getText().toString().trim();
                                            r=a1;
                                            String b1 = txtPassword.getText().toString().trim();

                                            if (a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                                //   if (spinner.getSelectedItem().equals("Student")) {
                                                Intent intent1 = new Intent(getActivity(), StudentLogin.class);
                                                intent1.putExtra("rollno", a1);
                                                startActivity(intent1);
                                                Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                                break;
                                                //else
                                                //   Toast.makeText(getActivity(), "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } else
                                        Toast.makeText(getActivity(), "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                    loader.dismiss();
                                }
                            });
                } else if (spinner.getSelectedItem().equals("Faculty")) {
                    loader.setMessage("Log in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    db.collection("Faculty")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String a = doc.getString("Faculty_Username");
                                            String b = doc.getString("Faculty_Password");
                                            String a1 = txtUser.getText().toString().trim();
                                            String b1 = txtPassword.getText().toString().trim();

                                            if (a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                                // if (spinner.getSelectedItem().equals("Lecturer")) {
                                                Intent intent = new Intent(getActivity(), LectureLogin.class);
                                                startActivity(intent);
                                                // }
                                                Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        }
                                    } else
                                        Toast.makeText(getActivity(), "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                    loader.dismiss();

                                }
                            });
                } else if (spinner.getSelectedItem().equals("Parent")) {
                    loader.setMessage("Log in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    db.collection("Parent")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                            String a = doc.getString("Roll no");
                                            String b = doc.getString("Student Password");
                                            String a1 = txtUser.getText().toString().trim();
                                            String b1 = txtPassword.getText().toString().trim();

                                            if (a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                                //   if (spinner.getSelectedItem().equals("Student")) {
                                                Intent intent1 = new Intent(getActivity(), ParentLogin.class);
                                                intent1.putExtra("rollno", r);
                                                startActivity(intent1);
                                                Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_SHORT).show();

                                                //else
                                                //   Toast.makeText(getActivity(), "Login UnSuccessfull", Toast.LENGTH_SHORT).show();


                                            }
                                        }

                                    } else
                                        Toast.makeText(getActivity(), "Login UnSuccessfull", Toast.LENGTH_SHORT).show();
                                    loader.dismiss();
                                }
                            });
                }
            }


            //if(spinner.getSelectedItem())
                /*   if (spinner.getSelectedItem().equals("Student")) {

FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, new StudentLogin());
                        fr.commit();
                        Intent intent1=new Intent(getActivity(),StudentLogin.class);
                        startActivity(intent1);
                    }
                else if(spinner.getSelectedItem().equals("Lecturer")){

 FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, new LectureLogin());
                        fr.commit();
                        Intent intent=new Intent(getActivity(), LectureLogin.class);
                        startActivity(intent);
                    }


        });*/


   /*Button btn111=(Button) view.findViewById(R.id.button111);
    btn111.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            db.collection("client")
                    //.whereEqualTo("Roll no",txtUser.getText())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                for(QueryDocumentSnapshot document: task.getResult())
                                {
                                    String z = document.getString("Roll no");
                                   // String z=task.getResult().toString();
                                    if(z.equals(txtUser.getText().toString())) {
                                        txt111 = view.findViewById(R.id.text111);
                                        txt111.setText(z);

                                        Toast.makeText(getActivity(), z, Toast.LENGTH_SHORT).show();
                                    }
                                  // Log.d(TAG,document.getId()+"=>"+document.getData());
                                }
                            }
                            else
                            {
                                Log.w(TAG,"Error getting doc",task.getException());

                            }
                        }
                    });

        }
    });
*/



});
        return view;}
}
