package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private TextView huy;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<ModelClass> articleList;
    private String apiKey = "26137db2e5e34e15a6779c26cf7b865a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        huy = findViewById(R.id.huy);
        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleList = new ArrayList<>();

        adapter = new Adapter(this, articleList);
        recyclerView.setAdapter(adapter);


        searchView.setIconified(false);
        searchView.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchArticles(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        huy.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MenuActivity.class);
            startActivity(intent);
        });


    }


    private void searchArticles(String query) {
        // Gọi API để tìm kiếm các bài báo
        ApiUtilities.getApiInterface().searchNews(query, "en", apiKey).enqueue(new Callback<mainNews>() {
            @Override
            public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleList.clear();
                    articleList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<mainNews> call, Throwable t) {
            }
        });
    }
}