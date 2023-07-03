package com.example.clgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageButton regForm;
    Button notice,holiday;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_home,container,false);

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        regForm=view.findViewById(R.id.imageButton);

        viewPager=view.findViewById(R.id.viewPager);
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.college_image);
        imageList.add(R.drawable.college_image2);
        imageList.add(R.drawable.ttt);
       /* imageList.add(R.drawable.four);
        imageList.add(R.drawable.five);*/
        ImageAdapter imgAdapter = new ImageAdapter(imageList);
        viewPager.setAdapter(imgAdapter);

        notice=view.findViewById(R.id.btnNotice);

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notice=new Intent(getActivity(),RetriveNotice.class);
                startActivity(notice);
            }
        });

        holiday=view.findViewById(R.id.btnHoliday);
        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent holiday=new Intent(getActivity(),Holidays.class);
                startActivity(holiday);
            }
        });

        regForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registration=new Intent(getActivity(),RegistrationForm.class);
                startActivity(registration);
            }
        });

        return view;
    }
}