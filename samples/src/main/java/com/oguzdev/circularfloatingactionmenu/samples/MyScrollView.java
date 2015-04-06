package com.oguzdev.circularfloatingactionmenu.samples;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Capture Layout resize before API 11
 *
 * @see <a href="http://stackoverflow.com/questions/4888624/android-need-to-use-onsizechanged-for-view-getwidth-height-in-class-extending">onsizechanged</a>
 */
public class MyScrollView extends ScrollView {

    private OnSizeChangeListener listener;

    public interface OnSizeChangeListener {
        void onSizeChange(View v, int w, int h, int oldw, int oldh);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        listener.onSizeChange(this, w, h, oldw, oldh);
    }

    public void addSizeChangeListener(OnSizeChangeListener listener) {
        this.listener = listener;
    }
}
