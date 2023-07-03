package com.example.clgapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class RetriveNotice extends AppCompatActivity {
    RecyclerView recyclerView;
    NoticeAdapter adapter;
    ArrayList<NoticeModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_notice);

        recyclerView=findViewById(R.id.recyler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        adapter=new NoticeAdapter(this,list);
      /*  LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); // last argument (true) is flag for reverse layout
        recyclerView.setLayoutManager(lm);*/
        recyclerView.setAdapter(adapter);


        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference reference= db.getReference().child("Notice");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NoticeModel noticeModel = dataSnapshot.getValue(NoticeModel.class);
                    Collections.reverse(list);
                    list.add(noticeModel);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(studentAttendance.this,"No Leave Today",Toast.LENGTH_SHORT).show();
            }
        });

    }
}