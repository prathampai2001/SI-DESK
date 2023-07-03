package com.example.clgapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class RetriveTimeTable extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Model> list;
    MyAdapter adapter;
    Button display;
    EditText year;
    String value,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_time_table);

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {
            value = extras.getString("rollno");
        }



        display=findViewById(R.id.bdisplay);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new MyAdapter(this,list);
        recyclerView.setAdapter(adapter);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Student").child(value);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*Log.d("Student",snapshot.getValue().toString());
                studentModel stdModel=snapshot.getValue(studentModel.class);
               // c.get(stdModel.getYear());
                y=stdModel.getYear();
                year=findViewById(R.id.editTextYear2);
                year.setText(y);*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     //   year.setText(y.toString());
       // DatabaseReference root= FirebaseDatabase.getInstance().getReference("Time Table").child("BCA").child(year.getText().toString());

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference root= FirebaseDatabase.getInstance().getReference().child("Time Table").child("BCA").child(year.getText().toString());
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            Model model = dataSnapshot.getValue(Model.class);
                           // Model model=dataSnapshot.getValue(Model.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}