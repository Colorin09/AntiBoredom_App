package com.example.antiboredomapp.Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//this class is a simplified version of FunActivity, to easier
//Room database handling
//Also handles notes
@Entity(tableName = "saved_activities")
public class FunActivityDB {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String  key;
    @ColumnInfo(name="KEY_NAME")
    private String name;
    @ColumnInfo(name="KEY_PARTICIPANTS")
    private int    participant_num;
    @ColumnInfo(name="KEY_LINK")
    private String link;
    @ColumnInfo(name="KEY_PRICE")
    private String  price;
    @ColumnInfo(name="KEY_ACCESS")
    private String  access;
    @ColumnInfo(name="KEY_NOTE")
    private String  note;

    public FunActivityDB() {}

    @Ignore
    public FunActivityDB(String key, String name, int participant_num, String link, String price, String access, String note) {
        this.key = key;
        this.name = name;
        this.participant_num = participant_num;
        this.link = link;
        this.price = price;
        this.access = access;
        this.note = note;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunActivityDB that = (FunActivityDB) o;
        return key == that.key;
    }

    @Ignore
    public static DiffUtil.ItemCallback<FunActivityDB> itemCallBack = new DiffUtil.ItemCallback<FunActivityDB>() {
        @Override
        public boolean areItemsTheSame(@NonNull FunActivityDB oldItem, @NonNull FunActivityDB newItem) {

            if(oldItem.getKey() == newItem.getKey())
                return true;

            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FunActivityDB oldItem, @NonNull FunActivityDB newItem) {
            return oldItem.equals(newItem);
        }

    };
}
