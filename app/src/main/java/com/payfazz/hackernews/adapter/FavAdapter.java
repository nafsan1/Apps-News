package com.payfazz.hackernews.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.payfazz.hackernews.R;
import com.payfazz.hackernews.model.Model;
import com.payfazz.hackernews.sqlite.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {

    private List<Model> favList = new ArrayList<>();
    private List<Model> favListItem = new ArrayList<>();
    private Activity activity;
    private Database db;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id, txt_title, txt_author, txt_date;
        ImageView image_fav,image_delete;
        LinearLayout layout;


        public MyViewHolder(View view) {
            super(view);
            txt_id = view.findViewById(R.id.txt_id);
            txt_title = view.findViewById(R.id.txt_title);
            txt_author = view.findViewById(R.id.txt_author);
            txt_date = view.findViewById(R.id.txt_date);
            image_fav = view.findViewById(R.id.image_fav);
            image_delete = view.findViewById(R.id.image_delete);
            layout = view.findViewById(R.id.layout);

        }
    }

    public FavAdapter(List<Model> favList, Activity activity) {
        this.favList = favList;
        this.activity = activity;
        db = new Database(activity);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Model model = favList.get(position);
        holder.txt_title.setText(model.getTitle());
        holder.image_fav.setVisibility(View.GONE);
        holder.txt_author.setVisibility(View.GONE);
        holder.txt_id.setVisibility(View.GONE);
        holder.image_delete.setVisibility(View.VISIBLE);
        holder.image_delete.bringToFront();
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = model.getId();
                db.deleteData(id);
                onItemDismiss(position);

                Toast.makeText(activity, "Sucessfully!!", Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void onItemDismiss(int position) {
        if(position!=-1 && position<favList.size())
        {
            favList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }
    @Override
    public int getItemCount() {
        return  (null != favList ? favList.size() : 0);
    }
}

