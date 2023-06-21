package com.example.newsapp.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newsapp.Adapter.Adapter;
import com.example.newsapp.Adapter.SavedAdapter;
import com.example.newsapp.DB.SavedDB;
import com.example.newsapp.OOP.SavedList;
import com.example.newsapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private SavedDB savedDB;
    private RecyclerView recyclerView;
    private SavedAdapter savedAdapter;
    ArrayList <SavedList> savedList = new ArrayList<>();
    private TextView delete;
    private SwipeRefreshLayout refreshLayout;
    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        savedDB = new SavedDB(getActivity());
        refreshLayout = v.findViewById(R.id.swipeRefreshsaved);

        recyclerView = v.findViewById(R.id.recyclerViewSaved);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        delete = v.findViewById(R.id.delete);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedDB.deleteAll();
                loadData();
            }
        });

        loadData();
        return v;
    }

    private void loadData() {
        refreshLayout.setRefreshing(true);
        if(savedList !=null){
            savedList.clear();
        }
        SQLiteDatabase db = savedDB.getReadableDatabase();
        Cursor cursor = savedDB.ReadAllData();
        try {
            while (cursor.moveToNext()){
                String tittle = cursor.getString(cursor.getColumnIndex(savedDB.NEWS_TITTLE));
                String source = cursor.getString(cursor.getColumnIndex(savedDB.NEWS_SOURCE));
                String time = cursor.getString(cursor.getColumnIndex(savedDB.NEWS_TIME));
                String desc = cursor.getString(cursor.getColumnIndex(savedDB.NEWS_DESC));
                String img_url = cursor.getString(cursor.getColumnIndex(savedDB.NEWS_IMG_URL));
                String url = cursor.getString(cursor.getColumnIndex(savedDB.NEWS_URL));
                SavedList savedListOOP = new SavedList(tittle,source,time,desc,img_url,url);
                savedList.add(savedListOOP);
            }
        }finally {
            if(cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }
        savedAdapter = new SavedAdapter(getActivity(), savedList);
        recyclerView.setAdapter(savedAdapter);
        refreshLayout.setRefreshing(false);
    }

}
