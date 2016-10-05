package com.synnapps.carouselview;

import android.support.v4.app.Fragment;

public class CarouselFragment extends Fragment {
    protected static final String POSITION = "position";
    protected static final String SCALE = "scale";
    protected static final String MARGIN = "margin";

    protected int position = 0;

    public void setPosition(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }
}
