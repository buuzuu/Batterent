package com.example.batterent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batterent.Distributor;
import com.example.batterent.Model.BatteryModel;
import com.example.batterent.R;
import com.example.batterent.Util.Common;

import java.util.ArrayList;
import java.util.List;

public class BatteryAdapter extends RecyclerView.Adapter<BatteryAdapter.ViewHolder> {

    Context context;
    List<BatteryModel> batteryModelList;
    List<BatteryModel> batteryModelListFull;
    private static final String TAG = "BatteryAdapter";

    int imageResource[] = {R.drawable.ba1,R.drawable.ba2,R.drawable.ba3,R.drawable.ba4,R.drawable.ba5,R.drawable.ba6,R.drawable.ba7,
                            R.drawable.ba8,R.drawable.ba9,R.drawable.ba10,R.drawable.ba11,R.drawable.ba12,R.drawable.ba13};

    public BatteryAdapter(Context context, List<BatteryModel> batteryModelList) {
        this.context = context;
        this.batteryModelList = batteryModelList;
        batteryModelListFull = new ArrayList<>(batteryModelList);
    }



    private Filter exampleFiler = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<BatteryModel> filteredList = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(batteryModelListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BatteryModel item : batteryModelListFull) {
                    if (item.getBatteryName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            batteryModelList.clear();
            batteryModelList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
     public Filter getFilter(){
         return exampleFiler;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.battery_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.textView.setText(batteryModelList.get(i).getBatteryName());
        viewHolder.imageView.setImageResource(imageResource[i]);

    }
    
    @Override
    public int getItemCount() {
        return batteryModelList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,Distributor.class));
                    Common.battery_model = batteryModelList.get(getAdapterPosition()).getBatteryName();
                    Common.batteryId = imageResource[getAdapterPosition()];
                }
            });
            textView = (TextView) itemView.findViewById(R.id.nameBattery);
            imageView = (ImageView) itemView.findViewById(R.id.imageBattery);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }

    }


}
