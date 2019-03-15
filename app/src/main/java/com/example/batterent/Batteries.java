package com.example.batterent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.example.batterent.Adapter.BatteryAdapter;
import com.example.batterent.Model.BatteryModel;

import java.util.ArrayList;
import java.util.List;

public class Batteries extends Fragment  {

    View view;
    RecyclerView recyclerView;
    List<BatteryModel> list = new ArrayList<>();
    BatteryAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.battery_layout,container,false);
        setdata();
        recyclerView = view.findViewById(R.id.battery_list);
        GridLayoutManager manager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new BatteryAdapter(view.getContext(),list);
        recyclerView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Battery Name");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void setdata() {

        list.add(new BatteryModel("A1B", "R.drawable.ba1"));
        list.add(new BatteryModel("A2B", "R.drawable.ba2"));
        list.add(new BatteryModel("A3B", "R.drawable.ba3"));
        list.add(new BatteryModel("A4B", "R.drawable.ba4"));
        list.add(new BatteryModel("A5B", "R.drawable.ba5"));
        list.add(new BatteryModel("A6B", "R.drawable.ba6"));
        list.add(new BatteryModel("A7B", "R.drawable.ba7"));
        list.add(new BatteryModel("A8B", "R.drawable.ba8"));
        list.add(new BatteryModel("A9B", "R.drawable.ba9"));
        list.add(new BatteryModel("A10B", "R.drawable.ba10"));
        list.add(new BatteryModel("A11B", "R.drawable.ba11"));
        list.add(new BatteryModel("A12B", "R.drawable.ba12"));
        list.add(new BatteryModel("A13B", "R.drawable.ba13"));





    }



}
