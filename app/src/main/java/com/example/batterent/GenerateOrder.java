package com.example.batterent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.batterent.Model.Order;
import com.example.batterent.Util.Common;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class GenerateOrder extends AppCompatActivity {

    private static final String TAG = "GenerateOrder";
    NiceSpinner niceSpinner;
    String finalAmount ="600";
    String month;
    private Button placeOrder;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView batteryImg;
    private TextView order_description, modelName,totalAmount;
    EditText shipAddre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_order);
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        batteryImg =findViewById(R.id.scheme_img);
        modelName =findViewById(R.id.scheme_name);
        totalAmount =findViewById(R.id.totalAmount);
        niceSpinner = findViewById(R.id.nice_spinner);
        shipAddre = findViewById(R.id.shipAddress);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
        order_description =findViewById(R.id.scheme_description);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
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


        batteryImg.setImageResource(Common.batteryId);
        shipAddre.setText(Common.currentUser.getAddress());
        modelName.setText(Common.battery_model);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId = GenerateOrderId.getAlphaNumericString(10);
                Common.myOrder = new Order(userId, distributorId, userFullName, batteryModel, month, finalAmount, shipAddress,orderId);

                Intent intent = new Intent(GenerateOrder.this, StartPaymentActivity.class);
                intent.putExtra("phone", distributorPhone);
                intent.putExtra("amount", finalAmount);
                intent.putExtra("orderId", orderId);
                intent.putExtra("model", batteryModel);
                intent.putExtra("email", distributorEmail);
                intent.putExtra("name", distributorName);

                startActivity(intent);


            }
        });
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                totalAmount.setText("₹ "+String.valueOf((position+1)*600));
                finalAmount=String.valueOf((position+1)*600);
                month=String.valueOf(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalAmount.setText("₹ 600");
                finalAmount="600";
                month="1";
            }
        });

    }


}
