
package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.newsapp.Adapter.Adapter;
import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Headines;
import com.example.newsapp.Model.Sources;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final String API_KEY = "c58c0c43e17843b6a049f903126f133f";

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter adapter;
    private AHBottomNavigation ahBottomNavigation;

    //Search function
    EditText etQuery;
    Button btnSearch;
    Button btnUser;

    List<Articles> articles = new ArrayList<>();
    List<Sources> sources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);


        ahBottomNavigation=findViewById(R.id.bottom_navigation);
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
                    retrieveJson("business","us",API_KEY);
                }
                if(position==2){
                    retrieveJson("sport","us",API_KEY);
                }
                if(position==3){
                    retrieveJson("technology","us",API_KEY);
                }
                if(position==4){
                    retrieveJson("entertaiment","us",API_KEY);
                }
                return true;
            }
        });

        etQuery = findViewById(R.id.etQuery);
        btnSearch = findViewById(R.id.btnSearch);
        btnUser = findViewById(R.id.aboutUs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String country = "us";
        retrieveJson("","us",API_KEY);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("",country,API_KEY);
            }
        });
        //Create button onClickListener
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity_main.class));
                finish();
            }
        });
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

    }

    public void retrieveJson(String query, String country, String apiKey){

        swipeRefreshLayout.setRefreshing(true);
        Call<Headines> call;
        if (!etQuery.getText().toString().equals("")){
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
                    adapter = new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setOnTouchListener(new TranslateAnimationUtil(MainActivity.this,ahBottomNavigation));
                }
            }

            @Override
            public void onFailure(Call<Headines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

//    public static MainActivity getInstance(){
//        return mainActivity;
//    }
}