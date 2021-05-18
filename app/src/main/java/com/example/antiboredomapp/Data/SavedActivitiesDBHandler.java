package com.example.antiboredomapp.Data;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.FunActivityDB;

//Creating and settin gup the database with Room
@Database(entities = FunActivityDB.class, exportSchema = false, version = 2)
public abstract class SavedActivitiesDBHandler extends RoomDatabase {
    private static final String DB_NAME = "activities_db";
    private static SavedActivitiesDBHandler instance;

    public static synchronized SavedActivitiesDBHandler getInstance(Context context)
    {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SavedActivitiesDBHandler.class,
                    DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract FunActivityDAO funActivityDAO();
}
