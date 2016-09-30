package com.synnapps.carouselview;

import android.support.v4.app.Fragment;

/**
 * CarouselFragment
 * TODO: add class header comment
 */
public class CarouselFragment extends Fragment {
    private int position = 0;

    public void setPosition(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }
}
