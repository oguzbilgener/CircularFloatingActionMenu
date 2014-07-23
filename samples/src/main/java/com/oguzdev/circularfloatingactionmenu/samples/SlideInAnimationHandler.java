package com.oguzdev.circularfloatingactionmenu.samples;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.animation.MenuAnimationHandler;

/**
 * Created by oguzbilgener on 23/07/14.
 */
public class SlideInAnimationHandler extends MenuAnimationHandler {
    /** duration of animations, in milliseconds */
    protected static final int DURATION = 700;
    /** duration to wait between each of  */
    protected static final int LAG_BETWEEN_ITEMS = 100;

    protected static final int DIST_Y = 1000;

    /** holds the current state of animation */

    private boolean animating;

    public SlideInAnimationHandler() {
        setAnimating(false);
    }

    @Override
    public void animateMenuOpening(Point center) {
        super.animateMenuOpening(center);

        setAnimating(true);

        Animator lastAnimation = null;
        for (int i = 0; i < menu.getSubActionItems().size(); i++) {

            menu.getSubActionItems().get(i).view.setAlpha(0);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) menu.getSubActionItems().get(i).view.getLayoutParams();
            params.setMargins(menu.getSubActionItems().get(i).x, menu.getSubActionItems().get(i).y + DIST_Y, 0, 0);
            menu.getSubActionItems().get(i).view.setLayoutParams(params);

//            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, menu.getSubActionItems().get(i).x/* - center.x + menu.getSubActionItems().get(i).width / 2*/);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -DIST_Y);
//            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
//            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(menu.getSubActionItems().get(i).view, pvhY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i), ActionType.OPENING));

            if(i == 0) {
                lastAnimation = animation;
            }

            animation.setStartDelay(Math.abs(menu.getSubActionItems().size()/2-i) * LAG_BETWEEN_ITEMS);
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
//            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, - (menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2));
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, DIST_Y);
//            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0);
//            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(menu.getSubActionItems().get(i).view, pvhY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i), ActionType.CLOSING));

            if(i == 0) {
                lastAnimation = animation;
            }

            if(i <= menu.getSubActionItems().size()/2) {
                animation.setStartDelay(i * LAG_BETWEEN_ITEMS);
            }
            else {
                animation.setStartDelay((menu.getSubActionItems().size() - i) * LAG_BETWEEN_ITEMS);
            }
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
