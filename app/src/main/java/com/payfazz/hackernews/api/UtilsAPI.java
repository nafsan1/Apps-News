package com.payfazz.hackernews.api;

public class UtilsAPI {

    public static final String BASE_ROOT_URL = "https://hacker-news.firebaseio.com/";

    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_ROOT_URL).create(BaseApiService.class);
    }

}
