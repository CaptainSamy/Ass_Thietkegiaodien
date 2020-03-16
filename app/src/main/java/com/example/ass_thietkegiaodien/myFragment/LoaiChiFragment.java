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

import com.example.ass_thietkegiaodien.thuoctinh.Mangement_loaiChi;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.loaiChiDAO;
import com.example.ass_thietkegiaodien.myAdapter.AdapterLoaiChi;

import java.util.ArrayList;

public class LoaiChiFragment extends Fragment {
    FloatingActionButton addLoaiChi;
    EditText et_AddLoaiChi;
    RecyclerView recyclerViewLoaiChi;
    ArrayList<Mangement_loaiChi> listLoaiChi = new ArrayList<Mangement_loaiChi>();
    com.example.ass_thietkegiaodien.mySQL.loaiChiDAO loaiChiDAO;
    Mangement_loaiChi loaiChi;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    View view;
    AdapterLoaiChi adapter;
    public LoaiChiFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loai_chi, null);
        addData();
        capnhatGiaodien();
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
        addLoaiChi = view.findViewById(R.id.addLoaiChi);
        addLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inf = LoaiChiFragment.this.getLayoutInflater();
                View view1 = inf.inflate(R.layout.dialog_loaichi, null);
                et_AddLoaiChi = view1.findViewById(R.id.et_AddLoaiChi);
                alertDialog.setView(view1);
                alertDialog.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenLoaiChi = et_AddLoaiChi.getText().toString();
                        loaiChi = new Mangement_loaiChi(tenLoaiChi);
                        loaiChiDAO = new loaiChiDAO(getContext());
                        loaiChiDAO.AddLoaiChi(loaiChi);
                        capnhatGiaodien();
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

    public void capnhatGiaodien() {
        recyclerViewLoaiChi = view.findViewById(R.id.recyclerViewLoaiChi);
        loaiChiDAO = new loaiChiDAO(view.getContext());
        listLoaiChi = loaiChiDAO.ViewLoaiChi();
        recyclerViewLoaiChi.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new AdapterLoaiChi(listLoaiChi, getContext(), this);
        recyclerViewLoaiChi.setLayoutManager(linearLayoutManager);
        recyclerViewLoaiChi.setAdapter(adapter);
    }
}
