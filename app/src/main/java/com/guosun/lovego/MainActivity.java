package com.guosun.lovego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.guosun.lovego.ui.BaseActivity;
import com.guosun.lovego.ui.UserActivity;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);
        application = (LovegoApplication) this.getApplication();
        initView();
        initListener();
    }

    @Override
    protected boolean showHeader() {
        return false;
    }

    @Override
    protected String getHeaderTitle() {
        return null;
    }

    private void initView(){
        tv_user = (TextView) findViewById(R.id.tv_user);
    }
    private void initListener(){
        tv_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == tv_user){
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }

    }
}
