package com.example.ass_thietkegiaodien.myViewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ass_thietkegiaodien.myFragment.KhoanThuFragment;
import com.example.ass_thietkegiaodien.myFragment.LoaiThuFragment;

public class ThuViewPager extends FragmentStatePagerAdapter {


    public ThuViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new LoaiThuFragment();
                break;
            case 1:
                fragment = new KhoanThuFragment();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
