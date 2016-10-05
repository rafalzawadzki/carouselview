package com.synnapps.carouselview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Sayyam on 3/28/16.
 */
public class CarouselViewPager extends ViewPager {

    private OnPageChangeListener externalOnPageChangeListener = null;
    private PageChangeListener actualPageChangeListener = null;

    public CarouselViewPager(Context context) {
        super(context);
        postInitViewPager();
        setActualOnPageChangeListener(new PageChangeListener(this));
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
        setActualOnPageChangeListener(new PageChangeListener(this));
    }

    private CarouselViewPagerScroller mScroller = null;

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new CarouselViewPagerScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    // move the last item to the front of the list so we don't start with empty space on the left
    private void initPagerFragments() {
        CarouselView.CarouselPagerAdapter adapter = (CarouselView.CarouselPagerAdapter) getAdapter();
        List<CarouselFragment> pagerFragments = adapter.getPagerFragments();
        final int lastPosition = pagerFragments.size() - 1;
        pagerFragments.add(0, pagerFragments.remove(lastPosition));
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setTransitionVelocity(int scrollFactor) {
        mScroller.setmScrollDuration(scrollFactor);
    }

    private void setActualOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener(listener);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.externalOnPageChangeListener = listener;
    }

    private OnPageChangeListener getExternalOnPageChangeListener() {
        return this.externalOnPageChangeListener;
    }

    private static class PageChangeListener implements OnPageChangeListener {
        private final CarouselViewPager viewPager;
        private int currentPosition = 0;

        public PageChangeListener(final CarouselViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            final OnPageChangeListener externalOnPageChangeListener =
                    viewPager.getExternalOnPageChangeListener();
            if (externalOnPageChangeListener != null) {
                externalOnPageChangeListener.onPageScrolled(
                        currentPosition, positionOffset, positionOffsetPixels-400);
            }
        }

        @Override
        public void onPageSelected(int position) {
            final OnPageChangeListener externalOnPageChangeListener =
                    viewPager.getExternalOnPageChangeListener();
            CarouselView.CarouselPagerAdapter adapter = (CarouselView.CarouselPagerAdapter) viewPager.getAdapter();
            List<CarouselFragment> pagerFragments = adapter.getPagerFragments();
            currentPosition = pagerFragments.get(viewPager.getCurrentItem()).getPosition();
            if (externalOnPageChangeListener != null) {
                externalOnPageChangeListener.onPageSelected(currentPosition);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            final OnPageChangeListener externalOnPageChangeListener =
                    viewPager.getExternalOnPageChangeListener();

            CarouselView.CarouselPagerAdapter adapter = (CarouselView.CarouselPagerAdapter) viewPager.getAdapter();
            List<CarouselFragment> pagerFragments = adapter.getPagerFragments();
            if (state == SCROLL_STATE_IDLE) {
                currentPosition = pagerFragments.get(viewPager.getCurrentItem()).getPosition();
                // Ensure that cycling only occurs if there are 3 or more fragments.
                if (pagerFragments.size() > 3) {
                    final int cycleResult = cyclePagerFragments(pagerFragments, viewPager.getCurrentItem());
                    if (cycleResult != 0) {
                        adapter.setPagerFragments(pagerFragments);
                        adapter.notifyDataSetChanged();

                        // Turn off the actual and external OnPageChangeListeners, so that
                        // this function does not unnecessarily get called again when
                        // setting the current item.
                        viewPager.setOnPageChangeListener(null);
                        viewPager.setActualOnPageChangeListener(null);
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + cycleResult, false);
                        viewPager.setOnPageChangeListener(externalOnPageChangeListener);
                        viewPager.setActualOnPageChangeListener(this);
                    }
                }
            }

            if (externalOnPageChangeListener != null) {
                externalOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }

    /**
     * Cycles through the fragments in the given pagerFragments list,
     * depending on the current position that is passed in.
     *
     * @param pagerFragments A {@link List} containing
     *                      {@link android.support.v4.app.Fragment} to be cycled.
     * @param position       The position of the currently selected item in the pager fragments.
     * @return -1 if the viewpager items were cycled to the right,
     * 1 if the viewpager items were cycled to the left,
     * 0 if the viewpager items were not cycled.
     */
    private static int cyclePagerFragments(List<CarouselFragment> pagerFragments, final int position) {
        final int lastPosition = pagerFragments.size() - 1;
        if (position == lastPosition-1) {
            pagerFragments.add(pagerFragments.remove(0));
            return -1;
        } else if (position == 1) {
            pagerFragments.add(0, pagerFragments.remove(lastPosition));
            return 1;
        }

        return 0;
    }
}