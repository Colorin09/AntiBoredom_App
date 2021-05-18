package com.example.antiboredomapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.viewpager.widget.PagerAdapter;

import com.example.antiboredomapp.Data.AnwserAsyncResponse;
import com.example.antiboredomapp.Data.FunActivityBank;
import com.example.antiboredomapp.Data.SavedActivitiesDBHandler;
import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.FunActivityDB;
import com.example.antiboredomapp.Utils.OptionsMenuActivity;
import com.example.antiboredomapp.Utils.Prefs;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

public class ResultActivity extends OptionsMenuActivity{

    private ExtendedFloatingActionButton fab;
    private TextView activity_title_tv;
    private TextView num_participants_tv;
    private TextView price_tv;
    private TextView prep_level_tv;
    private TextView link_tv;
    private TextView link_title_tv;
    //private TextView url_tv;
    private TableLayout result_table;
    private Button try_again_btn;

    private Toolbar toolbar;
    Spanned url;

    SavedActivitiesDBHandler savedDb;
    Intent intent;

    FunActivity   fun = new FunActivity();
    FunActivityDB holder = new FunActivityDB();
    Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        //declaring database instance using Room
        savedDb = SavedActivitiesDBHandler.getInstance(this.getApplicationContext());
        prefs = new Prefs(ResultActivity.this);

        initialize();
        setSupportActionBar(toolbar);

        //setting logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_run_24);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Setting listeners for the button and fab
        setListeners();

        //Setting context for the bank
        FunActivityBank.setCxt(this.getApplicationContext());
        disableContent();

        //Getting a message from the search activity (MainActivity)
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            String request_type = bundle.getString("request_type");

            //Seeing if it was an random activity
            if (request_type.equals("random")) {
                //Setting url for API
                FunActivityBank.setUrl("https://www.boredapi.com/api/activity/");

                //Using Callback interface
                fun = new FunActivityBank().getFunActivity(new AnwserAsyncResponse() {
                    @Override
                    public void processFinished(FunActivity activity) {
                        if (activity != null) {

                            //creating object to save in the database if requested
                            holder = new FunActivityDB(activity.getKey(), activity.getName(), activity.getParticipant_num(), activity.getLink(),
                                    activity.getPrice_obj().returnEstimate(), activity.getAccess().toString(), null);

                            //displaying the received data
                            activity_title_tv.setText(activity.getName());
                            num_participants_tv.setText(activity.getParticipant_num()+"");
                            price_tv.setText(activity.getPrice_obj().returnEstimate());
                            prep_level_tv.setText(activity.getAccess().toString());

                            if (activity.getLink().isEmpty()) {
                                link_title_tv.setText("");
                            } else {
                                link_tv.setText(activity.getLink());
                            }

                            //enable buttons and table while download
                            enableContent();
                            setLastSeenActivity(activity.getName());

                        } else {
                            activity_title_tv.setText("Sorry, your request could not be fulfilled.");
                            disableContent();
                            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));

                        }
                    }
                });

            } else if (request_type.equals("form")) {

                //Using Gson to retrieve the object from the request
                Gson gson = new Gson();
                String activityRequest = bundle.getString("fun_as_string");
                fun = gson.fromJson(activityRequest, FunActivity.class);

                if(fun != null) {
                    FunActivityBank.setUrl("https://www.boredapi.com/api/activity?type=" + fun.getType()
                            + "&participants=" + fun.getParticipant_num()
                            + "&maxprice=" + fun.getPrice_obj().getPrice_max() + "&minaccessibility=" + fun.getAccess().returnAccessLimits()[0]
                            + "&maxaccessibility=" + fun.getAccess().returnAccessLimits()[1]);
                }

                fun = new FunActivityBank().getFunActivity(new AnwserAsyncResponse() {
                    @Override
                    public void processFinished(FunActivity activity) {
                        if(activity != null && !activity.getName().isEmpty()){

                            //setting object to save in teh database in case
                            holder = new FunActivityDB(activity.getKey(), activity.getName(), activity.getParticipant_num(), activity.getLink(),
                                    activity.getPrice_obj().returnEstimate(),activity.getAccess().toString(), null);

                            activity_title_tv.setText(activity.getName());
                            num_participants_tv.setText(activity.getParticipant_num()+"");
                            price_tv.setText(activity.getPrice_obj().returnEstimate());
                            prep_level_tv.setText(activity.getAccess().toString());

                            if(activity.getLink().isEmpty()){
                                link_title_tv.setText("");
                            }
                            else{
                                link_tv.setText(activity.getLink());
                            }

                            enableContent();
                            setLastSeenActivity(activity.getName());
                        }
                        else{
                            activity_title_tv.setText("Sorry, we found no results for your search. Try again here!");
                            disableContent();
                            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                        }
                    }
                });

            } else {
                activity_title_tv.setText("Sorry, your request could not be fulfilled.");
                disableContent();
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
            }
        }
    }

    public void setLastSeenActivity(String name){
        prefs.saveThirdLastSeen(prefs.getSecondLastSeen());
        prefs.saveSecondLastSeen(prefs.getLastActivity());
        prefs.saveLastActivity(name);
    }

    public void initialize(){
        activity_title_tv = findViewById(R.id.res_act_title);
        num_participants_tv = findViewById(R.id.res_no_part_tv);
        price_tv = findViewById(R.id.res_price_tv);
        prep_level_tv = findViewById(R.id.res_prep_tv);
        link_title_tv = findViewById(R.id.res_link_title);
        link_tv = findViewById(R.id.res_link_tv);
        //url_tv = findViewById(R.id.click_link_res);

        result_table = findViewById(R.id.res_table);
        toolbar = findViewById(R.id.myToolBarWithoutTab);
        try_again_btn = findViewById(R.id.res_btn);
        fab = findViewById(R.id.floating_action_button);
    }

    public void setListeners(){
        //redirecting the user to the main search page
        try_again_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Saving the activity in the favorites list
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean saveActivity = true;
                List<FunActivityDB>  savedActivities = savedDb.funActivityDAO().getSavedActivities();

                for(int i=0;i<savedActivities.size();i++){
                    if(savedActivities.get(i).getKey() == holder.getKey())
                        saveActivity = false;
                }

                if(saveActivity) {
                    savedDb.funActivityDAO().insertSavedActivity(holder);
                    Toast.makeText(getApplicationContext(), R.string.toast_display, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_SHORT).show();
                }
                disableFab();
            }
        });
    }

    public void disableFab(){
        fab.setEnabled(false);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
    }

    public void disableContent(){
        result_table.setVisibility(View.GONE);
        fab.setEnabled(false);
    }

    public void enableContent(){
        result_table.setVisibility(View.VISIBLE);
        fab.setEnabled(true);
    }

}