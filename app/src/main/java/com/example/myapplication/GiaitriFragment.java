package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiaitriFragment extends Fragment {

    private String api = "26137db2e5e34e15a6779c26cf7b865a";
    private ArrayList<ModelClass> modelClassArrayList;
    private Adapter adapter;
    private String country = "us";
    private RecyclerView recyclerViewfgiaitri;
    private String category="science";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.giaitrifragment, null);
        recyclerViewfgiaitri = v.findViewById(R.id.recyclerviewfgiaitri);
        modelClassArrayList = new ArrayList<>();
        recyclerViewfgiaitri.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(), modelClassArrayList);
        recyclerViewfgiaitri.setAdapter(adapter);

        finNews();

        return v;
    }

    private void finNews() {
        ApiUtilities.getApiInterface().getCategoryNews(country,category, 100, api).enqueue(new Callback<mainNews>() {
            @Override
            public void onResponse(@NonNull Call<mainNews> call, @NonNull Response<mainNews> response) {
                if (response.isSuccessful() && response.body() != null) {
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<mainNews> call, @NonNull Throwable t) {
            }
        });
    }
}
