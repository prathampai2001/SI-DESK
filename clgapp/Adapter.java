package com.example.clgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Leave> arrayList;
    Context context;

    public Adapter(ArrayList<Leave> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.retrive_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Leave leave=arrayList.get(position);
        holder.rollNo.setText(leave.getRoll_no());
        holder.stream.setText(leave.getStream());
        holder.date.setText(leave.getDate());
        holder.reason.setText(leave.getReason());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        TextView rollNo,stream,date,reason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rollNo=itemView.findViewById(R.id.rollNo);
            stream=itemView.findViewById(R.id.stream);
            date=itemView.findViewById(R.id.date);
            reason=itemView.findViewById(R.id.reason);
        }
    }
}
