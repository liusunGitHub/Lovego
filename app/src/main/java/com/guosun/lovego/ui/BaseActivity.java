package com.guosun.lovego.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.guosun.lovego.LovegoApplication;
import com.guosun.lovego.R;


public abstract class BaseActivity extends AppCompatActivity{
    protected LovegoApplication application;
    private int layoutResID;
    private RelativeLayout layout_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        application = (LovegoApplication) this.getApplication();
        getSupportActionBar().hide();//隐藏主题
        layout_content = (RelativeLayout) findViewById(R.id.layout_content);

    }

    protected void setContentLayout(int layoutResID){
        this.layoutResID = layoutResID;
        View view = LayoutInflater.from(this)
                .inflate(layoutResID,null);
        layout_content.addView(view);
    };
    protected abstract boolean showHeader();
    protected abstract String getHeaderTitle();


}
