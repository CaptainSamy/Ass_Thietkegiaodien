package com.example.ass_thietkegiaodien.myViewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ass_thietkegiaodien.myFragment.ThongKeFragment;
import com.example.ass_thietkegiaodien.myFragment.ThongKeFragmentBaFragment;
import com.example.ass_thietkegiaodien.myFragment.ThongKeFragmentHaiFragment;

public class ThongKeTabPager extends FragmentStatePagerAdapter {
    public ThongKeTabPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment=new ThongKeFragment();
                break;
            case 1:
                fragment=new ThongKeFragmentHaiFragment();
                break;
            case 2:
                fragment=new ThongKeFragmentBaFragment();
                break;
            default:
                return null;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
