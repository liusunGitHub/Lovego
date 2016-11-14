package com.guosun.lovego.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.guosun.lovego.LovegoApplication;
import com.guosun.lovego.R;
import com.guosun.lovego.config.Config;
import com.guosun.lovego.entity.HttpResult;
import com.guosun.lovego.entity.MovieEntity;
import com.guosun.lovego.entity.Subject;
import com.guosun.lovego.retrofit.http.HttpMethods;
import com.guosun.lovego.retrofit.http.MovieApi;
import com.guosun.lovego.subscribers.HttpOnNextListener;
import com.guosun.lovego.subscribers.ProgressSubscriber;
import com.guosun.lovego.widget.CustomProgressDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitActivity extends BaseActivity implements View.OnClickListener {
    private TextView result_TV;
    private Button only_retrofit_BN;
    private Button retrofit_BN;
    private Button retrofit_BN_all;
    private HttpOnNextListener<List<Subject>> getTopMovieSubjectOnNext;
    private HttpOnNextListener<HttpResult<List<Subject>>> getTopMovieAllOnNext;
    private CustomProgressDialog myProgressDialog;
    private ProgressSubscriber progressSubscriberAll;
    private ProgressSubscriber progressSubscriberSubject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_retrofit);
        application = (LovegoApplication) this.getApplication();
        myProgressDialog = new CustomProgressDialog(this);
        initView();
        initListener();
        getTopMovieSubjectOnNext = new HttpOnNextListener<List<Subject>>(){
            @Override
            public void onNext(List<Subject> subject) {
                result_TV.setText(subject.toString());
                result_TV.setTextColor(Color.BLUE);
            }
        };
        getTopMovieAllOnNext = new HttpOnNextListener<HttpResult<List<Subject>>>(){
            @Override
            public void onNext(HttpResult<List<Subject>> subject) {
                result_TV.setText(subject.toString());
                result_TV.setTextColor(Color.GREEN);
            }
        };
    }

    @Override
    protected boolean showHeader() {
        return false;
    }

    @Override
    protected String getHeaderTitle() {
        return null;
    }


    private void initView() {
        result_TV = (TextView) findViewById(R.id.result_TV);
        only_retrofit_BN = (Button) findViewById(R.id.only_retrofit_BN);
        retrofit_BN = (Button) findViewById(R.id.retrofit_BN);
        retrofit_BN_all = (Button) findViewById(R.id.retrofit_BN_all);
    }

    private void initListener() {
        result_TV.setOnClickListener(this);
        only_retrofit_BN.setOnClickListener(this);
        retrofit_BN.setOnClickListener(this);
        retrofit_BN_all.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == only_retrofit_BN) {
            getMovie();
        }else if (v == retrofit_BN){
//            getMovieSubjectService();
            Intent intent = new Intent(RetrofitActivity.this, MovieListActivity.class);
            startActivity(intent);
        }else if (v == retrofit_BN_all){
            getMovieAllService();

        }
    }

    //进行网络请求
    private void getMovie() {
        myProgressDialog.showProgressDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApi MovieApi = retrofit.create(MovieApi.class);
        Call<MovieEntity> call = MovieApi.getTopMovie(0, 10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                myProgressDialog.dismissProgressDialog();
                result_TV.setTextColor(Color.BLACK);
                result_TV.setText(response.code() + " , "+response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                myProgressDialog.dismissProgressDialog();
                result_TV.setText(t.getMessage());
                result_TV.setTextColor(Color.BLACK);

            }
        });
    }

    private void getMovieAllService(){
        progressSubscriberAll = new ProgressSubscriber<HttpResult<List<Subject>>>(getTopMovieAllOnNext,RetrofitActivity.this,false);
        HttpMethods.getInstance().getTopMovieAll(progressSubscriberAll, 0, 10);

    }
    private void getMovieSubjectService(){
        progressSubscriberSubject = new ProgressSubscriber<List<Subject>>(getTopMovieSubjectOnNext,RetrofitActivity.this,false);
        HttpMethods.getInstance().getTopMovieSubject(progressSubscriberSubject, 0, 10);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressSubscriberAll!=null && !progressSubscriberAll.isUnsubscribed()) {
            progressSubscriberAll.unsubscribe();
        }
        if (progressSubscriberSubject!=null && !progressSubscriberSubject.isUnsubscribed()) {
            progressSubscriberSubject.unsubscribe();
        }
    }
}
