package com.example.ass_thietkegiaodien.myFragment;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanChi;
import com.example.ass_thietkegiaodien.thuoctinh.Mangement_loaiChi;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.khoanChiDAO;
import com.example.ass_thietkegiaodien.mySQL.loaiChiDAO;
import com.example.ass_thietkegiaodien.myAdapter.Adapter_KhoanChi;
import com.example.ass_thietkegiaodien.myAdapter.Adapter_spinnerLoaiChi;

import java.util.ArrayList;

public class KhoanChiFragment extends Fragment {
    FloatingActionButton addKhoanChi;
    public EditText et_KhoanChi, et_NoidungKhoanChi;
    Spinner sp_LoaiChi;
    com.example.ass_thietkegiaodien.mySQL.loaiChiDAO loaiChiDAO;
    CalendarView calendarView2;
    String date1;
    ArrayList<Mangement_loaiChi> listLoaiChi = new ArrayList<Mangement_loaiChi>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerViewKhoanChi;
    ArrayList<Management_khoanChi> listKhoanChi = new ArrayList<Management_khoanChi>();
    View view;
    Adapter_KhoanChi adapter_khoanChi;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    com.example.ass_thietkegiaodien.mySQL.khoanChiDAO khoanChiDAO;
    Management_khoanChi khoanChi;
    public KhoanChiFragment() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_for_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    adapter_khoanChi.getFilter().filter(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_khoan_chi, null);
        addKhoanChi = view.findViewById(R.id.addKhoanChi);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CapNhatGiaoDien();
                        Toast.makeText(getContext(), " Đã cập nhật! ", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        recyclerViewKhoanChi = view.findViewById(R.id.recyclerViewKhoanChi);
        khoanChiDAO = new khoanChiDAO(getContext());
        CapNhatGiaoDien();
        addKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDial = new AlertDialog.Builder(view.getContext());
                LayoutInflater inf = KhoanChiFragment.this.getLayoutInflater();
                View view1 = inf.inflate(R.layout.dialog_khoanchi, null);
                et_KhoanChi = view1.findViewById(R.id.et_KhoanChi);
                et_NoidungKhoanChi = view1.findViewById(R.id.et_NoidungKhoanChi);
                calendarView2 = view1.findViewById(R.id.calendarView2);
                calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date1 = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                });
                sp_LoaiChi = view1.findViewById(R.id.sp_LoaiChi);
                loaiChiDAO = new loaiChiDAO(view1.getContext());
                CapNhatGiaoDienKhoanSpinnerKhoanChi();
                alertDial.setView(view1);
                alertDial.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenKhoanChi = et_KhoanChi.getText().toString();
                        String noiDung = et_NoidungKhoanChi.getText().toString();
                        String getDate = date1;
                        int _idLoaiChi = 0;
                        try {

                            int index = sp_LoaiChi.getSelectedItemPosition();
                            Mangement_loaiChi loaiChi = listLoaiChi.get(index);
                            _idLoaiChi = loaiChi._id;

                        } catch (Exception e) {

                        }
                        Management_khoanChi khoanChi = new Management_khoanChi(tenKhoanChi, noiDung, getDate, _idLoaiChi);
                        khoanChiDAO khoanChiDAO = new khoanChiDAO(getContext());
                        khoanChiDAO.addKhoanChi(khoanChi);
                        CapNhatGiaoDien();
                        Toast.makeText(getContext(), "Đã thêm!", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDial.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDial.show();

            }
        });
        return view;
    }

    public void CapNhatGiaoDien() {
        listKhoanChi = khoanChiDAO.ViewKhoanChi();
        adapter_khoanChi = new Adapter_KhoanChi(listKhoanChi, listLoaiChi, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewKhoanChi.setLayoutManager(linearLayoutManager);
        recyclerViewKhoanChi.setAdapter(adapter_khoanChi);

    }

    public void CapNhatGiaoDienKhoanSpinnerKhoanChi() {
        listLoaiChi = loaiChiDAO.ViewLoaiChi();
        Adapter_spinnerLoaiChi spinnerLoaiChi = new Adapter_spinnerLoaiChi(getContext(), listLoaiChi);
        sp_LoaiChi.setAdapter(spinnerLoaiChi);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
