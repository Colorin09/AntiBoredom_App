package com.example.antiboredomapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.antiboredomapp.Utils.Prefs;
import com.example.antiboredomapp.Utils.ViewPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView t1;
    TextView t2;
    TextView t3;
    Button subSearch;

    Prefs prefs;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    //Variables used for the viewpager
    int   currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    //Image array
    int[] images = {R.drawable.ads, R.drawable.fun_ahead, R.drawable.fun_time};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        prefs = new Prefs(getActivity());

        subSearch = view.findViewById(R.id.submit_reg_form);
        t1 = view.findViewById(R.id.last1);
        t2 = view.findViewById(R.id.last2);
        t3= view.findViewById(R.id.last3);

        setImageSlider(view);
        setLastSeen();

        //submitting request
        subSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("request_type", "random");
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void setImageSlider(View view){
        viewPager = view.findViewById(R.id.viewPagerMain);
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), images);
        viewPager.setAdapter(viewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    public void setLastSeen(){
        t1.setText(prefs.getLastActivity());
        t2.setText(prefs.getSecondLastSeen());
        t3.setText(prefs.getThirdLastSeen());
        Log.d("FRAGMENT ACTIVITY", prefs.getLastActivity());

    }
}















