package com.example.antiboredomapp.Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

//this class is mainly to handle the request objects received from the Bored API
public class FunActivity implements Comparable{

    private String        key;
    private String        name;
    private String        type;
    private int           participant_num;
    private String        link;
    private Price         price_obj;
    private Accessibility access;

    public FunActivity() {}

    public FunActivity(String key, String name, String type, int participant_num, String link, Price price_obj, Accessibility access) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.participant_num = participant_num;
        this.link = link;
        this.price_obj = price_obj;
        this.access = access;
    }

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParticipant_num() {
        return participant_num;
    }

    public void setParticipant_num(int participant_num) {
        this.participant_num = participant_num;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Price getPrice_obj() {
        return price_obj;
    }

    public void setPrice_obj(Price price_obj) {
        this.price_obj = price_obj;
    }

    public Accessibility getAccess() {
        return access;
    }

    public void setAccess(Accessibility access) {
        this.access = access;
    }

    @Override
    public int compareTo(Object o) {
        FunActivity fun = (FunActivity) o;

        if(fun.getKey() == this.key)
            return 0;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunActivity that = (FunActivity) o;
        return key == that.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
