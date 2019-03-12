package com.payfazz.hackernews.api;



import com.payfazz.hackernews.model.Model;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    /*@FormUrlEncoded
    @POST("AKfycbxB5RVPFfecAjzOwfcc7ZX2TGYwcKV8_A6qR4eD/exec")
    Call<ReadDataResponse> readData(@Field("action") String action,
                                    @Field("tabelName") String tabelName);
*/
    @GET("v0/topstories.json")
        Call <List<Integer>>getTopstories();

    @GET("v0/item/{articleid}.json")
    Call<Model> getArticle (@Path("articleid") int id);

}
