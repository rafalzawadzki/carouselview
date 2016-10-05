package com.synnapps.carouselview;

import android.os.Bundle;

public class LayoutFragment extends CarouselFragment {

    public static com.synnapps.carouselview.LayoutFragment newInstance(int layout, int pos, float scale) {
        com.synnapps.carouselview.LayoutFragment fragment = new com.synnapps.carouselview.LayoutFragment();
        Bundle b = new Bundle();
        b.putInt(POSITION, pos);
        b.putFloat(SCALE, scale);
        fragment.setArguments(b);

        return fragment;
    }


}
