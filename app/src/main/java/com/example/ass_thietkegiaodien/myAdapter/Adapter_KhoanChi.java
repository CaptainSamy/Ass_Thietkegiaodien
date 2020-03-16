package com.example.ass_thietkegiaodien.myAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ass_thietkegiaodien.myFragment.KhoanChiFragment;
import com.example.ass_thietkegiaodien.mySQL.khoanThuDAO;
import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanChi;
import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanThu;
import com.example.ass_thietkegiaodien.thuoctinh.Mangement_loaiChi;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.khoanChiDAO;
import com.example.ass_thietkegiaodien.mySQL.loaiChiDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class Adapter_KhoanChi extends RecyclerView.Adapter<Adapter_KhoanChi.MyViewHolder> implements Filterable {


    ArrayList<Management_khoanChi> listKhoanChi = new ArrayList<Management_khoanChi>();
    ArrayList<Mangement_loaiChi> listLoaiChi = new ArrayList<Mangement_loaiChi>();
    ArrayList<Management_khoanChi> listSearch;
    public Context context;
    KhoanChiFragment khoanChiFragment;


    String date;
    Spinner sp_EditLoaiChi;
    com.example.ass_thietkegiaodien.mySQL.loaiChiDAO loaiChiDAO;

    public Adapter_KhoanChi(ArrayList<Management_khoanChi> listKhoanChi, ArrayList<Mangement_loaiChi> listLoaiChi, Context context, KhoanChiFragment khoanChiFragment) {
        this.listKhoanChi = listKhoanChi;
        this.listLoaiChi = listLoaiChi;
        this.context = context;
        this.khoanChiFragment = khoanChiFragment;
        listSearch=new ArrayList<>(listKhoanChi);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.one_item_for_khoan_chi, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Management_khoanChi khoanChi = listKhoanChi.get(position);
        String tenLoaiChi = "";
        loaiChiDAO = new loaiChiDAO(khoanChiFragment.getContext());
        listLoaiChi = loaiChiDAO.ViewLoaiChi();
        for (int i = 0; i < listLoaiChi.size(); i++) {
            if (listLoaiChi.get(i)._id == khoanChi._idLoaiChi) {
                tenLoaiChi = listLoaiChi.get(i).tenLoaiChi;
            }
        }
        holder.tv_LoaiChi.setText(tenLoaiChi);


        holder.iv_DeleteKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(khoanChiFragment.getContext());
                builder.setMessage("Dữ liệu sẽ không thể phục hồi!");
                builder.setTitle("Bạn có chắc muốn xóa!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Management_khoanChi khoanChi1 = listKhoanChi.get(position);
                        int _id = khoanChi1._id;
                        khoanChiDAO khoanChiDAO = new khoanChiDAO(khoanChiFragment.getContext());
                        khoanChiDAO.DeleteKhoanChi(_id);
                        khoanChiFragment.CapNhatGiaoDien();
                        Toast.makeText(khoanChiFragment.getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


        holder.iv_editKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(khoanChiFragment.getContext());
                LayoutInflater inf = khoanChiFragment.getActivity().getLayoutInflater();
                View view1 = inf.inflate(R.layout.dialog_for_edit_khoan_chi, null);
                final Management_khoanChi khoanChi = listKhoanChi.get(position);
                final EditText et_EditKhoanChi = view1.findViewById(R.id.et_EditKhoanChi);
                final EditText et_EditNoidungKhoanChi = view1.findViewById(R.id.et_EditNoidungKhoanChi);
                sp_EditLoaiChi = view1.findViewById(R.id.sp_EditLoaiChi);
                loaiChiDAO = new loaiChiDAO(view1.getContext());
                CapNhatGiaoDienKhoanSpinnerKhoanChi();

                CalendarView EditcalendarView = view1.findViewById(R.id.EditcalendarView2);
                EditcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                });
                try {
                    String getDate = khoanChi.ngayChi;
                    String MangDate[] = getDate.split("/");
                    int day = Integer.parseInt(MangDate[0]);
                    int month = Integer.parseInt(MangDate[1]);
                    int year = Integer.parseInt(MangDate[2]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, (month - 1));
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    long milliTime = calendar.getTimeInMillis();
                    EditcalendarView.setDate(milliTime, true, true);
                } catch (Exception e) {

                }
                int IndexSpinner = 0;
                for (int i = 0; i <= listLoaiChi.size() - 1; i++) {
                    if (khoanChi._idLoaiChi == listLoaiChi.get(i)._id) {
                        IndexSpinner = i;
                    }
                }
                sp_EditLoaiChi.setSelection(IndexSpinner);
                et_EditKhoanChi.setText(khoanChi.tenKhoanChi);
                et_EditNoidungKhoanChi.setText(khoanChi.noiDung);
                alertDialog.setView(view1);
                alertDialog.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenKhoanChi = et_EditKhoanChi.getText().toString();
                        String noiDung = et_EditNoidungKhoanChi.getText().toString();
                        int _idLoaiChi = 0;
                        try {

                            int index = sp_EditLoaiChi.getSelectedItemPosition();
                            Mangement_loaiChi loaiChi = listLoaiChi.get(index);
                            _idLoaiChi = loaiChi._id;
                        } catch (Exception e) {

                        }
                        String getDate = date;
                        try {
                            if (getDate == null) {
                                getDate = khoanChi.ngayChi;
                            }
                        } catch (Exception e) {

                        }
                        Management_khoanChi khoanChi = listKhoanChi.get(position);
                        Management_khoanChi khoanChi1 = new Management_khoanChi(khoanChi._id, tenKhoanChi, noiDung, getDate, _idLoaiChi);
                        khoanChiDAO chiDAO = new khoanChiDAO(khoanChiFragment.getContext());
                        chiDAO.UpdateKhoanChi(khoanChi1);
                        khoanChiFragment.CapNhatGiaoDien();
                        Toast.makeText(khoanChiFragment.getContext(), "Đã cập nhật!", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(khoanChiFragment.getContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return listKhoanChi.size();
    }
    @Override
    public Filter getFilter() {
        return filterList;
    }
    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Management_khoanChi> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listSearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Management_khoanChi item : listSearch) {
                    if (item.tenKhoanChi.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listKhoanChi.clear();
            listKhoanChi.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tenKhoanChi, tv_SoTienChi, tv_LoaiChi, tv_ngayChi;
        public ImageView iv_editKhoanChi, iv_DeleteKhoanChi;

        public MyViewHolder(View view) {
            super(view);
            this.tv_tenKhoanChi = view.findViewById(R.id.tv_tenKhoanChi);
            this.tv_SoTienChi = view.findViewById(R.id.tv_SoTienChi);
            this.tv_LoaiChi = view.findViewById(R.id.tv_LoaiChi);
            this.tv_ngayChi = view.findViewById(R.id.tv_ngaychi);

            this.iv_DeleteKhoanChi = view.findViewById(R.id.iv_DeleteKhoanChi);
            this.iv_editKhoanChi = view.findViewById(R.id.iv_editKhoanChi);

        }
    }

    public void CapNhatGiaoDienKhoanSpinnerKhoanChi() {
        listLoaiChi = loaiChiDAO.ViewLoaiChi();
        Adapter_spinnerLoaiChi spinnerLoaiChi = new Adapter_spinnerLoaiChi(khoanChiFragment.getContext(), listLoaiChi);
        sp_EditLoaiChi.setAdapter(spinnerLoaiChi);
    }

}

