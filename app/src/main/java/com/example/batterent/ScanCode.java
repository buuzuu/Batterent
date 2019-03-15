package com.example.batterent;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode extends Fragment implements ZXingScannerView.ResultHandler {


    private ZXingScannerView mScannerView;
    private static final String TAG = "ScanCode";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  view = inflater.inflate(R.layout.scan_code,container,false);
        Log.d(TAG, "onCreateView: ");
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        StringBuilder stringBuilder = new StringBuilder(rawResult.getText());

        String modelNum = stringBuilder.substring(0,3);
        String serialNum = stringBuilder.substring(3,6);
        String distributorId = stringBuilder.substring(6,rawResult.getText().length());




        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanCode.this);
            }
        }, 2000);


        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity(), notification);
            r.play();
        } catch (Exception e) {}



        new TTFancyGifDialog.Builder(getActivity())
                .setTitle("Online Shopping")
                .setMessage("Model Selected: "+modelNum+"\n"+"Serial Number: "+serialNum+"\n"+"Distributor ID: "+distributorId)
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#22b573")
                .setGifResource(R.drawable.proceed)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {

                        Intent intent = new Intent(getActivity(),GenerateOrder2.class);
                        intent.putExtra("modelNum",modelNum);
                        intent.putExtra("distributorId",distributorId);
                        intent.putExtra("productNumber",rawResult.getText());
                        startActivity(intent);


                        Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();



    }
}
