package com.devmc.smartcity6.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 * Created by Android Studio.
 * User: Devmc
 * Date: 2022/7/3
 * Time: 10:46
 */
public class CGridView extends GridView {
    public CGridView(Context context) {
        super(context);
    }

    public CGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expand = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expand);
    }
}
