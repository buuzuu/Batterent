package com.example.batterent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batterent.Model.Order;
import com.example.batterent.Util.Common;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GenerateOrder2 extends AppCompatActivity {

    NiceSpinner niceSpinner;
    String finalAmount ="600";
    String month;
    private Button placeOrder;
     CollapsingToolbarLayout collapsingToolbarLayout;
    TextView scheme_name,customerName,distributor_ID,totalAmount;
    String modelNum,productNumber,distributorId;
    EditText shipAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_order2);
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        scheme_name=findViewById(R.id.scheme_name);
        customerName=findViewById(R.id.customerName);
        distributor_ID=findViewById(R.id.distributor_ID);
        shipAdd=findViewById(R.id.shipAdd);
        niceSpinner=findViewById(R.id.nice_spinner);
        totalAmount=findViewById(R.id.totalAmount);
        placeOrder=findViewById(R.id.placeOrder);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        Bundle bundle = getIntent().getExtras();
        modelNum = bundle.getString("modelNum");
        distributorId = bundle.getString("distributorId");
        productNumber = bundle.getString("productNumber");



        scheme_name.setText(modelNum);
        customerName.setText(Common.currentUser.getFirstname());
        distributor_ID.setText(distributorId);
        shipAdd.setText(Common.currentUser.getAddress());
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


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = GenerateOrderId.getAlphaNumericString(10);
                Common.myOrder2 = new Order(Common.currentUser.getUserName(), distributorId, Common.currentUser.getFirstname()+" "+
                        Common.currentUser.getLastname(), modelNum, month, finalAmount, Common.currentUser.getAddress(),orderId);


                String [] listItem ={"Cash","Online"};
                AlertDialog.Builder builder = new AlertDialog.Builder(GenerateOrder2.this);
                builder.setTitle("Choose Payment Options");
                builder.setCancelable(true);
                builder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which==0){




                            Toast.makeText(GenerateOrder2.this, "Cash", Toast.LENGTH_SHORT).show();
                        }else {

                            Intent intent = new Intent(GenerateOrder2.this, StartPaymentActivity.class);
                            intent.putExtra("phone", Common.distributrPhone);
                            intent.putExtra("amount", finalAmount);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("model", modelNum);
                            intent.putExtra("email", "hritikgupta722@gmail.com");
                            intent.putExtra("name", distributorId);

                            startActivity(intent);

                            Toast.makeText(GenerateOrder2.this, "Online", Toast.LENGTH_SHORT).show();
                        }


                        dialog.dismiss();

                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();


            }
        });



    }
}
