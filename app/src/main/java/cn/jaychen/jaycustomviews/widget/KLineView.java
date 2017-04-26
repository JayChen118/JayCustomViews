package cn.jaychen.jaycustomviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.jaychen.jaycustomviews.R;
import cn.jaychen.jaycustomviews.bean.Quote;
import cn.jaychen.jaycustomviews.utils.FakeDataUtil;
import cn.jaychen.jaycustomviews.utils.TimeUtils;

/**
 * K线图
 * Created by Jay on 2017/3/30.
 */

public class KLineView extends LineView {

    private static final String TAG = "KLineView";

    private List<Quote> list = FakeDataUtil.generateSomeQuoteData();

    private Paint redPaint = new Paint();
    private Paint greenPaint = new Paint();

    private float candleWidth = 0;

    public KLineView(Context context) {
        this(context, null);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

        calculateLimits();
    }

    @Override
    protected List<Long> getTimeList() {
        List<Long> longList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);

        while (calendar.getTimeInMillis() < endTime) {
            longList.add(calendar.getTimeInMillis());
            calendar.add(Calendar.MINUTE, 15);
        }
        return longList;
    }

    @Override
    protected String formatTime(long time) {
        return TimeUtils.getHourMinute(time);
    }

    private void initPaint() {
        redPaint.setColor(getResources().getColor(R.color.red_F9293D));
        greenPaint.setColor(getResources().getColor(R.color.green_19962A));
    }


    private void calculateLimits() {
        for (Quote quote : list) {
            if (quote.getTopPrice() > maxPrice) {
                maxPrice = quote.getTopPrice();
            }
            if (quote.getBottomPrice() < minPrice) {
                minPrice = quote.getBottomPrice();
            }
            if (quote.getTime() > endTime) {
                endTime = quote.getTime();
            }
            if (quote.getTime() < startTime) {
                startTime = quote.getTime();
            }
        }

        perPriceRange = (maxPrice - minPrice) / 6;

        // 添加顶部冗余
        maxPrice += perPriceRange;

        priceRange = perPriceRange * 8;

        timeRange = endTime - startTime;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        candleWidth = (getWidth() - rightPadding) / list.size() - strokeWidth;

        for (Quote quote : list) {
            float x = calculateXValue(quote.getTime());
            canvas.drawLine(x, calculateYValue(quote.getTopPrice()), x,
                    calculateYValue(quote.getBottomPrice()), quote.isRed() ? redPaint : greenPaint);

            Log.d(TAG, "onDraw: candleWidth : " + candleWidth);
            Log.d(TAG, "onDraw: x - candleWidth / 2 : " + (x - candleWidth / 2));
            Log.d(TAG, "onDraw: calculateYValue(quote.getTopPrice()) : " + calculateYValue(quote.getTopPrice()));
            Log.d(TAG, "onDraw: x + candleWidth / 2 : " + (x + candleWidth / 2));
            Log.d(TAG, "onDraw: calculateYValue(quote.getBottomPrice()) : " + calculateYValue(quote.getBottomPrice()));
            Log.d(TAG, "onDraw: height : " + getHeight());

            if (quote.isRed()) {

                canvas.drawRect(x - candleWidth / 2, calculateYValue(quote.getClosePrice()),
                        x + candleWidth / 2, calculateYValue(quote.getOpenPrice()), redPaint);
            } else {
                canvas.drawRect(x - candleWidth / 2, calculateYValue(quote.getOpenPrice()),
                        x + candleWidth / 2, calculateYValue(quote.getClosePrice()), greenPaint);

            }
        }
    }
}
