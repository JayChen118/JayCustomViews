package cn.jaychen.jaycustomviews.bean;


import cn.jaychen.jaycustomviews.utils.TimeUtils;

/**
 * 分时图数据类
 * Created by Jay on 2017/3/29.
 */

public class TimePrice {

    private long time;
    private float price;

    public TimePrice(long time, float price) {
        this.time = time;
        this.price = price;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TimePrice{" +
                "time=" + TimeUtils.getHourMinute(time) +
                ", price=" + price +
                '}';
    }
}
