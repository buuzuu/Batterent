package com.example.batterent.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.batterent.Model.BatteryModel;
import com.example.batterent.R;

import java.util.ArrayList;
import java.util.List;

public class BatteryAdapter extends RecyclerView.Adapter<BatteryAdapter.ViewHolder> {

    Context context;
    List<BatteryModel> batteryModelList;
    List<BatteryModel> batteryModelListFull;


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

        viewHolder.relativeLayout.setBackgroundColor(Color.parseColor(batteryModelList.get(i).getColor()));
        viewHolder.textView.setText(batteryModelList.get(i).getBatteryName());


    }

    @Override
    public int getItemCount() {
        return batteryModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }

        @Override
        public void onClick(View v) {

        }
    }


}
