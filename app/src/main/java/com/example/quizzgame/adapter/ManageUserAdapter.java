package com.example.quizzgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizzgame.ListUser;
import com.example.quizzgame.R;
import com.example.quizzgame.model.User;

import java.util.ArrayList;

public class ManageUserAdapter extends ArrayAdapter<User> {
    Context context;
    TextView tv_name;
    TextView tv_username;
    ArrayList<User> list;
    View v_del;
    ListUser main;

    public ManageUserAdapter(@NonNull Context context, ListUser activity, ArrayList<User> objects) {
                super(context, 0, objects);
                this.main = activity;
                this.context = context;
                this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_user, null);
        }
        final User item = list.get(position);
        if (v != null){
            tv_username = v.findViewById(R.id.tv_username);
            tv_username.setText(item.getUsername());
        }

        return v;
    }
}
