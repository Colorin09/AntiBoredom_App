package com.example.antiboredomapp.Data;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antiboredomapp.Model.FunActivityDB;
import com.example.antiboredomapp.R;
import com.example.antiboredomapp.ViewModel.FunActivityViewModel;

public class ActivityListAdapter extends ListAdapter<FunActivityDB, ActivityListAdapter.ActivityViewHolder> {

    public final String TAG = this.getClass().getSimpleName();
    Context context;

    public ActivityListAdapter(Context context, @NonNull DiffUtil.ItemCallback<FunActivityDB> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ActivityViewHolder(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        FunActivityDB funActivityDB = getItem(position);
        holder.bind(funActivityDB);

    }

    //inner class
    public class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv;
        TextView parts_tv;
        TextView price_tv;
        TextView prep_tv;
        TextView link_tv;
        EditText note_et;
        Button clear_note_btn;
        Button delete_act_btn;

        LinearLayout parentLayout;

        public ActivityViewHolder(@NonNull View itemView, Context context)
        {
            super(itemView);

            SavedActivitiesDBHandler savedDb = SavedActivitiesDBHandler.getInstance(context.getApplicationContext());

            name_tv = itemView.findViewById(R.id.fav_activity_title);
            parts_tv = itemView.findViewById(R.id.fav_no_parts);
            price_tv = itemView.findViewById(R.id.fav_no_price);
            prep_tv = itemView.findViewById(R.id.fav_prep);
            link_tv = itemView.findViewById(R.id.fav_link);
            note_et = itemView.findViewById(R.id.fav_note);
            clear_note_btn = itemView.findViewById(R.id.fav_delete_note);
            delete_act_btn = itemView.findViewById(R.id.fav_delete_activity);

            parentLayout = itemView.findViewById(R.id.parent_layout);

            clear_note_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FunActivityDB fun = (FunActivityDB)getItem(getAdapterPosition());
                    note_et.setText("");
                }
            });

            delete_act_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FunActivityDB fun = (FunActivityDB)getItem(getAdapterPosition());
                    savedDb.funActivityDAO().deleteSavedActivity(fun);
                    FunActivityViewModel.deleteSavedActivity(fun);
                }
            });

            note_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    FunActivityDB fun = (FunActivityDB)getItem(getAdapterPosition());
                    fun.setNote(note_et.getText().toString());
                    savedDb.funActivityDAO().updateSavedNote(fun);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    FunActivityDB fun = (FunActivityDB)getItem(getAdapterPosition());
                    fun.setNote(note_et.getText().toString());
                    savedDb.funActivityDAO().updateSavedNote(fun);
                }
            });

        }

        public void bind(FunActivityDB fun)
        {
            name_tv.setText(fun.getName());
            parts_tv.setText(fun.getParticipant_num()+"");
            price_tv.setText(fun.getPrice());
            prep_tv.setText(fun.getAccess());

            if(!fun.getLink().isEmpty())
                link_tv.setText(fun.getLink());

            note_et.setText(fun.getNote());
        }
    }

}
