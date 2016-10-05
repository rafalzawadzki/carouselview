package com.synnapps.carouselview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageFragment extends CarouselFragment {
    private static final String IMAGE_RESOURCE = "image_resource";

    protected int screenWidth;
    protected int screenHeight;

    public static com.synnapps.carouselview.ImageFragment newInstance(int pos, float scale, int margin, int image) {
        com.synnapps.carouselview.ImageFragment fragment = new com.synnapps.carouselview.ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, pos);
        bundle.putFloat(SCALE, scale);
        bundle.putInt(MARGIN, margin);
        bundle.putInt(IMAGE_RESOURCE, image);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWidthAndHeight();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        position = this.getArguments().getInt(POSITION);
        float scale = this.getArguments().getFloat(SCALE);
        int margin = this.getArguments().getInt(MARGIN);
        int imgResource = this.getArguments().getInt(IMAGE_RESOURCE);
        setPosition(position);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth - margin, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.image_fragment, container, false);

        CarouselLinearLayout root = (CarouselLinearLayout) linearLayout.findViewById(R.id.root_container);
        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.image);

        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imgResource);

        root.setScaleBoth(scale);

        return linearLayout;
    }

    private void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
}
