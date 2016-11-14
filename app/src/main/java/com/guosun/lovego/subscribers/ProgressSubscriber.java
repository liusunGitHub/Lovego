package com.guosun.lovego.subscribers;

import android.content.Context;

import com.guosun.lovego.util.ToastUtils;
import com.guosun.lovego.widget.CustomProgressDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liuguosheng on 2016/11/3.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements CustomProgressDialog.ProgressCancelListener {
    //    回调接口
    private HttpOnNextListener mSubscriberOnNextListener;
    //    是否能取消请求
    private boolean cancel;
    //    加载框可自己定义
    private CustomProgressDialog pd;

    public ProgressSubscriber(HttpOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.cancel = true;
        initProgressDialog(context);
    }

    public ProgressSubscriber(HttpOnNextListener mSubscriberOnNextListener, Context context, boolean cancel) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.cancel = cancel;
        initProgressDialog(context);
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(Context context) {
        pd = new CustomProgressDialog(context,cancel);
        pd.setProgressCancelListener(this);
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        pd.showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        pd.dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {
        Throwable e = throwable;
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            e.printStackTrace();
            ToastUtils.ShowToast_long("网络繁忙，请稍候");
        } else {
            e.printStackTrace();
            ToastUtils.ShowToast_long(e.toString());
        }
        pd.dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
//        if (!this.isUnsubscribed()) {
//            this.unsubscribe();
//        }
    }
}
