package com.guosun.lovego.entity;

/**
 * Created by liuguosheng on 2016/10/31.
 */
public class MovieEntity extends BaseResult{
    private int count;
    private int start;
    private int total;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", resultCode=" + resultCode +
                ", resultMessage=" + resultMessage+
                '}';
    }
}
