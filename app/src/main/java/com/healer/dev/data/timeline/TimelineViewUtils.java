package com.healer.dev.data.timeline;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class TimelineViewUtils {

    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    private static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

}
