package com.example.antiboredomapp.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.antiboredomapp.MainActivity;
import com.example.antiboredomapp.R;
import com.example.antiboredomapp.FavoritesActivity;

public class OptionsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionsmenuactivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.menuitem_app_info:
                DialogFrag dialog = new DialogFrag();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                dialog.show(ft, "dialog");
                break;
            case R.id.menuitem_saved_acts:
                Intent seeSavedActivities = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(seeSavedActivities);
                finish();
                break;
            case R.id.menuitem_search:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

}
