package com.guosun.lovego.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.lang.ref.WeakReference;

/**
 * Created by liuguosheng on 2016/11/3.
 */
public class CustomProgressDialog {

    //    弱引用防止内存泄露
    private WeakReference<Context> mActivity;
    //    是否能取消请求
    private boolean cancel;
    //    加载框可自己定义
    private ProgressDialog pd;
    private ProgressCancelListener progressCancelListener;

    public CustomProgressDialog(Context context) {
        this.mActivity = new WeakReference<>(context);
        this.cancel = true;//默认可以取消
        initProgressDialog();
    }
    public CustomProgressDialog(Context context, boolean cancel) {
        this.mActivity = new WeakReference<>(context);
        this.cancel = cancel;
        initProgressDialog();
    }

    public void setProgressCancelListener(ProgressCancelListener progressCancelListener) {
        this.progressCancelListener = progressCancelListener;
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog() {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if(progressCancelListener!=null){
                            progressCancelListener.onCancelProgress();
                        }
                    }
                });
            }
        }
    }


    /**
     * 显示加载框
     */
    public void showProgressDialog() {
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    /**
     * 隐藏
     */
    public void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
    public interface ProgressCancelListener {
        void onCancelProgress();
    }
}
