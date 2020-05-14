package com.example.todo_application;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class HomeScreenSlidePagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 3;

    private ViewPager main_pager;
    private PagerAdapter pager_adapter;

    private LinearLayout active_dot_holder;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_pager_holder);
        int starting_position = 1;

        main_pager = findViewById(R.id.main_pager);
        pager_adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        main_pager.setAdapter(pager_adapter);
        main_pager.setCurrentItem(starting_position);

        active_dot_holder = findViewById(R.id.active_dot_holder);
        createDots(starting_position);
        main_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createDots(int currentPosition) {
        if(active_dot_holder != null)
            active_dot_holder.removeAllViews();
        dots = new ImageView[NUM_PAGES];
        for(int i = 0; i < NUM_PAGES; i++) {
            dots[i] = new ImageView(this);
            if(i==currentPosition)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dot));
            else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dot));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            active_dot_holder.addView(dots[i], params);
        }
    }


    @Override
    public void onBackPressed() {
        if(main_pager.getCurrentItem() == 1) {
            super.onBackPressed();
        } else if(main_pager.getCurrentItem() == 0) {
            main_pager.setCurrentItem(main_pager.getCurrentItem()+1);
        } else if(main_pager.getCurrentItem() == 2) {
            main_pager.setCurrentItem(main_pager.getCurrentItem()-1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fr;
            switch(position) {
                case(1):
                    fr = new TaskRecyclerFragment();
                    break;
                case(2):
                    fr = new ProfileSliderFragment();
                    break;
                default:
                    fr =  new NewTaskFragment();
            }
            return fr;
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
