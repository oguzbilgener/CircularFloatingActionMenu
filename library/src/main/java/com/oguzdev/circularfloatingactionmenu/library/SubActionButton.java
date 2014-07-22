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

/**
 * A simple button implementation with a similar look an feel to{@link FloatingActionButton}.
 */
public class SubActionButton extends FrameLayout {

    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_LIGHTER = 2;
    public static final int THEME_DARKER = 3;

    public SubActionButton(Activity activity, LayoutParams layoutParams, int theme, Drawable backgroundDrawable, View contentView) {
        super(activity);
        setLayoutParams(layoutParams);
        // If no custom backgroundDrawable is specified, use the background drawable of the theme.
        if(backgroundDrawable == null) {
            if(theme == THEME_LIGHT) {
                backgroundDrawable = activity.getResources().getDrawable(R.drawable.button_sub_action_selector);
            }
            else if(theme == THEME_DARK) {
                backgroundDrawable = activity.getResources().getDrawable(R.drawable.button_sub_action_dark_selector);
            }
            else if(theme == THEME_LIGHTER) {
                backgroundDrawable = activity.getResources().getDrawable(R.drawable.button_action_selector);
            }
            else if(theme == THEME_DARKER) {
                backgroundDrawable = activity.getResources().getDrawable(R.drawable.button_action_dark_selector);
            }
            else {
                throw new RuntimeException("Unknown SubActionButton theme: " + theme);
            }
        }
        else {
            backgroundDrawable = backgroundDrawable.mutate().getConstantState().newDrawable();
        }
        setBackgroundResource(backgroundDrawable);
        if(contentView != null) {
            setContentView(contentView);
        }
        setClickable(true);
    }

    /**
     * Sets a content view that will be displayed inside this SubActionButton.
     * @param contentView
     */
    public void setContentView(View contentView) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        final int margin = getResources().getDimensionPixelSize(R.dimen.sub_action_button_content_margin);
        params.setMargins(margin, margin, margin, margin);
        contentView.setLayoutParams(params);
        contentView.setClickable(false);
        this.addView(contentView, params);
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
     * A builder for {@link SubActionButton} in conventional Java Builder format
     */
    public static class Builder {

        private Activity activity;
        private LayoutParams layoutParams;
        private int theme;
        private Drawable backgroundDrawable;
        private View contentView;

        public Builder(Activity activity) {
            this.activity = activity;

            // Default SubActionButton settings
            int size = activity.getResources().getDimensionPixelSize(R.dimen.sub_action_button_size);
            LayoutParams params = new LayoutParams(size, size, Gravity.TOP | Gravity.LEFT);
            setLayoutParams(params);
            setTheme(SubActionButton.THEME_LIGHT);
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

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public SubActionButton build() {
            return new SubActionButton(activity,
                    layoutParams,
                    theme,
                    backgroundDrawable,
                    contentView);
        }
    }

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
