//package com.web.instafx.promotional_page;
//
//import android.os.Bundle;
//import android.os.Handler;
//
//import androidx.viewpager.widget.ViewPager;
//
//import com.web.instafx.BaseActivity;
//import com.web.instafx.R;
//import com.web.instafx.utilpackage.SlidingImage_Adapter;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import me.relex.circleindicator.CircleIndicator;
//
//public class PromotionalScreen extends BaseActivity {
//    private static ViewPager mPager;
//    private static ViewPager bottomPager;
//    private static int currentPage = 0;
//    private static int NUM_PAGES = 0;
//    private static final String[] IMAGES= {"https://via.placeholder.com/300/09f/fff.png"};
//    private ArrayList<String> ImagesArray = new ArrayList<String>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.promotinal_screen);
//        promotionalTopImage();
//        promotionalBottomImage();
//    }
//    private void promotionalTopImage(){
//        for(int i=0;i<IMAGES.length;i++)
//            ImagesArray.add(IMAGES[i]);
//
//        mPager = findViewById(R.id.banner_pager);
//        mPager.setAdapter(new SlidingImage_Adapter(PromotionalScreen.this,ImagesArray));
//        CircleIndicator indicator = findViewById(R.id.indicator);
//        indicator.setViewPager(mPager);
//        NUM_PAGES =IMAGES.length;
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);
//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//
//            }
//
//            @Override
//            public void onPageScrolled(int pos, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int pos) {
//
//            }
//        });
//
//
//    }
//    private void promotionalBottomImage(){
//        for(int i=0;i<IMAGES.length;i++)
//            ImagesArray.add(IMAGES[i]);
//
//        bottomPager = findViewById(R.id.bottom_banner_pager);
//        bottomPager.setAdapter(new SlidingImage_Adapter(PromotionalScreen.this,ImagesArray));
//        CircleIndicator indicator = findViewById(R.id.bottom_indicator);
//        indicator.setViewPager(bottomPager);
//        NUM_PAGES =IMAGES.length;
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                bottomPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);
//        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPage = position;
//
//            }
//
//            @Override
//            public void onPageScrolled(int pos, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int pos) {
//
//            }
//        });
//
//
//    }
//
//}
