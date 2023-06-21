package com.example.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.newsapp.Adapter.Adapter;
import com.example.newsapp.ApiClient;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Headines;
import com.example.newsapp.Model.Sources;
import com.example.newsapp.OOP.SavedList;
import com.example.newsapp.R;
import com.example.newsapp.TranslateAnimationUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    final String API_KEY = "c58c0c43e17843b6a049f903126f133f";

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter adapter;
    private AHBottomNavigation ahBottomNavigation;

    //Search function
    EditText etQuery;
    Button btnSearch;

    List<Articles> articles = new ArrayList<>();


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        swipeRefreshLayout =v.findViewById(R.id.swipeRefresh);
        recyclerView = v.findViewById(R.id.recyclerView);


        ahBottomNavigation=v.findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.all, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.euro, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.running, R.color.color_tab_4);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.cpu, R.color.color_tab_5);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.tab_5, R.drawable.cinema, R.color.color_tab_3);

// Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);
        ahBottomNavigation.addItem(item5);
        ahBottomNavigation.setColored(true);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==0){
                    retrieveJson("","us",API_KEY);
                }
                if(position==1){
                    retrieveJson("business AND economy","us",API_KEY);
                }
                if(position==2){
                    retrieveJson("sport AND football","us",API_KEY);
                }
                if(position==3){
                    retrieveJson("technology AND CPU","us",API_KEY);
                }
                if(position==4){
                    retrieveJson("entertainment AND show","us",API_KEY);
                }
                return true;
            }
        });

        etQuery = v.findViewById(R.id.etQuery);
        btnSearch = v.findViewById(R.id.btnSearch);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final String country = "us";
        retrieveJson("","us",API_KEY);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_KEY);
            }
        });
        //Create button onClickListener

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuery.getText().toString().equals("")){
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson(etQuery.getText().toString(),country,API_KEY);
                        }
                    });
                    retrieveJson(etQuery.getText().toString(),country,API_KEY);
                }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retrieveJson("",country,API_KEY);
                        }
                    });
                    retrieveJson("",country,API_KEY);
                }
            }
        });
     return v;
    }

    public void retrieveJson(String query, String country, String apiKey){

        swipeRefreshLayout.setRefreshing(true);
        Call<Headines> call;
        if (!query.equals("")){
            call = ApiClient.getInstance().getApi().getSpecificData(query,apiKey);
        } else {
            call = ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        }

        call.enqueue(new Callback<Headines>() {
            @Override
            public void onResponse(Call<Headines> call, Response<Headines> response) {
                if(response.isSuccessful() && response.body().getArticles() != null){
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(getActivity(),articles);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setOnTouchListener(new TranslateAnimationUtil(getActivity(),ahBottomNavigation));
                }
            }

            @Override
            public void onFailure(Call<Headines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public String getCountry(){
//        Locale locale = Locale.getDefault();
//        String country = locale.getCountry();
//        return country.toLowerCase();
//    }
//


}
