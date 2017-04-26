package cn.jaychen.jaycustomviews.utils;

import android.content.res.Resources;

/**
 * UI工具类
 * Created Jay on 2017/3/29.
 */

public class UiUtil {

    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static float pxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

}
