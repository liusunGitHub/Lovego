package com.guosun.lovego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.guosun.lovego.event.TestMsgEvent;
import com.guosun.lovego.ui.BaseActivity;
import com.guosun.lovego.ui.UserActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity1 extends BaseActivity implements View.OnClickListener{

    private TextView tv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main1);
        application = (LovegoApplication) this.getApplication();
        EventBus.getDefault().register(this);
        initView();
        initListener();
    }

    @Subscribe
    public void testMsg(TestMsgEvent event){
        tv_user.setText(event.msg);
//        ToastUtils.ShowToast_long(event.msg);
    }

    @Override
    protected boolean showHeader() {
        return false;
    }

    @Override
    protected String getHeaderTitle() {
        return "首页";
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
            Intent intent = new Intent(MainActivity1.this, UserActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
