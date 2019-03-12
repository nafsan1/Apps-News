package com.payfazz.hackernews.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.payfazz.hackernews.R;
import com.payfazz.hackernews.model.Model;
import com.payfazz.hackernews.sqlite.Database;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Model> articleList;
    private Activity activity;
    private Database db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id, txt_title, txt_author, txt_date;
        ImageView image_fav;


        public MyViewHolder(View view) {
            super(view);
            txt_id = view.findViewById(R.id.txt_id);
            txt_title = view.findViewById(R.id.txt_title);
            txt_author = view.findViewById(R.id.txt_author);
            txt_date = view.findViewById(R.id.txt_date);
            image_fav = view.findViewById(R.id.image_fav);

        }
    }

    public ArticleAdapter(List<Model> articleList, Activity activity) {
        this.articleList = articleList;
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

        final Model model = articleList.get(position);
        holder.txt_id.setText("#" + String.valueOf(model.getId()));
        holder.txt_title.setText(model.getTitle());
        holder.txt_author.setText("Author: " + model.getBy());
        holder.txt_date.setText(convertFromUnix(model.getTime()));
        holder.image_fav.bringToFront();
        final String sql = "SELECT * FROM tbl_news WHERE id_news ='" +model.getId() + "'";
        if (db.getData(sql).getCount()>0){
            holder.image_fav.setImageResource(R.drawable.ic_favorite_red_24dp);

        }else {
            holder.image_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        holder.image_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String by = model.getBy();
                int id = model.getId();
                int time = model.getTime();
                String title = model.getTitle();


                if (db.getData(sql).getCount()>0){
                    db.deleteData(id);
                    holder.image_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    Toast.makeText(activity, "Succesfully!", Toast.LENGTH_SHORT).show();
                }else {
                    db.insertNews(by,id,time,title);
                    holder.image_fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                    Toast.makeText(activity, "Succesfully!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    private String convertFromUnix(long time) throws NullPointerException, IllegalArgumentException {

        String result = "";
        Date date = new Date(time * 1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        result = format.format(date);
        Log.d("date", format.format(date));


        return result;
    }

}

