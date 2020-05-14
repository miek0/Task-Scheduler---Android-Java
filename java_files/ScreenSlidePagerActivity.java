package com.example.todo_application;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ScreenSlidePagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 2;

    private ViewPager main_pager;

    private PagerAdapter pager_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        main_pager = findViewById(R.id.main_pager);
        pager_adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        main_pager.setAdapter(pager_adapter);
    }

    @Override
    public void onBackPressed() {
        if(main_pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            main_pager.setCurrentItem(main_pager.getCurrentItem()-1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fr =  new TaskRecyclerFragment();
            if (position == 1)
                fr = new ProfileSliderFragment();


            return fr;
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
