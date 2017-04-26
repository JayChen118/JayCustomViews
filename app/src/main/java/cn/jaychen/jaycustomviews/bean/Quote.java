package cn.jaychen.jaycustomviews.bean;

/**
 * 报价实体类
 * Created by Jay on 2017/3/30.
 */

public class Quote {

    private long time;
    private float openPrice;
    private float closePrice;
    private float topPrice;
    private float bottomPrice;

    public boolean isRed() {
        return closePrice > openPrice;
    }

    public Quote(long time, float openPrice, float closePrice, float topPrice, float bottomPrice) {
        this.time = time;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.topPrice = topPrice;
        this.bottomPrice = bottomPrice;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(float openPrice) {
        this.openPrice = openPrice;
    }

    public float getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(float closePrice) {
        this.closePrice = closePrice;
    }

    public float getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(float topPrice) {
        this.topPrice = topPrice;
    }

    public float getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(float bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "time=" + time +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", topPrice=" + topPrice +
                ", bottomPrice=" + bottomPrice +
                '}';
    }
}
