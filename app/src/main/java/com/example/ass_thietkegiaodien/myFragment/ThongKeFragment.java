package com.example.ass_thietkegiaodien.myFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanChi;
import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanThu;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.khoanChiDAO;
import com.example.ass_thietkegiaodien.mySQL.khoanThuDAO;

import java.util.ArrayList;

public class ThongKeFragment extends Fragment {

    TextView tv_TongThu, tv_TongChi;
    ArrayList<Management_khoanThu> listKhoanThu;
    com.example.ass_thietkegiaodien.mySQL.khoanThuDAO khoanThuDAO;
    ArrayList<Management_khoanChi> listKhoanChi;
    com.example.ass_thietkegiaodien.mySQL.khoanChiDAO khoanChiDAO;

    public ThongKeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thong_ke, null);
        return view;
    }

}
