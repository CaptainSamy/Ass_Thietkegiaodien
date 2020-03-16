package com.example.ass_thietkegiaodien.myFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanThu;
import com.example.ass_thietkegiaodien.thuoctinh.Management_loaiThu;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.khoanThuDAO;
import com.example.ass_thietkegiaodien.mySQL.loaiThuDAO;

import java.util.ArrayList;

public class ThongKeFragmentHaiFragment extends Fragment {

    TextView tv_TongThu, tv_tongLoaiThu;
    ArrayList<Management_khoanThu> listKhoanThu;
    com.example.ass_thietkegiaodien.mySQL.khoanThuDAO khoanThuDAO;
    ArrayList<Management_loaiThu> listLoaiThu;

    public ThongKeFragmentHaiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_fragment_hai, null);
        return view;
    }

}
