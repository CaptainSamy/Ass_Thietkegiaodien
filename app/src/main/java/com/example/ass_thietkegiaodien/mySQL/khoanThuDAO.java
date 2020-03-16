package com.example.ass_thietkegiaodien.mySQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ass_thietkegiaodien.thuoctinh.Management_khoanThu;

import java.util.ArrayList;

public class khoanThuDAO {
    SQLiteDatabase db;
    Dbhelper dbh;

    public khoanThuDAO(Context c) {
        dbh = new Dbhelper(c);
    }

    public void addKhoanThu(Management_khoanThu khoanThu) {
        db = dbh.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhoanThu", khoanThu.tenKhoanThu);
        values.put("noiDung", khoanThu.noiDung);
        values.put("ngayThu", khoanThu.ngayThu);
        values.put("_idLoaiThu", khoanThu._idLoaiThu);
        db.insert("khoanThu", null, values);
    }

    public ArrayList<Management_khoanThu> ViewKhoanThu() {
        ArrayList<Management_khoanThu> listkhoanThu = new ArrayList<Management_khoanThu>();
        db=dbh.getReadableDatabase();
        Cursor c=db.query("khoanThu",null,null,null,null,null,null);
        if (c.moveToFirst()){
            do {
                int _id=c.getInt(0);
                String tenKhoanThu=c.getString(1);
                String noiDung=c.getString(2);
                String ngayThu=c.getString(3);
                int _idLoaiThu=c.getInt(4);
                Management_khoanThu khoanThu=new Management_khoanThu(_id,tenKhoanThu,noiDung,ngayThu,_idLoaiThu);
                listkhoanThu.add(khoanThu);
            }while (c.moveToNext());
        }

        return listkhoanThu;
    }


    public void DeleteKhoanThu(int _id){
        db=dbh.getWritableDatabase();
        db.delete("khoanThu","_id=?",new String[]{_id+""});
    }

    public void UpdateKhoanThu(Management_khoanThu khoanThu){
        db=dbh.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("tenKhoanThu",khoanThu.tenKhoanThu);
        values.put("noiDung",khoanThu.noiDung);
        values.put("ngayThu",khoanThu.ngayThu);
        values.put("_idLoaiThu",khoanThu._idLoaiThu);
        db.update("khoanThu",values,"_id=?",new String[]{khoanThu._id+""});
    }
}
