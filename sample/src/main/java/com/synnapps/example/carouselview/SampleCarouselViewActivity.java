package com.synnapps.example.carouselview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.synnapps.carouselview.CarouselFragment;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageFragment;
import java.util.ArrayList;
import java.util.List;

public class SampleCarouselViewActivity extends AppCompatActivity {
    CarouselView customCarouselView;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_carousel_view);

        customCarouselView = (CarouselView) findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(sampleImages.length, getSupportFragmentManager(), makeImages());
        customCarouselView.setPageMargin(-400);
    }

    public List<CarouselFragment> makeImages() {
        List<CarouselFragment> cheeseList = new ArrayList<>();

        for (int i = 0; i < sampleImages.length; i++) {
            CarouselFragment fragment = ImageFragment.newInstance(i, CarouselView.BIG_SCALE, 400, sampleImages[i]);
            cheeseList.add(fragment);
        }

        return cheeseList;
    }
}
