package com.example.batterent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.batterent.GenerateOrder;
import com.example.batterent.Model.DistributorModel;
import com.example.batterent.R;
import com.example.batterent.Util.Common;

import java.util.List;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.ViewHolder> {

    private Context context;
    private List<DistributorModel> distributorModelList;

    public DistributorAdapter(Context context, List<DistributorModel> distributorModelList) {
        this.context = context;
        this.distributorModelList = distributorModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.distributor_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    viewHolder.name.setText(distributorModelList.get(i).getFirstname()+" "+distributorModelList.get(i).getLastname());
    viewHolder.address.setText(distributorModelList.get(i).getAddress());
    viewHolder.phoneNumber.setText(distributorModelList.get(i).getPhoneNumber());


    }

    @Override
    public int getItemCount() {
        return distributorModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, price, distance,phoneNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.distributor_name);
            address = itemView.findViewById(R.id.distributor_address);
            price = itemView.findViewById(R.id.price);
            distance = itemView.findViewById(R.id.distance);
            phoneNumber = itemView.findViewById(R.id.distributor_contact);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent =new Intent(context,GenerateOrder.class);
                    intent.putExtra("userId",Common.currentUser.getUserName());
                    intent.putExtra("userFullName",Common.currentUser.getFirstname()+" "+Common.currentUser.getLastname());
                    intent.putExtra("shipAddress",Common.currentUser.getAddress());
                    intent.putExtra("distributorId",distributorModelList.get(getAdapterPosition()).getUserName());
                    intent.putExtra("distributorName",distributorModelList.get(getAdapterPosition()).getFirstname());
                    intent.putExtra("distributorEmail",distributorModelList.get(getAdapterPosition()).getEmail());
                    intent.putExtra("distributorPhone",distributorModelList.get(getAdapterPosition()).getPhoneNumber());
                    intent.putExtra("batteryModel",Common.battery_model);

                    context.startActivity(intent);


                }
            });


        }
    }
}
