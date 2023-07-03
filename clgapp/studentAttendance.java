package com.example.clgapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Calendar;

public class studentAttendance extends AppCompatActivity {
    public TextView selectDate;
    int mYear,mMonth,mDay;
    RecyclerView recyclerView;
    EditText editdate;

    ListView listView;
    Button retrive;
    EditText Data;

    Adapter adapter;
    ArrayList<Leave> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
       // listView=findViewById(R.id.listView);
        retrive=findViewById(R.id.retriveBtn);
        recyclerView=findViewById(R.id.recyler_view);
        recyclerView=findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

                dateDialog = new DatePickerDialog(studentAttendance.this, mDateSetListener, mYear, mMonth, mDay);
                dateDialog.show();
            }

        });



        list=new ArrayList<>();
        adapter=new Adapter(list,this);
        recyclerView.setAdapter(adapter);
        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference reference= db.getReference().child("LeaveNotice").child(editdate.getText().toString());

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Leave leave = dataSnapshot.getValue(Leave.class);
                                list.add(leave);
                            }
                            adapter.notifyDataSetChanged();
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                       // Toast.makeText(studentAttendance.this,"No Leave Today",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




               /* reference= FirebaseDatabase.getInstance().getReference().child("LeaveNotice").child("31-8-2022").child("19799");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("LeaveNotice",snapshot.getValue().toString());
                        LeaveInfo info=snapshot.getValue(LeaveInfo.class);
                        String txt=info.getRoll_no()+":"+info.getStream()+":"+info.getDate()+" : "+info.getReason();
                        Data.setText(txt);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
/*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // list.clear();
               */
/* for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {*//*

                    LeaveInfo info=snapshot.getValue(LeaveInfo.class);
                    String txt=info.getRollNo()+":"+info.getStream()+":"+info.getDate()+" : "+info.getReason();
                    Data.setText(txt);
                   // list.add(txt);
                    // info=snapshot.getValue()
               // }
           //     adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

    }
}