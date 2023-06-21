package com.example.newsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.newsapp.DB.SavedDB;
import com.example.newsapp.MainActivity_main;
import com.example.newsapp.OOP.SavedList;
import com.example.newsapp.R;
import com.example.newsapp.tool.Check;


public class ShowUser extends Fragment {

    private SavedDB savedDB;
    TextView tvName,tvEmail,tvSdt,tvPassword;
    Button btnlogOut;
    public ShowUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_user, container, false);
        tvName = v.findViewById(R.id.UserName);
        tvEmail = v.findViewById(R.id.UserEmail);
        tvSdt = v.findViewById(R.id.UserPhone);
        tvPassword = v.findViewById(R.id.UserPass);
        btnlogOut = v.findViewById(R.id.logout);
        savedDB = new SavedDB(getActivity());


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String session_mail = preferences.getString("Check_Email",null);
        if(session_mail!=null) {
            SQLiteDatabase db = savedDB.getReadableDatabase();
            Cursor cursor = savedDB.ReadUser(session_mail);
            try {
                while (cursor.moveToNext()) {
                    String getEmail = cursor.getString(cursor.getColumnIndex(savedDB.EMAIL));
                    String getName = cursor.getString(cursor.getColumnIndex(savedDB.NAME));
                    String getSdt = cursor.getString(cursor.getColumnIndex(savedDB.SDT));
                    String getPass = cursor.getString(cursor.getColumnIndex(savedDB.PASSWORD));
                    tvName.setText(getName);
                    tvSdt.setText(getSdt);
                    tvEmail.setText(getEmail);
                    tvPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tvPassword.setText(getPass);
                        }
                    });
                }
            } finally {
                if (cursor != null && cursor.isClosed())
                    cursor.close();
                db.close();
            }
        }
        btnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), MainActivity_main.class));
            }
        });




        return v;

    }
}