package com.example.batterent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.batterent.Util.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import io.github.dvegasa.arcpointer.ArcPointer;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import io.netopen.hotbitmapgg.view.NewCreditSesameView;
import io.netopen.hotbitmapgg.view.OldCreditSesameView;

public class BatteryReport extends Fragment {

    View view;
    ColorfulRingProgressView colorfulRingProgressView;
    TextView temp;
    DatabaseReference reference ,storageRef,storageVoltRef,reference2;
    RingProgressBar ringProgressBar;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    ArcPointer arcPointer,arcPointer2 ;
    Button estKm;

    private static final String TAG = "BatteryReport";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.battery_report,container,false);
        ringProgressBar = view.findViewById(R.id.progress_bar_2);
        arcPointer = view.findViewById(R.id.arcPointer);
        estKm = view.findViewById(R.id.estKm);
        estKm.setClickable(false);
        arcPointer2=view.findViewById(R.id.arcPointer2);
        temp = view.findViewById(R.id.textView8);
        colorfulRingProgressView  = view.findViewById(R.id.crpv);
        arcPointer2.setAnimated(true);
        arcPointer2.setColorLine(arcPointer.getColorMarker());
        arcPointer2.setNotchesLengthRatio(0.1f);
        arcPointer2.setNotches(12);
        arcPointer2.setNotchesColors(Color.parseColor("#4264fb"));
        arcPointer2.setValue(0.65f);
        arcPointer2.setAnimationVelocity(500l);


        arcPointer.setAnimated(true);
        arcPointer.setColorLine(arcPointer.getColorMarker());
        arcPointer.setNotchesLengthRatio(0.1f);
        arcPointer.setNotches(12);
        arcPointer.setNotchesColors(Color.parseColor("#4264fb"));
        arcPointer.setValue(0.20f);
        arcPointer.setAnimationVelocity(500l);
        ringProgressBar.setProgress(78);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("temp");
        reference2 = FirebaseDatabase.getInstance().getReference("volt");
        storageRef = FirebaseDatabase.getInstance().getReference("storedTemp");
        storageVoltRef = FirebaseDatabase.getInstance().getReference("storedVolt");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                colorfulRingProgressView.setPercent(Float.parseFloat(String.valueOf(dataSnapshot.getValue())));
                temp.setText((int) Float.parseFloat(String.valueOf(dataSnapshot.getValue()))+" °C");

                Log.d(TAG, "onDataChange: "+dataSnapshot.getValue());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String a = sdf.format(timestamp);
                Log.d(TAG, "onDataChange: "+a);
                storageRef.child(a).setValue(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                double a = (double) dataSnapshot.getValue();
                Log.d(TAG, "onDataChange: "+a);
                for(int i=0;i< Common.batteryVAl.length;i++){

                    if (a>Common.batteryVAl[i]){
                        ringProgressBar.setProgress(i);
                        estKm.setText(String.valueOf((i*0.1609)+" km"));
                    }

                }

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String b = sdf.format(timestamp);
                storageVoltRef.child(b).setValue(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
