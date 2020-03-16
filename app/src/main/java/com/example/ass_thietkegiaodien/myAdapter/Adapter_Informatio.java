package com.example.ass_thietkegiaodien.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ass_thietkegiaodien.R;
import com.example.ass_thietkegiaodien.thuoctinh.information;

import java.util.ArrayList;

public class Adapter_Informatio extends RecyclerView.Adapter<Adapter_Informatio.MyViewHolder> {
    ArrayList<information> ds=new ArrayList<information>();
    Context context;

    public Adapter_Informatio(ArrayList<information> ds, Context context) {
        this.ds = ds;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.one_item_information, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        information information=ds.get(position);
        holder.tv_title.setText(information.title);
        holder.tv_content.setText(information.content);
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_content ;

        public MyViewHolder(View view) {
            super(view);
            this.tv_title =view.findViewById(R.id.tv_title);
            this.tv_content=view.findViewById(R.id.tv_content);
        }
    }

}

