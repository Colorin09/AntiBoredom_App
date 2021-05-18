package com.example.antiboredomapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.antiboredomapp.Model.Accessibility;
import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.Price;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormSearchFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SeekBar seekBar;
    private Spinner participantSpinner;
    private Spinner typeSpinner;
    private RadioGroup prepRG;
    private Button submitBTN;
    private TextView price_title_tv;

    private FunActivity fun = new FunActivity();
    private Price p = new Price();
    private Accessibility access = new Accessibility();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormSearchFragment newInstance(String param1, String param2) {
        FormSearchFragment fragment = new FormSearchFragment();
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
        View view =  inflater.inflate(R.layout.fragment_form_search, container, false);

        instantiate(view);
        setParticipantSpinner();
        setTypeSpinner();

        //RadioGroup Listener
        prepRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.prep_radio1:
                        access.setLevel("Quick");
                        break;
                    case R.id.prep_radio2:
                        access.setLevel("Medium");
                        break;
                    case R.id.prep_radio3:
                        access.setLevel("High");
                        break;
                }
            }
        });

        //Submit Button Listener
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fun.setPrice_obj(p);
                fun.setAccess(access);

                Gson gson = new Gson();
                String fun_as_string = gson.toJson(fun);
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("request_type", "form");
                intent.putExtra("fun_as_string", fun_as_string);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    public void instantiate(View view){
        seekBar = view.findViewById(R.id.priceSeeker);
        prepRG = view.findViewById(R.id.prepRG);
        participantSpinner = view.findViewById(R.id.no_parts);
        typeSpinner = view.findViewById(R.id.type);
        submitBTN = view.findViewById(R.id.submit_form);
        price_title_tv = view.findViewById(R.id.price_title);

        seekBar.setOnSeekBarChangeListener(priceListener);
    }

    //Setting seekbar
    private SeekBar.OnSeekBarChangeListener priceListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            p.setPrice_max((double)seekBar.getProgress()/10);
            updatePercentage(seekBar.getProgress()*10);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void updatePercentage(int percent){
        price_title_tv.setText(getString(R.string.pricing) + " ("+percent+")%");
    }

    //Setting Spinners
    public void setParticipantSpinner(){

        ArrayList<Integer> participants = new ArrayList();
        participantSpinner.setOnItemSelectedListener(this);

        for(int i=1;i<6;i++)
        {
            participants.add(i);
        }

        ArrayAdapter<Integer> dataAdapter= new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, participants);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        participantSpinner.setAdapter(dataAdapter);
    }

    public void setTypeSpinner(){

        typeSpinner.setOnItemSelectedListener(this);
        String[] typelist2 = {"music", "diy", "education", "social", "recreational","cooking", "relaxation", "busywork"};

        ArrayAdapter<Integer> dataAdapter= new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, typelist2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
    }

    //Spinner Listeners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(parent.getId() == R.id.type){
            String item = parent.getItemAtPosition(position).toString();
            fun.setType(item);
        }
        else if(parent.getId() == R.id.no_parts) {
            int item = Integer.parseInt(parent.getItemAtPosition(position).toString());
            fun.setParticipant_num(item);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}