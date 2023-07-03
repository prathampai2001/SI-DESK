package com.example.clgapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    ArrayList<NoticeModel> arrayList;
    Context context;
    public NoticeAdapter(Context context, ArrayList<NoticeModel> arraylist){
        this.context=context;
        this.arrayList=arraylist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.retrive_notice,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
        NoticeModel noticeModel=arrayList.get(position);
        holder.date.setText(noticeModel.getDate());
        holder.title.setText(noticeModel.getTitle());
        holder.notice.setText(noticeModel.getNotice());
    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder

    {
        TextView date,title,notice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            notice=itemView.findViewById(R.id.notice);
        }
    }
}
