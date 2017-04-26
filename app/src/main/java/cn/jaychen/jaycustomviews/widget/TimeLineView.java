package cn.jaychen.jaycustomviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.jaychen.jaycustomviews.R;
import cn.jaychen.jaycustomviews.bean.TimePrice;
import cn.jaychen.jaycustomviews.utils.FakeDataUtil;
import cn.jaychen.jaycustomviews.utils.TimeUtils;
import cn.jaychen.jaycustomviews.utils.UiUtil;

/**
 * 自定义分时图
 * Created by Jay on 2017/3/29.
 */

public class TimeLineView extends LineView {

    private static final String TAG = "TimeLineView";

    private Paint paint = new Paint();
    private Paint wholePaint = new Paint();
    private Paint backPaint = new Paint();
    private Paint whiteTextPaint = new Paint();
    private Paint pointPaint = new Paint();
    private Paint redPaint = new Paint();
    private Path path = new Path();

    private final float textSize2 = UiUtil.dpToPx(9);
    private final float pointSize = UiUtil.dpToPx(5);
    private final float arrowWidth = UiUtil.dpToPx(11);
    private final float arrowHeight = UiUtil.dpToPx(13);

    private List<TimePrice> list = FakeDataUtil.generateSomeTimePriceData();



    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            list = FakeDataUtil.generateSomeTimePriceData();
        }

        initPaint();

        calculateLimits();
    }

    @Override
    protected List<Long> getTimeList() {

        List<Long> longList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.get(Calendar.MINUTE) > 30) {
            calendar.set(Calendar.MINUTE, 30);
        } else {
            calendar.set(Calendar.MINUTE, 0);
        }

        // 画时间点
        while (calendar.getTimeInMillis() > startTime) {
            longList.add(calendar.getTimeInMillis());
            calendar.add(Calendar.MINUTE, -30);
        }

        return longList;
    }

    @Override
    protected String formatTime(long time) {
        return TimeUtils.getHourMinute(time);
    }

    private void initPaint() {
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(getResources().getColor(R.color.yellow_bd9532));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        backPaint.setColor(getResources().getColor(R.color.yellow_17ffc73c));
        backPaint.setStyle(Paint.Style.FILL);

        wholePaint.setColor(getResources().getColor(R.color.black_151517));
        pointPaint.setColor(getResources().getColor(R.color.yellow_FFC73C));

        redPaint.setColor(getResources().getColor(R.color.red_F9293D));
        redPaint.setAntiAlias(true);

        whiteTextPaint.setColor(Color.WHITE);
        whiteTextPaint.setTextSize(textSize2);
        whiteTextPaint.setAntiAlias(true);

    }

    private void calculateLimits() {
        for (TimePrice price : list) {
            if (price.getPrice() > maxPrice) {
                maxPrice = price.getPrice();
            }
            if (price.getPrice() < minPrice) {
                minPrice = price.getPrice();
            }
            if (price.getTime() > endTime) {
                endTime = price.getTime();
            }
            if (price.getTime() < startTime) {
                startTime = price.getTime();
            }
        }

        perPriceRange = (maxPrice - minPrice) / 6;

        // 添加顶部冗余
        maxPrice += perPriceRange;

        priceRange = perPriceRange * 8;

        timeRange = endTime - startTime;

    }

    private float calculateYValue(TimePrice price) {
        return calculateYValue(price.getPrice());
    }

    private float calculateXValue(TimePrice price) {
        return calculateXValue(price.getTime());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode()) {
//            return;
        }

//        canvas.drawPaint(wholePaint);

        path.reset();
        float x = getWidth() - rightPadding;
        float y;

        for (int i = 0; i < list.size(); i++) {

            x = calculateXValue(list.get(i));
            y = calculateYValue(list.get(i));

            Log.d(TAG, "onDraw: x : " + x);
            Log.d(TAG, "onDraw: y : " + y);

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        // 画黄色折线
        canvas.drawPath(path, paint);

        path.lineTo(x, getHeight());
        path.lineTo(0, getHeight());
        path.close();
        // 画浅黄色底色
        canvas.drawPath(path, backPaint);




        TimePrice lastPrice = list.get(list.size() - 1);
        float lastPriceYValue = calculateYValue(lastPrice);
        String lastPriceText = formatPrice(lastPrice.getPrice());
        float lastPriceTextWidth = whiteTextPaint.measureText(lastPriceText);

        // 画当前数值圆点
        canvas.drawCircle(x, lastPriceYValue, pointSize / 2, pointPaint);

        // 画红线
        canvas.drawLine(0, lastPriceYValue, getWidth() - strokeWidth, lastPriceYValue, redPaint);

        float arrowRight = getWidth() - strokeWidth;
        // 画红色箭头底色
        path.reset();
        path.moveTo(arrowRight, lastPriceYValue - arrowHeight / 2);
        path.lineTo(arrowRight, lastPriceYValue + arrowHeight / 2);
        path.lineTo(arrowRight - lastPriceTextWidth - textMargin, lastPriceYValue + arrowHeight / 2);
        path.lineTo(arrowRight - lastPriceTextWidth - textMargin - arrowWidth, lastPriceYValue);
        path.lineTo(arrowRight - lastPriceTextWidth - textMargin, lastPriceYValue - arrowHeight / 2);

        path.close();

        canvas.drawPath(path, redPaint);

        // 画箭头数值
        canvas.drawText(lastPriceText, arrowRight - lastPriceTextWidth - textMargin,
                lastPriceYValue + whiteTextPaint.getTextSize() / 2 - strokeWidth, whiteTextPaint);


    }
}
