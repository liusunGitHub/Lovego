package com.guosun.lovego.entity;

/**
 * 接口返回统一格式
 * Created by liuguosheng on 2016/11/1.
 */
public class HttpResult<T> extends BaseResult{
//    {
//        "resultCode": 0,
//            "resultMessage": "成功",
//            "data":
//    }
    //用来模仿Data
    private T subjects;
    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("resultCode=" + resultCode + " resultMessage=" + resultMessage);
        if (null != subjects) {
            sb.append(" subjects:" + subjects.toString());
        }
        return sb.toString();
    }
}
