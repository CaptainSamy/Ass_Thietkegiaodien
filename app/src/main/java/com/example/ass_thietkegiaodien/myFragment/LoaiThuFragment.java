package com.example.ass_thietkegiaodien.myFragment;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.ass_thietkegiaodien.thuoctinh.Management_loaiThu;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.loaiThuDAO;
import com.example.ass_thietkegiaodien.myAdapter.AdapterLoaiThu;

import java.util.ArrayList;

public class LoaiThuFragment extends Fragment {

    FloatingActionButton addLoaiThu;
    EditText et_AddLoaiThu;
    RecyclerView recyclerViewLoaiThu;
    ArrayList<Management_loaiThu> listLoaiThu = new ArrayList<Management_loaiThu>();
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    AdapterLoaiThu adapter;
    View view;
    com.example.ass_thietkegiaodien.mySQL.loaiThuDAO loaiThuDAO;
    Management_loaiThu loaiThu;

    public LoaiThuFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loai_thu, null);
        addData();
        capnhatGiaodienLoaiThu();
        return view;
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
                    adapter.getFilter().filter(newText);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }



    public void addData() {
        addLoaiThu = view.findViewById(R.id.addLoaiThu);
        addLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inf = LoaiThuFragment.this.getLayoutInflater();
                View view1 = inf.inflate(R.layout.dialog_one, null);
                et_AddLoaiThu = view1.findViewById(R.id.et_AddLoaiThu);
                alertDialog.setView(view1);
                alertDialog.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenLoaiThu = et_AddLoaiThu.getText().toString();
                        loaiThu = new Management_loaiThu(tenLoaiThu);
                        loaiThuDAO = new loaiThuDAO(getContext());
                        loaiThuDAO.AddLoaiThu(loaiThu);
                        //SQLite
                        capnhatGiaodienLoaiThu();
                        Toast.makeText(getContext(), "Đã thêm!", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void capnhatGiaodienLoaiThu() {
        recyclerViewLoaiThu = view.findViewById(R.id.recyclerView);
        loaiThuDAO = new loaiThuDAO(view.getContext());
        listLoaiThu = loaiThuDAO.ViewLoaiThu();
        recyclerViewLoaiThu.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new AdapterLoaiThu(listLoaiThu, getContext(), this);
        recyclerViewLoaiThu.setLayoutManager(linearLayoutManager);
        recyclerViewLoaiThu.setAdapter(adapter);
    }
}
