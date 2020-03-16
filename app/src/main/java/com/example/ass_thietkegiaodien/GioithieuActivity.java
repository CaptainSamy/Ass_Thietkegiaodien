package com.example.ass_thietkegiaodien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GioithieuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioithieu);

        ListView listView = findViewById(R.id.listviewGioithieu);
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Tên ứng dụng : Nhạc_Fistly");
        stringArrayList.add("Phiên bản: 1.0.0");
        stringArrayList.add("Nhóm viết: NHÓM 1");
        stringArrayList.add("Năm: 2020");
        stringArrayList.add("Nghành học: MOB");
        stringArrayList.add("Khóa học: 14251");
        stringArrayList.add("Môn học: Quản lý dự án với phần mềm Agile");
        stringArrayList.add("Trường: FPT PolyHN");

        final ArrayAdapter adapter = new ArrayAdapter(GioithieuActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        listView.setAdapter(adapter);
    }
}
