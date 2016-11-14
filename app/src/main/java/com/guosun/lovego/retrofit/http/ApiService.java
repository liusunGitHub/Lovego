package com.guosun.lovego.retrofit.http;

import com.guosun.lovego.entity.HttpResult;
import com.guosun.lovego.entity.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liuguosheng on 2016/10/31.
 */
public interface ApiService {
    @GET("top250")
//    @GET("http://192.168.113.129/liuguosheng/da1ta.php")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
