package com.example.batterent.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.batterent.GenerateOrder;
import com.example.batterent.GenerateOrder2;
import com.example.batterent.Model.DistributorModel;
import com.example.batterent.R;
import com.example.batterent.Util.Common;

import java.util.List;

public class DistributorAdapter extends RecyclerView.Adapter<DistributorAdapter.ViewHolder> {

    private Context context;
    private List<DistributorModel> distributorModelList;
    String [] listItem ={"40%  upto 6.4 km","50%  upto 8.1 km","60%  upto 9.6 km","70%  upto 11.2 km","80%  upto 12.8 km","90%  upto 14.4 km",
            "100%  upto 16.0 km"};

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


                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select SOH");
                    builder.setCancelable(true);
                    builder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which==0){
                                Common.amountSOH=480;
                            }else if(which==1){
                                Common.amountSOH=500;

                            }else if (which ==2){
                                Common.amountSOH=520;

                            }else if(which == 3){
                                Common.amountSOH=540;

                            }else  if (which == 4){
                                Common.amountSOH=560;

                            }else  if (which == 5){
                                Common.amountSOH=580;

                            }else if (which == 6){
                                Common.amountSOH=600;

                            }
                            //
                    Common.distributrPhone =distributorModelList.get(getAdapterPosition()).getPhoneNumber();
                    Common.distributrName =distributorModelList.get(getAdapterPosition()).getFirstname();
                    Common.distributrEmail =distributorModelList.get(getAdapterPosition()).getEmail();

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
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog=builder.create();
                    dialog.show();



                }
            });


        }
    }
}
