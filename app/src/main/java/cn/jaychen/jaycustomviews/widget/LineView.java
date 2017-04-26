package cn.jaychen.jaycustomviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import java.util.List;
import java.util.Locale;

import cn.jaychen.jaycustomviews.R;
import cn.jaychen.jaycustomviews.utils.UiUtil;

/**
 * 分时图及K线图的父类，负责画背景及标尺
 * Created by Jay on 2017/3/30.
 */

public abstract class LineView extends View {

    protected final float strokeWidth = UiUtil.dpToPx(1);
    private final float textSize = UiUtil.dpToPx(8);
    protected final float textMargin = UiUtil.dpToPx(4);
    protected final float rightPadding = UiUtil.dpToPx(70);

    private Paint grayLinePaint = new Paint();
    protected Paint textPaint = new Paint();


    protected float maxPrice = Float.MIN_VALUE;
    protected float minPrice = Float.MAX_VALUE;
    protected float perPriceRange;
    protected float priceRange;


    // 开始及结束时间值
    protected long startTime = Long.MAX_VALUE;
    protected long endTime = Long.MIN_VALUE;
    protected float timeRange;


    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        grayLinePaint.setColor(getResources().getColor(R.color.gray_29292c));
        grayLinePaint.setStrokeWidth(strokeWidth);


        textPaint.setColor(getResources().getColor(R.color.gray_81808A));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
    }

    protected float calculateXValue(long time) {
        return ((time - startTime) / timeRange) * (getWidth() - rightPadding);
    }

    protected float calculateYValue(float price) {
        return ((maxPrice - price) / priceRange) * getHeight();
    }


    protected String formatPrice(float price) {
        return String.format(Locale.SIMPLIFIED_CHINESE, "%.2f", price);
    }

    protected abstract List<Long> getTimeList();

    protected abstract String formatTime(long time);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画灰白色的横线
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * getHeight() / 8, getWidth(), i * getHeight() / 8, grayLinePaint);
        }

        // 画价格数值
        for (int i = 0; i < 7; i++) {

            String text = formatPrice(maxPrice - i * perPriceRange);

            float textWidth = textPaint.measureText(text) + strokeWidth;// 留空隙

            canvas.drawText(text, getWidth() - textWidth, (i + 1) * (getHeight() / 8) - textMargin, textPaint);

        }

        // 画时间点
        for (Long theLong : getTimeList()) {
            canvas.drawText(formatTime(theLong), calculateXValue(theLong), getHeight(), textPaint);
        }

    }
}
