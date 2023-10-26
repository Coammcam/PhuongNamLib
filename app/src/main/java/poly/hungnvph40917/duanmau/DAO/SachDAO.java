package poly.hungnvph40917.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import poly.hungnvph40917.duanmau.database.DbHelper;
import poly.hungnvph40917.duanmau.model.Sach;

public class SachDAO {
    DbHelper dbHelper;
     public SachDAO(Context context) {
         dbHelper = new DbHelper(context);
     }

     // lay toan bo dau sach trong thu vien
    public ArrayList<Sach> getDsDauSach() {
         ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sc.maSach, sc.tenSach, sc.giaThue, sc.maLoai, lo.tenLoai FROM SACH sc, LOAISACH lo WHERE sc.maLoai = lo.maLoai", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4)));
            } while (cursor.moveToNext());
        }

         return list;
    }

    public boolean themSachMoi(String tenSach, int giaTien, int maLoai) {
         SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tenSach);
        contentValues.put("giathue", giaTien);
        contentValues.put("maloai", maLoai);

        long check = sqLiteDatabase.insert("SACH", null, contentValues);

        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean capNhatThongTinSach(int masach, String tensach, int giathue, int maloai) {
         SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
         contentValues.put("tensach", tensach);
         contentValues.put("giathue", giathue);
         contentValues.put("maloai", maloai);

         long check = sqLiteDatabase.update("SACH", contentValues, "masach = ?", new String[]{String.valueOf(masach)});
         if (check == -1) {
             return false;
         } return true;
    }

    // xoa thanh cong, xoa that bai, khong duoc phep xoa khi co trong phieu muon
    public int xoaSach(int masach) {
         SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
         Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ?", new String[]{String.valueOf(masach)});
         if (cursor.getCount() != 0) {
             return -1;
         }

         long check = sqLiteDatabase.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
         if (check == -1) {
             return 0;
         } return 1;
    }
}
