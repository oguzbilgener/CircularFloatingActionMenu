/*
 *   Copyright 2014 Oguz Bilgener
 */
package com.oguzdev.circularfloatingactionmenu.library.animation;

import android.graphics.Point;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.view.ViewHelper;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.NineOldHelper;

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

            ViewHelper.setScaleX(menu.getSubActionItems().get(i).view, 0);
            ViewHelper.setScaleY(menu.getSubActionItems().get(i).view, 0);
            ViewHelper.setAlpha(menu.getSubActionItems().get(i).view, 0);

            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2);
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat("rotation", 720);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat("scaleX", 1);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat("scaleY", 1);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat("alpha", 1);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(NineOldHelper.getRealTarget(menu.getSubActionItems().get(i).view), pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);

            // 使用ValueAnimator,效果同上
//            final ValueAnimator animation = ValueAnimator.ofPropertyValuesHolder(pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
//            final View targetView = menu.getSubActionItems().get(i).view;
//            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                public void onAnimationUpdate(ValueAnimator animation) {
//
//                    ViewHelper.setTranslationX(targetView, (Float)animation.getAnimatedValue("translationX"));
//                    ViewHelper.setTranslationY(targetView, (Float)animation.getAnimatedValue("translationY"));
//                    ViewHelper.setRotation(targetView, (Float)animation.getAnimatedValue("rotation"));
//                    ViewHelper.setScaleX(targetView, (Float)animation.getAnimatedValue("scaleX"));
//                    ViewHelper.setScaleY(targetView, (Float)animation.getAnimatedValue("scaleY"));
//                    ViewHelper.setAlpha(targetView, (Float)animation.getAnimatedValue("alpha"));
//                }
//            });
//            animation.setTarget(targetView);

            animation.setDuration(DURATION);
            animation.setInterpolator(new OvershootInterpolator(0.9f));
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i), ActionType.OPENING));

            if(i == 0) {
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
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationX", - (menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2));
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", - (menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2));
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat("rotation", -720);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat("scaleX", 0);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat("scaleY", 0);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat("alpha", 0);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(NineOldHelper.getRealTarget(menu.getSubActionItems().get(i).view), pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i), ActionType.CLOSING));

            if(i == 0) {
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
