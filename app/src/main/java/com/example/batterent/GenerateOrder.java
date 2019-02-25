package com.example.batterent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.batterent.Model.Order;
import com.example.batterent.Util.Common;

import java.util.Random;


public class GenerateOrder extends AppCompatActivity {

    private static final String TAG = "GenerateOrder";

    private Button placeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_order);
        placeOrder = findViewById(R.id.placeOrder);
        Bundle bundle = getIntent().getExtras();
        String userId = bundle.getString("userId");
        String userFullName = bundle.getString("userFullName");
        String shipAddress = bundle.getString("shipAddress");
        String distributorId = bundle.getString("distributorId");
        String batteryModel = bundle.getString("batteryModel");
        String distributorPhone = bundle.getString("distributorPhone");
        String distributorName = bundle.getString("distributorName");
        String distributorEmail = bundle.getString("distributorEmail");
        Log.d(TAG, "onCreate: " + userId);
        Log.d(TAG, "onCreate: " + userFullName);
        Log.d(TAG, "onCreate: " + shipAddress);
        Log.d(TAG, "onCreate: " + distributorId);
        Log.d(TAG, "onCreate: " + batteryModel);
        Log.d(TAG, "onCreate: " + distributorPhone);
        Log.d(TAG, "onCreate: " + distributorName);
        Log.d(TAG, "onCreate: " + distributorEmail);


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId = GenerateOrderId.getAlphaNumericString(10);
                Common.myOrder = new Order(userId, distributorId, userFullName, batteryModel, "2", "1200", shipAddress,orderId);

                Intent intent = new Intent(GenerateOrder.this, StartPaymentActivity.class);
                intent.putExtra("phone", distributorPhone);
                intent.putExtra("amount", "250");
                intent.putExtra("orderId", orderId);
                intent.putExtra("model", batteryModel);
                intent.putExtra("email", distributorEmail);
                intent.putExtra("name", distributorName);

                startActivity(intent);


            }
        });


    }


}
