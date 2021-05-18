package com.example.antiboredomapp.Utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.antiboredomapp.FormSearchFragment;
import com.example.antiboredomapp.SearchFragment;

public class ViewStateAdapter extends FragmentStateAdapter {

    protected int numberOfTabs;

    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0) {
            return new SearchFragment();
        }
        if(position == 1) {
            return new FormSearchFragment();
        }

        return new SearchFragment();
    }

    @NonNull
    public Fragment createFragment(int position, boolean landscape) {
        if(landscape) {
            if (position == 0) {
                return new SearchFragment();
            }
            if (position == 1) {
                return new FormSearchFragment();
            }
        }

        return new SearchFragment();
    }

    @Override
    public int getItemCount() {
        return numberOfTabs;
    }

    public void setNumberOfTabs(int numTabs) {
        this.numberOfTabs = numTabs;
    }
}
