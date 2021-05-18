package com.example.antiboredomapp;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antiboredomapp.Data.ActivityListAdapter;
import com.example.antiboredomapp.Data.AnwserAsyncResponse;
import com.example.antiboredomapp.Data.FunActivityBank;
import com.example.antiboredomapp.Data.SavedActivitiesDBHandler;
import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.FunActivityDB;
import com.example.antiboredomapp.Utils.OptionsMenuActivity;
import com.example.antiboredomapp.ViewModel.FunActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FavoritesActivity extends OptionsMenuActivity {

    private static final String TAG = "favorite_activity";
    ActivityListAdapter activityListAdapter;
    FunActivityViewModel funActivityViewModel;
    RecyclerView recyclerView;

    List<FunActivityDB> savedActivities;

    Toolbar toolbar;

    SavedActivitiesDBHandler savedDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        toolbar = findViewById(R.id.myToolBarWithoutTab);
        recyclerView = findViewById(R.id.myRecyclerView);

        setSupportActionBar(toolbar);

        //setting logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_run_24);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        savedDb = SavedActivitiesDBHandler.getInstance(this.getApplicationContext());

        initRecyclerView();
    }

    private void initRecyclerView(){
        activityListAdapter = new ActivityListAdapter(this.getApplicationContext(), FunActivityDB.itemCallBack);
        recyclerView.setAdapter(activityListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        funActivityViewModel = new ViewModelProvider(this).get(FunActivityViewModel.class);

        savedActivities = savedDb.funActivityDAO().getSavedActivities();
        FunActivityBank.setCxt(this.getApplicationContext());

        funActivityViewModel.initSavedList(savedActivities);

        funActivityViewModel.getSavedList().observe(this, new Observer<List<FunActivityDB>>() {
            @Override
            public void onChanged(List<FunActivityDB> funActivities) {
                activityListAdapter.submitList(funActivities);
            }
        });
    }

    //To disable the options creating a new Favorite Activity
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.getItem(1).setEnabled(false);
        return true;
    }

}