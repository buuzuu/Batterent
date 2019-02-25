package com.example.batterent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.dvegasa.arcpointer.ArcPointer;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import io.netopen.hotbitmapgg.view.NewCreditSesameView;

public class BatteryReport extends Fragment {

    View view;
    RingProgressBar ringProgressBar;
    ArcPointer arcPointer,arcPointer2 ;
    NewCreditSesameView creditSesameView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.battery_report,container,false);
        ringProgressBar = view.findViewById(R.id.progress_bar_2);
        arcPointer = view.findViewById(R.id.arcPointer);
        arcPointer2=view.findViewById(R.id.arcPointer2);
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
}
