package org.jokar.gankio.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义Fab的Behavior
 * Created by JokAr on 16/9/21.
 */

public class ScrollAwareFabBehavior extends FloatingActionButton.Behavior {

    private boolean isAnimatingOut = false;

    public ScrollAwareFabBehavior() {
    }

    public ScrollAwareFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child,
                                       View directTargetChild,
                                       View target,
                                       int nestedScrollAxes) {

        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll
                (coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               FloatingActionButton child,
                               View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        if (dyConsumed > 0 && !isAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }


    }

    private void animateIn(FloatingActionButton fab) {

        fab.setVisibility(View.VISIBLE);
        ViewCompat.animate(fab)
                .translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withLayer()
                .setListener(null)
                .start();
    }

    private void animateOut(FloatingActionButton fab) {

        ViewCompat.animate(fab)
                .translationY(fab.getHeight() + getMarginBottom(fab))
                .setInterpolator(new FastOutLinearInInterpolator())
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isAnimatingOut = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isAnimatingOut = false;
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isAnimatingOut = false;

                    }
                }).start();
    }

    private int getMarginBottom(View v) {

        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }
}
