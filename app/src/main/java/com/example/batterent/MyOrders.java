package com.example.batterent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.batterent.Model.Order;
import com.example.batterent.Util.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MyOrders extends android.support.v4.app.Fragment {

    View view;
    DatabaseReference reference,walletBal;
    Button cancel;
    TextView textView,textView2;
    private static final String TAG = "MyOrders";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.offers_layout,container,false);
        textView = view.findViewById(R.id.textView5);
        textView2 = view.findViewById(R.id.textView9);
        cancel = view.findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setVisibility(View.INVISIBLE);
                textView.setText("No Orders");
            //    Common.balance = String.valueOf(Integer.valueOf(Common.recievedOrder.getAmount())-100);
                walletBal.setValue(String.valueOf(Integer.valueOf(Common.recievedOrder.getAmount())-100));
                reference.child(Common.recievedOrder.getOrderId()).removeValue();
            }
        });



        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        walletBal = FirebaseDatabase.getInstance().getReference("walletBal");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();
                    while (iterable.hasNext()){
                        DataSnapshot tempItem = iterable.next();
                        Common.recievedOrder =tempItem.getValue(Order.class);
                    }
                    Log.d(TAG, "onDataChange: "+Common.recievedOrder.getAmount());
                    textView.setText("Txn ID :"+Common.recievedOrder.getOrderId());
                    textView2.setText("Name :"+Common.recievedOrder.getUserName()+"\n"+
                                        "Amount :"+Common.recievedOrder.getAmount()+"\n"
                                        + "Distributor ID:"+Common.recievedOrder.getDistributorId()
                                        +"\n"+"Product :"+Common.recievedOrder.getProduct());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
