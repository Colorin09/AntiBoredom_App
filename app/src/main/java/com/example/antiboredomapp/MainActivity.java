package com.example.antiboredomapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.antiboredomapp.Utils.OptionsMenuActivity;
import com.example.antiboredomapp.Utils.ViewStateAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends OptionsMenuActivity {

    private ViewStateAdapter viewStateAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    int orientation = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        initialize();

        setSupportActionBar(toolbar);

        //setting logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_run_24);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setViewStateAdapter();
    }


    public void initialize(){
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.myToolBar);
    }

    public void setViewStateAdapter(){
        //setting tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_name2));

        //starting fragments
        FragmentManager fragManager = getSupportFragmentManager();
        viewStateAdapter = new ViewStateAdapter(fragManager, getLifecycle());
        viewStateAdapter.setNumberOfTabs(tabLayout.getTabCount());
        viewPager.setAdapter(viewStateAdapter);

        //tab listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    //To disable the options creating a new Search Activity
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        menu.getItem(0).setEnabled(false);
        return true;
    }
}