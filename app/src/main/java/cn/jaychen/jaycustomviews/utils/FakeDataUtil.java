package cn.jaychen.jaycustomviews.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.jaychen.jaycustomviews.bean.Quote;
import cn.jaychen.jaycustomviews.bean.TimePrice;

/**
 * Mock数据生成工具类
 * Created by Jay on 2017/3/29.
 */

public class FakeDataUtil {

    private static Random random = new Random();

    public static List<TimePrice> generateSomeTimePriceData() {
        Date date = new Date();
        long time = date.getTime();

        List<TimePrice> list = new ArrayList<>();

        float price = 3700;

        for (int i = 0; i < 100; i++) {
            if (random.nextBoolean()) {
                price += random.nextFloat() * 10;
            } else {
                price -= random.nextFloat() * 10;
            }
            list.add(0, new TimePrice(time -= 60000, price));
        }

        return list;
    }

    public static List<Quote> generateSomeQuoteData() {
        Date date = new Date();
        long time = date.getTime();

        List<Quote> list = new ArrayList<>();

        Random random = new Random();

        float price = 3700;

        for (int i = 0; i < 60; i++) {
            List<Float> floats = getFourFloat(price);
            Quote quote;
            if (random.nextBoolean()) {
                quote = new Quote(time -= 60000, floats.get(1), floats.get(2), floats.get(3), floats.get(0));
            } else {
                quote = new Quote(time -= 60000, floats.get(2), floats.get(1), floats.get(3), floats.get(0));
            }
            list.add(0, quote);
            price = quote.getClosePrice();
        }

        return list;
    }

    private static List<Float> getFourFloat(float price) {
        List<Float> list = new ArrayList<>();

        list.add(price);

        for (int i = 0; i < 3; i++) {
            if (random.nextBoolean()) {
                price += random.nextFloat() * 10;
            } else {
                price -= random.nextFloat() * 10;
            }
            list.add(price);
        }
        Collections.sort(list);
        return list;
    }



    public static double[] getPrices() {
        return new double[]{1638.45, 1638.45, 1638.45, 1638.45};
    }



    public static void main(String[] args) {
/*        List<TimePrice> list = generateSomeTimePriceData();

        for (TimePrice timePrice : list) {
            System.out.println(timePrice.toString());
        }*/

/*        List<Float> floatList = getFourFloat(1000);
        for (Float theFloat : floatList) {
            System.out.println(theFloat);
        }*/

/*        List<Quote> quoteList = generateSomeQuoteData();
        for (Quote quote : quoteList) {
            System.out.println(quote.toString());
        }*/

    }
}
