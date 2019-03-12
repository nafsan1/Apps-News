package com.payfazz.hackernews.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.payfazz.hackernews.R;
import com.payfazz.hackernews.adapter.ArticleAdapter;
import com.payfazz.hackernews.api.BaseApiService;
import com.payfazz.hackernews.api.UtilsAPI;
import com.payfazz.hackernews.model.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class HomeFragment extends Fragment {

    private BaseApiService mApiService;
    private RecyclerView recyclerView;
    List<Model> listArticle = new ArrayList<>();
    private ShimmerRecyclerView shimmer_recycler_view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        mApiService = UtilsAPI.getApiService();
        shimmer_recycler_view = (ShimmerRecyclerView) view.findViewById(R.id.shimmer_recycler_view);

        return view;
    }

    private void showData() {
        recyclerView.setVisibility(View.GONE);
        listArticle.clear();
        mApiService.getTopstories().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {
                    List<Integer> topStories = response.body();
                    for (int i = 0; i < 50; i++) {
                        final int finalI = i;
                        mApiService.getArticle(topStories.get(i)).enqueue(new Callback<Model>() {
                            @Override
                            public void onResponse(Call<Model> call, Response<Model> response) {
                                if (response.isSuccessful()) {
                                    Model m = new Model(
                                            response.body().getBy().toString(),
                                            Integer.valueOf(response.body().getId().toString()),
                                            Integer.valueOf(response.body().getTime().toString()),
                                            response.body().getTitle().toString());
                                    listArticle.add(m);

                                    if (finalI == 50-1){
                                        Collections.sort(listArticle, Model.BY_DATE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        setupRecycle(listArticle);
                                        shimmer_recycler_view.hideShimmerAdapter();

                                    }

                                } else {
                                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Model> call, Throwable t) {
                                shimmer_recycler_view.hideShimmerAdapter();
                                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }




                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                shimmer_recycler_view.hideShimmerAdapter();
                Toast.makeText(getContext(), t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecycle(List<Model> listArticle) {

        ArticleAdapter adapter = new ArticleAdapter(listArticle, getActivity());
        LinearLayoutManager lm = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer_recycler_view.showShimmerAdapter();
        showData();
    }
}
