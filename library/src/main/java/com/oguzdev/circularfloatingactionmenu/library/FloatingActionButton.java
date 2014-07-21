/*
 *   Copyright 2014 Oguz Bilgener
 */
package com.oguzdev.circularfloatingactionmenu.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.oguzdev.circularfloatingactionmenu.library.R;

/**
 * An alternative Floating Action Button implementation that can be independently placed in
 * one of 8 different places on the screen.
 */
public class FloatingActionButton extends FrameLayout {

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;

    public static final int POSITION_TOP_CENTER = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_RIGHT_CENTER = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_BOTTOM_CENTER = 5;
    public static final int POSITION_BOTTOM_LEFT = 6;
    public static final int POSITION_LEFT_CENTER = 7;
    public static final int POSITION_TOP_LEFT = 8;

    private View contentView;

    /**
     * Constructor that takes parameters collected using {@link FloatingActionMenu.Builder}
     * @param activity a reference to the activity that will
     * @param layoutParams
     * @param theme
     * @param backgroundDrawable
     * @param position
     * @param contentView
     * @param contentParams
     */
    public FloatingActionButton(Activity activity, LayoutParams layoutParams, int theme, Drawable backgroundDrawable, int position, View contentView, FrameLayout.LayoutParams contentParams) {
        super(activity);
        setPosition(position, layoutParams);

        // If no custom backgroundDrawable is specified, use the background drawable of the theme.
        if(backgroundDrawable == null) {
            if(theme == THEME_LIGHT)
                backgroundDrawable = activity.getResources().getDrawable(R.drawable.button_action_selector);
            else
                backgroundDrawable = activity.getResources().getDrawable(R.drawable.button_action_dark_selector);
        }
        setBackgroundResource(backgroundDrawable);
        if(contentView != null) {
            setContentView(contentView, contentParams);
        }
        setClickable(true);

        attach(layoutParams);
    }

    /**
     * Sets the position of the button by calculating its Gravity from the position parameter
     * @param position one of 8 specified positions.
     * @param layoutParams
     */
    public void setPosition(int position, FrameLayout.LayoutParams layoutParams) {
        int gravity;
        switch(position) {
            case POSITION_TOP_CENTER:
                gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                break;
            case POSITION_TOP_RIGHT:
                gravity = Gravity.TOP | Gravity.RIGHT;
                break;
            case POSITION_RIGHT_CENTER:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
            case POSITION_BOTTOM_CENTER:
                gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                break;
            case POSITION_BOTTOM_LEFT:
                gravity = Gravity.BOTTOM | Gravity.LEFT;
                break;
            case POSITION_LEFT_CENTER:
                gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case POSITION_TOP_LEFT:
                gravity = Gravity.TOP | Gravity.LEFT;
                break;
            case POSITION_BOTTOM_RIGHT:
                default:
                gravity = Gravity.BOTTOM | Gravity.RIGHT;
                break;
        }
        layoutParams.gravity = gravity;
        setLayoutParams(layoutParams);
    }

    /**
     * Sets a content view that will be displayed inside this FloatingActionButton.
     * @param contentView
     */
    public void setContentView(View contentView, FrameLayout.LayoutParams contentParams) {
        this.contentView = contentView;
        FrameLayout.LayoutParams params;
        if(contentParams == null ){
            params =new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            final int margin = getResources().getDimensionPixelSize(R.dimen.action_button_content_margin);
            params.setMargins(margin, margin, margin, margin);
        }
        else {
            params = contentParams;
        }
        params.gravity = Gravity.CENTER;

        contentView.setClickable(false);
        this.addView(contentView, params);
    }

    /**
     * Attaches it to the Activity content view with specified LayoutParams.
     * @param layoutParams
     */
    public void attach(FrameLayout.LayoutParams layoutParams) {
        ((ViewGroup)getActivityContentView()).addView(this, layoutParams);
    }

    /**
     * Detaches it from the Activity content view.
     */
    public void detach() {
        ((ViewGroup)getActivityContentView()).removeView(this);
    }

    /**
     * Finds and returns the main content view from the Activity context.
     * @return the main content view
     */
    public View getActivityContentView() {
        return ((Activity)getContext()).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    private void setBackgroundResource(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        }
        else {
            setBackgroundDrawable(drawable);
        }
    }

    /**
     * A builder for {@link FloatingActionButton} in conventional Java Builder format
     */
    public static class Builder {

        private Activity activity;
        private LayoutParams layoutParams;
        private int theme;
        private Drawable backgroundDrawable;
        private int position;
        private View contentView;
        private LayoutParams contentParams;

        public Builder(Activity activity) {
            this.activity = activity;

            // Default FloatingActionButton settings
            int size = activity.getResources().getDimensionPixelSize(R.dimen.action_button_size);
            int margin = activity.getResources().getDimensionPixelSize(R.dimen.action_button_margin);
            LayoutParams layoutParams = new LayoutParams(size, size, Gravity.BOTTOM | Gravity.RIGHT);
            layoutParams.setMargins(margin, margin, margin, margin);
            setLayoutParams(layoutParams);
            setTheme(FloatingActionButton.THEME_LIGHT);
            setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT);
        }

        public Builder setLayoutParams(LayoutParams params) {
            this.layoutParams = params;
            return this;
        }

        public Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setBackgroundDrawable(int drawableId) {
            return setBackgroundDrawable(activity.getResources().getDrawable(drawableId));
        }

        public Builder setPosition(int position) {
            this.position = position;
            return this;
        }

        public Builder setContentView(View contentView) {
            return setContentView(contentView, null);
        }

        public Builder setContentView(View contentView, LayoutParams contentParams) {
            this.contentView = contentView;
            this.contentParams = contentParams;
            return this;
        }

        public FloatingActionButton build() {
            return new FloatingActionButton(activity,
                                           layoutParams,
                                           theme,
                                           backgroundDrawable,
                                           position,
                                           contentView,
                                           contentParams);
        }
    }

    /**
     * An alias for the FrameLayout.LayoutParams, which is intended to be used
     * while setting Layout parameters to the contentView
     */
    public static class LayoutParams extends FrameLayout.LayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
