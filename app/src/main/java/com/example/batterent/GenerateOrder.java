package com.example.batterent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batterent.Model.Credentials;
import com.example.batterent.Model.Order;
import com.example.batterent.Util.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class GenerateOrder extends AppCompatActivity {

    private static final String TAG = "GenerateOrder";
    NiceSpinner niceSpinner;
    String finalAmount ="600";
    String month;
    private Uri filePath;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    DatabaseReference reference;
    private Button placeOrder , upload;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView batteryImg,uploadImage;
    public static final int SELECT_PICTURE = 1520;
    private TextView order_description, modelName,totalAmount,megaAmount;
    EditText shipAddre;
    int a;
    String userId,userFullName,shipAddress,distributorId,batteryModel,distributorPhone,distributorName,distributorEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_order);
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        reference= FirebaseDatabase.getInstance().getReference("credentials");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("usersRC");
        batteryImg =findViewById(R.id.scheme_img);
        megaAmount =findViewById(R.id.megaAmount);
        modelName =findViewById(R.id.scheme_name);
        uploadImage = findViewById(R.id.uploadedImage);
        totalAmount =findViewById(R.id.totalAmount);
        upload = findViewById(R.id.upload);
        niceSpinner = findViewById(R.id.nice_spinner);
        shipAddre = findViewById(R.id.shipAddress);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);
        order_description =findViewById(R.id.scheme_description);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        placeOrder = findViewById(R.id.placeOrder);
        totalAmount.setText("₹ "+Common.amountSOH);
        megaAmount.setText("₹ "+Common.amountSOH);
        Bundle bundle = getIntent().getExtras();

        userId = bundle.getString("userId");
         userFullName = bundle.getString("userFullName");
         shipAddress = bundle.getString("shipAddress");
         distributorId = bundle.getString("distributorId");
         batteryModel = bundle.getString("batteryModel");
         distributorPhone = bundle.getString("distributorPhone");
         distributorName = bundle.getString("distributorName");
         distributorEmail = bundle.getString("distributorEmail");


        batteryImg.setImageResource(Common.batteryId);
        shipAddre.setText(Common.currentUser.getAddress());
        modelName.setText(Common.battery_model);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            uploadFile();


            }
        });
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                finalAmount=String.valueOf((position+1)*600);
                month=String.valueOf(position+1);

                totalAmount.setText("₹ "+Common.amountSOH);

                 a = Common.amountSOH * Integer.parseInt(month);

                megaAmount.setText("₹ "+a);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalAmount.setText("₹ "+Common.amountSOH);

                finalAmount="600";
                month="1";
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                //Get ImageURi and load with help of picasso
                //Uri selectedImageURI = data.getData();
                filePath = data.getData();
                Picasso.with(GenerateOrder.this).load(data.getData()).centerCrop().fit()
                        .into(uploadImage);
            }

        }
    }

    private void uploadFile(){

        final ProgressDialog progressDialog = new ProgressDialog(GenerateOrder.this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        storageReference.child(userId).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(GenerateOrder.this, "Uploaded", Toast.LENGTH_SHORT).show();

                storageReference.child(userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        saveCredentials(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }

    private void saveCredentials(Uri uri) {

        Credentials credentials = new Credentials(userId,uri.toString());

        reference.child(userId).setValue(credentials).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Added");

                String orderId = GenerateOrderId.getAlphaNumericString(10);
                Common.myOrder = new Order(userId, distributorId, userFullName, batteryModel, month, finalAmount, shipAddress,orderId);

                Intent intent = new Intent(GenerateOrder.this, StartPaymentActivity.class);
                intent.putExtra("phone", distributorPhone);
                intent.putExtra("amount", a);
                intent.putExtra("orderId", orderId);
                intent.putExtra("model", batteryModel);
                intent.putExtra("email", distributorEmail);
                intent.putExtra("name", distributorName);

                startActivity(intent);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Failed");
            }
        });

    }


}
