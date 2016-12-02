package com.guosun.lovego.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.guosun.lovego.LovegoApplication;
import com.guosun.lovego.R;
import com.guosun.lovego.entity.LovegoInfo;
import com.guosun.lovego.event.TestMsgEvent;
import com.guosun.lovego.util.ULog;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HandlerActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_content;
    private TextView tv_content2;
    private HandlerThread mHandlerThread;

    //与UI线程管理的handler
    private Handler uiHandler = new Handler();
    private Handler threadHandler;

    private Subscriber<Long> updataInfo;

    //界面关闭后，不请求数据
    private boolean isUpdateInfo = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_handler);
        application = (LovegoApplication) this.getApplication();
        LovegoInfo.testName = "test666";
        initView();
        initBackThread();
        setTitle(HandlerActivity.this.getLocalClassName());
    }

    @Override
    protected boolean showHeader() {
        return false;
    }

    @Override
    protected String getHeaderTitle() {
        return HandlerActivity.class.getSimpleName();
    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content2 = (TextView) findViewById(R.id.tv_content2);
        tv_content2.setOnClickListener(this);
    }
    private void initBackThread(){
        mHandlerThread = new HandlerThread("check-message-coming");
        mHandlerThread.start();
        threadHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                checkForUpdate();
                if(isUpdateInfo)
                threadHandler.sendEmptyMessage(0);
            }
        };

    }

    private void checkForUpdate(){
        try
        {
            //模拟耗时
            Thread.sleep(1000);
            uiHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    ULog.i("HandlerActivity","实时更新中...........");
                    String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                    result = String.format(result, (int) (Math.random() * 3000 + 1000));
                    tv_content.setText(Html.fromHtml(result));
                }
            });

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isUpdateInfo = true;
        threadHandler.sendEmptyMessage(0);
        updataInfo = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long number) {
                ULog.e("HandlerActivity","实时更新中..........."+number);
                String result = "实时更新中，当前大盘指数：<font color='green'>%d</font>";
                result = String.format(result, (int) (Math.random() * 3000 + 1000));
                tv_content2.setText(Html.fromHtml(result));
            }
        };

        Observable.interval(0,1,TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(updataInfo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isUpdateInfo = false;
        if(!updataInfo.isUnsubscribed())
        updataInfo.unsubscribe();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ULog.i("HandlerActivity","onDestroy...........");
        isUpdateInfo = false;
        //释放资源
        mHandlerThread.quit();
    }
    @Override
    public void onClick(View v) {
        if(v == tv_content2){
            Intent intent = new Intent(HandlerActivity.this, RetrofitActivity.class);
            startActivity(intent);
            EventBus.getDefault().post(new TestMsgEvent("RetrofitActivity。。。"));

        }
    }

}
