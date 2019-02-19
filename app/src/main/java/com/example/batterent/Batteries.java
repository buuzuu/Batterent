package com.example.batterent;

import android.content.Context;
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

public class Batteries extends Fragment {

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

        list.add(new BatteryModel("Hritik", "#09A9FF"));
        list.add(new BatteryModel("Parul", "#3E51B1"));
        list.add(new BatteryModel("Indeer", "#673BB7"));
        list.add(new BatteryModel("Vika", "#4BAA50"));
        list.add(new BatteryModel("Harpreet", "#F94336"));
        list.add(new BatteryModel("Sittu", "#0A9B88"));
        list.add(new BatteryModel("Mayank", "#09A9FF"));
        list.add(new BatteryModel("Suhani", "#3E51B1"));
        list.add(new BatteryModel("Japan", "#673BB7"));
        list.add(new BatteryModel("India", "#4BAA50"));
        list.add(new BatteryModel("Italy", "#F94336"));
        list.add(new BatteryModel("Georgia", "#0A9B88"));
        list.add(new BatteryModel("Perth", "#09A9FF"));
        list.add(new BatteryModel("Berlin", "#3E51B1"));
        list.add(new BatteryModel("Belgium", "#673BB7"));
        list.add(new BatteryModel("Zurich", "#4BAA50"));
        list.add(new BatteryModel("Goa", "#F94336"));
        list.add(new BatteryModel("Delhi", "#0A9B88"));
        list.add(new BatteryModel("Michigan", "#09A9FF"));
        list.add(new BatteryModel("London", "#3E51B1"));
        list.add(new BatteryModel("Lagos", "#673BB7"));
        list.add(new BatteryModel("Africa", "#4BAA50"));
        list.add(new BatteryModel("Canada", "#F94336"));
        list.add(new BatteryModel("Mexico", "#0A9B88"));
        list.add(new BatteryModel("Jaipur", "#09A9FF"));
        list.add(new BatteryModel("Mohali", "#3E51B1"));
        list.add(new BatteryModel("Punjab", "#673BB7"));


    }
}
