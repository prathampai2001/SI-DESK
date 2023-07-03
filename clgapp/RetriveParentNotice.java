package com.example.clgapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetriveParentNotice extends AppCompatActivity {
    String value,noticeValue;
    TextView notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_parent_notice);

        notice = findViewById(R.id.textViewNotice);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("rollno");
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ParentNotice").child(value);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ParentNotice", snapshot.getValue().toString());
                ParentNoticeModel parentNotice = snapshot.getValue(ParentNoticeModel.class);
                noticeValue = parentNotice.getNotice();
                notice.setText(noticeValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}