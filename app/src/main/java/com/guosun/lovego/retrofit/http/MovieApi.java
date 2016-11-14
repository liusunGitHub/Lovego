package com.guosun.lovego.retrofit.http;

import com.guosun.lovego.entity.MovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liuguosheng on 2016/10/31.
 */
public interface MovieApi {
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);
}
