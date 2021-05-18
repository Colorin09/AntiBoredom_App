package com.example.antiboredomapp.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.FunActivityDB;

import java.util.List;

//interface to create the database CRUD
@Dao
public interface FunActivityDAO {

    //Read
    @Query("Select * from saved_activities")
    List<FunActivityDB> getSavedActivities();

    //Create
    @Insert
    void insertSavedActivity(FunActivityDB fun);

    //Update
    @Update
    void updateSavedNote(FunActivityDB fun);

    //Delete
    @Delete
    void deleteSavedActivity(FunActivityDB fun);
}
