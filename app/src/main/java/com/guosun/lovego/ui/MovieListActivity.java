package com.guosun.lovego.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.TextView;

import com.guosun.lovego.R;
import com.guosun.lovego.adapter.MovieAdapter;
import com.guosun.lovego.config.ConstsData;
import com.guosun.lovego.entity.HttpResult;
import com.guosun.lovego.entity.Subject;
import com.guosun.lovego.retrofit.http.HttpMethods;
import com.guosun.lovego.subscribers.HttpOnNextListener;
import com.guosun.lovego.subscribers.ProgressSubscriber;
import com.guosun.lovego.widget.CustomRecyclerView;
import com.guosun.lovego.widget.HorizontalDividerItemDecoration;
import com.guosun.lovego.widget.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class MovieListActivity extends BaseActivity implements View.OnClickListener {
    private HttpOnNextListener<List<Subject>> getTopMovieSubjectOnNext;
    private HttpOnNextListener<HttpResult<List<Subject>>> getTopMovieAllOnNext;
    private ProgressSubscriber progressSubscriberAll;
    private ProgressSubscriber progressSubscriberSubject;

    private CustomRecyclerView recyclerView_movie_list;
    private TextView change_type;
    private MovieAdapter mAdapter;
    private List<Subject> subjectList;
    private int currentMode;//0 默认listview  ， 1：gridview

    private VerticalDividerItemDecoration mVerticalDividerItemDecoration ;
    private HorizontalDividerItemDecoration mHorizontalDividerItemDecoration ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_movie_list);
        mVerticalDividerItemDecoration = new VerticalDividerItemDecoration.Builder(MovieListActivity.this).build();
        mHorizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(MovieListActivity.this).showLastDivider().build();
        initView();
        subjectList = new ArrayList<>();
        currentMode = ConstsData.ViewModes.MODE_LIST;
        change_type.setText("切换为网格");


        getTopMovieSubjectOnNext = new HttpOnNextListener<List<Subject>>(){
            @Override
            public void onNext(List<Subject> subject) {
                subjectList = subject;
                mAdapter = new MovieAdapter(MovieListActivity.this,subject);
                recyclerView_movie_list.setAdapter(mAdapter);
                recyclerView_movie_list.setVisibility(View.VISIBLE);

            }
        };
        getTopMovieAllOnNext = new HttpOnNextListener<HttpResult<List<Subject>>>(){
            @Override
            public void onNext(HttpResult<List<Subject>> subject) {
            }
        };

        getMovieSubjectService();
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
        recyclerView_movie_list = (CustomRecyclerView) findViewById(R.id.recyclerView_movie_list);
        change_type = (TextView) findViewById(R.id.change_type);
        change_type.setOnClickListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MovieListActivity.this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_movie_list.setLayoutManager(mLayoutManager);
        recyclerView_movie_list.addItemDecoration(mHorizontalDividerItemDecoration);
        //如果确定每个item的内容不会改变RecyclerView的大小，设置这个选项可以提高性能
        recyclerView_movie_list.setHasFixedSize(true);
       }



    @Override
    public void onClick(View v) {
        if(v == change_type){

            if (currentMode == ConstsData.ViewModes.MODE_LIST) {
                GridLayoutManager manager = new GridLayoutManager(MovieListActivity.this, 2);
                mAdapter.setViewMode(ConstsData.ViewModes.MODE_GRID);
                recyclerView_movie_list.removeItemDecoration(mHorizontalDividerItemDecoration);
                recyclerView_movie_list.removeItemDecoration(mVerticalDividerItemDecoration);
                recyclerView_movie_list.addItemDecoration(mVerticalDividerItemDecoration);
                recyclerView_movie_list.addItemDecoration(mHorizontalDividerItemDecoration);
                recyclerView_movie_list.switchLayoutManager(manager);
                recyclerView_movie_list.setAdapter(mAdapter);
                change_type.setText("切换为列表");
                currentMode = ConstsData.ViewModes.MODE_GRID;
            } else if (currentMode == ConstsData.ViewModes.MODE_GRID){
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(MovieListActivity.this);
                mAdapter.setViewMode(ConstsData.ViewModes.MODE_LIST);
                recyclerView_movie_list.removeItemDecoration(mHorizontalDividerItemDecoration);
                recyclerView_movie_list.removeItemDecoration(mVerticalDividerItemDecoration);
                recyclerView_movie_list.addItemDecoration(mHorizontalDividerItemDecoration);
                recyclerView_movie_list.switchLayoutManager(mLayoutManager);
                recyclerView_movie_list.setAdapter(mAdapter);
                change_type.setText("切换为网格");
                currentMode = ConstsData.ViewModes.MODE_LIST;
            }

        }
    }


    private void getMovieAllService(){
        progressSubscriberAll = new ProgressSubscriber<HttpResult<List<Subject>>>(getTopMovieAllOnNext,MovieListActivity.this,true);
        HttpMethods.getInstance().getTopMovieAll(progressSubscriberAll, 0, 10);

    }
    private void getMovieSubjectService(){
        progressSubscriberSubject = new ProgressSubscriber<List<Subject>>(getTopMovieSubjectOnNext,MovieListActivity.this,true);
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
