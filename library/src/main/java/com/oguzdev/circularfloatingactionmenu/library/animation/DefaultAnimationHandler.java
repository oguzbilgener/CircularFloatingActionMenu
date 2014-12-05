/*
 *   Copyright 2014 Oguz Bilgener
 */
package com.oguzdev.circularfloatingactionmenu.library.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

/**
 * An example animation handler
 * Animates translation, rotation, scale and alpha at the same time using Property Animation APIs.
 */
public class DefaultAnimationHandler extends MenuAnimationHandler {

    /** duration of animations, in milliseconds */
    protected static final int DURATION = 500;
    /** duration to wait between each of  */
    protected static final int LAG_BETWEEN_ITEMS = 20;
    /** holds the current state of animation */
    private boolean animating;

    public DefaultAnimationHandler() {
        setAnimating(false);
    }

    @Override
    public void animateMenuOpening(Point center) {
        super.animateMenuOpening(center);

        setAnimating(true);

        Animator lastAnimation = null;
        for (int i = 0; i < menu.getSubActionItems().size(); i++) {
            FloatingActionMenu.Item item = menu.getSubActionItems().get(i);
                item.view.setScaleX(0);
                item.view.setScaleY(0);
                item.view.setAlpha(0);

                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, item.x - center.x + item.width / 2);
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, item.y - center.y + item.height / 2);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 720);
                PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
                PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
                PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

                final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(item.view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
                animation.setDuration(DURATION);
                animation.setInterpolator(new OvershootInterpolator(0.9f));
                animation.addListener(new SubActionItemAnimationListener(item, ActionType.OPENING));

                if (i == 0) {
                    lastAnimation = animation;
                }

                // Put a slight lag between each of the menu items to make it asymmetric
                animation.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
                animation.start();
        }
        if(lastAnimation != null) {
            lastAnimation.addListener(new LastAnimationListener());
        }

    }

    @Override
    public void animateMenuClosing(Point center) {
        super.animateMenuOpening(center);

        setAnimating(true);

        Animator lastAnimation = null;
        for (int i = 0; i < menu.getSubActionItems().size(); i++) {
            FloatingActionMenu.Item item = menu.getSubActionItems().get(i);
                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -(item.x - center.x + item.width / 2));
                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -(item.y - center.y + item.height / 2));
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, -720);
                PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
                PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
                PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0);

                final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(item.view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
                animation.setDuration(DURATION);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.addListener(new SubActionItemAnimationListener(item, ActionType.CLOSING));

                if (i == 0) {
                    lastAnimation = animation;
                }

                animation.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
                animation.start();
        }
        if(lastAnimation != null) {
            lastAnimation.addListener(new LastAnimationListener());
        }
    }

    @Override
    public boolean isAnimating() {
        return animating;
    }

    @Override
    protected void setAnimating(boolean animating) {
        this.animating = animating;
    }

    protected class SubActionItemAnimationListener implements Animator.AnimatorListener {

        private FloatingActionMenu.Item subActionItem;
        private ActionType actionType;

        public SubActionItemAnimationListener(FloatingActionMenu.Item subActionItem, ActionType actionType) {
            this.subActionItem = subActionItem;
            this.actionType = actionType;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            restoreSubActionViewAfterAnimation(subActionItem, actionType);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            restoreSubActionViewAfterAnimation(subActionItem, actionType);
        }

        @Override public void onAnimationRepeat(Animator animation) {}
    }
}
