package com.payfazz.hackernews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Model {
    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("title")
    @Expose
    private String title;

    public Model(String by, Integer id, Integer time, String title) {
        this.by = by;
        this.id = id;
        this.time = time;
        this.title = title;
    }
    public Model(){}
    public static final Comparator<Model> BY_DATE = new Comparator<Model>() {
        @Override
        public int compare(Model o1, Model o2) {

            return o1.time.compareTo(o2.time);
        }
    };

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
