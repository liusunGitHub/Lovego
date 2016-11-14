package com.guosun.lovego.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.guosun.lovego.LovegoApplication;
import com.guosun.lovego.R;
import com.guosun.lovego.config.ConstsData;
import com.guosun.lovego.domain.service.AsycGetUserInfoService;
import com.guosun.lovego.entity.LovegoInfo;
import com.guosun.lovego.entity.UserInfoModle;


public class UserActivity extends BaseActivity implements View.OnClickListener{
    private LovegoApplication application;
    private TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_user);
        application = (LovegoApplication) this.getApplication();
        initView();
        initListener();
//        getSn();

    }

    @Override
    protected boolean showHeader() {
        return false;
    }

    @Override
    protected String getHeaderTitle() {
        return "个人中心";
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(UserActivity.this.getLocalClassName()+ " , "+LovegoInfo.testName+ " , "+application);
        tv_title.setText(UserActivity.this.getLocalClassName()+ " , "+LovegoInfo.testName+ " , "+application);

        LovegoInfo.testName = "test666";
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
    }
    private void initListener(){
        tv_title.setOnClickListener(this);
    }

    private void getSn() {

        String sn = application.getSharedPreferences(ConstsData.SharedPreferences.SN);
        AsycGetUserInfoService ss = new AsycGetUserInfoService(sn);
        ss.setFirstInfoServiceLinstener(new AsycGetUserInfoService.GetFirstInfoService() {

            @Override
            public void Success(String result, UserInfoModle item) {
                // TODO Auto-generated method stub
                if (item != null && !TextUtils.isEmpty(item.sn)) {
                    tv_title.setText(item.sn);
                }
            }

            @Override
            public void Failure() {
                // TODO Auto-generated method stub

            }

            @Override
            public void Error() {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == tv_title){
            Intent intent = new Intent(UserActivity.this, HandlerActivity.class);
            startActivity(intent);
        }
    }
}
