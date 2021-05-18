package com.example.antiboredomapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.FunActivityDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunActivityViewModel extends ViewModel {

    private static MutableLiveData<List<FunActivityDB>> mutableLiveData;

    public static LiveData<List<FunActivityDB>> getSavedList(){

        if(mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();

        return mutableLiveData;
    }

    public void initSavedList(List<FunActivityDB> list)
    {
        if(mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();

        mutableLiveData.setValue(list);
    }

    public static void addSavedActivity(FunActivityDB activity)
    {
        if(mutableLiveData.getValue() != null)
        {
            List<FunActivityDB> savedActivities = new ArrayList<>(mutableLiveData.getValue());
            savedActivities.add(activity);
            mutableLiveData.setValue(savedActivities);
        }
    }

    public static void deleteSavedActivity(FunActivityDB fun)
    {
        List<FunActivityDB> savedActivities = new ArrayList<>(mutableLiveData.getValue());
        savedActivities.remove(fun);
        mutableLiveData.setValue(savedActivities);
    }

    public static void updateSavedActivity(FunActivityDB activity, int position)
    {
        if(mutableLiveData.getValue() != null)
        {
            List<FunActivityDB> savedActivities = new ArrayList<>(mutableLiveData.getValue());
            savedActivities.set(position, activity);
            mutableLiveData.setValue(savedActivities);
        }
    }


}
