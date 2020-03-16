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

import com.example.ass_thietkegiaodien.myFragment.KhoanThuFragment;
import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanThu;
import com.example.ass_thietkegiaodien.thuoctinh.Management_loaiThu;
import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.mySQL.khoanThuDAO;
import com.example.ass_thietkegiaodien.mySQL.loaiThuDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class Adapter_KhoanThu extends RecyclerView.Adapter<Adapter_KhoanThu.MyViewHolder> implements Filterable {


    Context context;
    ArrayList<Management_khoanThu> listKhoanThu = new ArrayList<Management_khoanThu>();
    ArrayList<Management_loaiThu> listLoaiThu = new ArrayList<Management_loaiThu>();
    KhoanThuFragment khoanThuFragment;

    ArrayList<Management_khoanThu> listSearch;


    public Adapter_KhoanThu(Context context, ArrayList<Management_khoanThu> listKhoanThu, ArrayList<Management_loaiThu> listLoaiThu, KhoanThuFragment khoanThuFragment) {
        this.context = context;
        this.listKhoanThu = listKhoanThu;
        this.listLoaiThu = listLoaiThu;
        this.khoanThuFragment = khoanThuFragment;
        listSearch=new ArrayList<>(listKhoanThu);
    }

    String date;
    Spinner sp_EditLoaiThu;
    com.example.ass_thietkegiaodien.mySQL.loaiThuDAO loaiThuDAO;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.one_item_for_khoan_thu, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Management_khoanThu khoanThu = listKhoanThu.get(position);
        String tenLoaiThu = null;
        loaiThuDAO = new loaiThuDAO(khoanThuFragment.getContext());
        listLoaiThu = loaiThuDAO.ViewLoaiThu();
        for (Management_loaiThu loaiThu : listLoaiThu) {
            if (loaiThu._id == khoanThu._idLoaiThu) {
                tenLoaiThu = loaiThu.tenLoaiThu;

            }
        }
        holder.tv_LoaiThu.setText(tenLoaiThu);
        holder.iv_DeleteKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(khoanThuFragment.getContext());
                builder.setMessage("Dữ liệu sẽ không thể phục hồi!");
                builder.setTitle("Bạn có chắc muốn xóa!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Management_khoanThu khoanThu1 = listKhoanThu.get(position);
                        int _id = khoanThu1._id;
                        khoanThuDAO khoanThuDAO = new khoanThuDAO(khoanThuFragment.getContext());
                        khoanThuDAO.DeleteKhoanThu(_id);
                        khoanThuFragment.CapNhatGiaoDienKhoanThu();
                        Toast.makeText(khoanThuFragment.getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        holder.iv_editKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(khoanThuFragment.getContext());
                LayoutInflater inf = khoanThuFragment.getActivity().getLayoutInflater();
                View view1 = inf.inflate(R.layout.dialog_for_edit_khoan_thu, null);
                final Management_khoanThu khoanThu = listKhoanThu.get(position);
                final EditText et_EditKhoanThu = view1.findViewById(R.id.et_EditKhoanThu);
                final EditText et_EditNoidungKhoanThu = view1.findViewById(R.id.et_EditNoidungKhoanThu);
                sp_EditLoaiThu = view1.findViewById(R.id.sp_EditLoaiThu);
                loaiThuDAO = new loaiThuDAO(view1.getContext());
                CapNhatGiaoDienSpinnerKhoanThu();
                CalendarView EditcalendarView = view1.findViewById(R.id.EditcalendarView);
                EditcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                });
                EditcalendarView.setSelected(true);
                try {
                    String getDate = khoanThu.ngayThu;
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
                for (int i = 0; i <= listLoaiThu.size() - 1; i++) {
                    if (khoanThu._idLoaiThu == listLoaiThu.get(i)._id) {
                        IndexSpinner = i;
                    }
                }
                sp_EditLoaiThu.setSelection(IndexSpinner);
                et_EditKhoanThu.setText(khoanThu.tenKhoanThu);
                et_EditNoidungKhoanThu.setText(khoanThu.noiDung);
                alertDialog.setView(view1);
                alertDialog.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenKhoanThu = et_EditKhoanThu.getText().toString();
                        String noiDung = et_EditNoidungKhoanThu.getText().toString();
                        int _idLoaiThu = 0;
                        try {
                            int index = sp_EditLoaiThu.getSelectedItemPosition();
                            Management_loaiThu loaiThu = listLoaiThu.get(index);
                            _idLoaiThu = loaiThu._id;
                        } catch (Exception e) {

                        }
                        String getDate = date;
                        try {
                            if (getDate == null) {
                                getDate = khoanThu.ngayThu;
                            }

                        } catch (Exception e) {

                        }
                        Management_khoanThu khoanThu = listKhoanThu.get(position);
                        Management_khoanThu khoanThu1 = new Management_khoanThu(khoanThu._id, tenKhoanThu, noiDung, getDate, _idLoaiThu);
                        khoanThuDAO thuDAO = new khoanThuDAO(khoanThuFragment.getContext());
                        thuDAO.UpdateKhoanThu(khoanThu1);
                        khoanThuFragment.CapNhatGiaoDienKhoanThu();
                        Toast.makeText(khoanThuFragment.getContext(), "Đã cập nhật!", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(khoanThuFragment.getContext(), "Đã hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKhoanThu.size();
    }

    @Override
    public Filter getFilter() {
        return filterList;
    }
    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Management_khoanThu> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listSearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Management_khoanThu item : listSearch) {
                    if (item.tenKhoanThu.toLowerCase().contains(filterPattern)) {
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
            listKhoanThu.clear();
            listKhoanThu.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_tenKhoanThu, tv_SoTienThu, tv_LoaiThu, tv_ngayThu;
        public ImageView iv_editKhoanThu, iv_DeleteKhoanThu;

        public MyViewHolder(View view) {
            super(view);
            this.tv_tenKhoanThu = view.findViewById(R.id.tv_tenKhoanThu);
            this.tv_SoTienThu = view.findViewById(R.id.tv_SoTienThu);
            this.tv_LoaiThu = view.findViewById(R.id.tv_LoaiThu);
            this.tv_ngayThu = view.findViewById(R.id.tv_ngayThu);

            this.iv_DeleteKhoanThu = view.findViewById(R.id.iv_DeleteKhoanThu);
            this.iv_editKhoanThu = view.findViewById(R.id.iv_editKhoanThu);

        }
    }

    public void CapNhatGiaoDienSpinnerKhoanThu() {
        listLoaiThu = loaiThuDAO.ViewLoaiThu();
        Adapter_SpinnerLoaiThu spinnerLoaiThu = new Adapter_SpinnerLoaiThu(khoanThuFragment.getContext(), listLoaiThu);
        sp_EditLoaiThu.setAdapter(spinnerLoaiThu);
    }

}
