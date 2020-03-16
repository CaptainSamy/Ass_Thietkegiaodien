package com.example.ass_thietkegiaodien.myFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanChi;
import com.example.ass_thietkegiaodien.thuoctinh.Mangement_loaiChi;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.khoanChiDAO;
import com.example.ass_thietkegiaodien.mySQL.loaiChiDAO;

import java.util.ArrayList;

public class ThongKeFragmentBaFragment extends Fragment {

    TextView tv_TongChi, tv_TongLoaiChi;
    ArrayList<Management_khoanChi> listKhoanChi;
    com.example.ass_thietkegiaodien.mySQL.khoanChiDAO khoanChiDAO;
    ArrayList<Mangement_loaiChi> listLoaiChi;

    public ThongKeFragmentBaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_thong_ke_fragment_ba,null);
        return  view;
    }

}
