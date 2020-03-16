package com.example.ass_thietkegiaodien.myFragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ass_thietkegiaodien.myViewpager.ChiViewPager;
import com.example.ass_thietkegiaodien.R;

public class ChiFragment extends Fragment {
    TabLayout tabLayoutChi;
    ViewPager pagerChi;

    public ChiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi, null);
        pagerChi=view.findViewById(R.id.pagerChi);
        tabLayoutChi=view.findViewById(R.id.tabLayoutChi);
        tabLayoutChi.addTab(tabLayoutChi.newTab().setText("Loại chi"));
        tabLayoutChi.addTab(tabLayoutChi.newTab().setText("Khoản chi"));
        pagerChi.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutChi));
        tabLayoutChi.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerChi.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ChiViewPager chiViewPager=new ChiViewPager(getActivity().getSupportFragmentManager());
        pagerChi.setAdapter(chiViewPager);


        return view;
    }

}
