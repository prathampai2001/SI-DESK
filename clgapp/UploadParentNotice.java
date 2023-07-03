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

public class UploadParentNotice extends AppCompatActivity {
    EditText parentId,parentNotice;
    Button send;

    DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_parent_notice);

        parentId=findViewById(R.id.parentId);
        parentNotice=findViewById(R.id.parentNotice);
        send=findViewById(R.id.btnSend);

        rootRef= FirebaseDatabase.getInstance().getReference();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentId.getText().toString().equals(""))
                {
                    Toast.makeText(UploadParentNotice.this, "Enter Parent Id", Toast.LENGTH_LONG).show();
                }
                else if(parentNotice.getText().toString().equals(""))
                {
                    Toast.makeText(UploadParentNotice.this, "Enter Notice", Toast.LENGTH_LONG).show();
                }
                else
                {
                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("Parent_id",parentId.getText().toString());
                reg_entry.put("Notice",parentNotice.getText().toString());

                rootRef.child("ParentNotice").child(parentId.getText().toString()).setValue(reg_entry).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UploadParentNotice.this, "Successfully Sent", Toast.LENGTH_LONG).show();
                                parentNotice.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UploadParentNotice.this, "UnSuccessful", Toast.LENGTH_LONG).show();
                                Log.d("Error", e.getMessage());
                            }
                        });
            }}
        });

    }
}