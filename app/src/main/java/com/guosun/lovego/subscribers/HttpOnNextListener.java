package com.guosun.lovego.subscribers;

/**
 * 成功回调处理
 * Created by liuguosheng on 2016/11/3.
 */
public interface HttpOnNextListener<T> {
    void onNext(T t);
}
