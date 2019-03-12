package com.payfazz.hackernews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.payfazz.hackernews.R;
import com.payfazz.hackernews.adapter.ArticleAdapter;
import com.payfazz.hackernews.adapter.FavAdapter;
import com.payfazz.hackernews.model.Model;
import com.payfazz.hackernews.sqlite.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

    Database db;
    private RecyclerView recycleView;
    private List<Model> list = new ArrayList<>();
    private ImageView image_empty;
    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view =  inflater.inflate(R.layout.fragment_fav, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        image_empty = view.findViewById(R.id.image_empty);

        return view;
    }
    private void setupRecycle() {
        list.clear();
        db = new Database(getContext());
        list = db.getNews();
        checkData(list);
        Collections.sort(list,Model.BY_DATE);
        FavAdapter adapter = new FavAdapter(list, getActivity());
        LinearLayoutManager lm = new GridLayoutManager(getContext(),1);
        recycleView.setLayoutManager(lm);
        recycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    private void checkData( List<Model> list){
       if (list.size()> 0){
           image_empty.setVisibility(View.GONE);
           recycleView.setVisibility(View.VISIBLE);
       }else {
           image_empty.setVisibility(View.VISIBLE);
           recycleView.setVisibility(View.GONE);
       }

    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecycle();


    }
}
