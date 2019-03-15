package com.example.batterent;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.batterent.Adapter.DistributorAdapter;
import com.example.batterent.Model.DistributorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intrusoft.library.FrissonView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Distributor extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<DistributorModel> modelList = new ArrayList<>();
    DatabaseReference reference;
    DistributorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        reference = FirebaseDatabase.getInstance().getReference("Distributors");
        recyclerView = findViewById(R.id.distributorList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setUpData();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        }

    }

    private void setUpData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()){

                    Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();
                    while (iterable.hasNext()){
                        DataSnapshot tempItem = iterable.next();
                        modelList.add(tempItem.getValue(DistributorModel.class));
                    }
                    adapter= new DistributorAdapter(Distributor.this,modelList);
                    recyclerView.setAdapter(adapter);

                }else {
                    Toast.makeText(Distributor.this, "Failed in setting adapter", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(Distributor.this, ""+modelList.size(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Distributor.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modelList.clear();
    }
}
