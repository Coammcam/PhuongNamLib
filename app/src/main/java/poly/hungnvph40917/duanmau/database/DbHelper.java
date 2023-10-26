package poly.hungnvph40917.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "DUANMAU", null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbThuThu = "CREATE TABLE THUTHU (matt TEXT PRIMARY KEY, hoTen TEXT NOT NULL, matkhau TEXT NOT NULL, loaitaikhoan TEXT)";
        db.execSQL(dbThuThu);

        String dbThanhVien = "CREATE TABLE THANHVIEN (matv INTEGER PRIMARY KEY AUTOINCREMENT, hoTen TEXT NOT NULL, namSinh TEXT NOT NULL)";
        db.execSQL(dbThanhVien);

        String dbLoaiSach = "CREATE TABLE LOAISACH (maLoai INTEGER PRIMARY KEY AUTOINCREMENT, tenLoai text NOT NULL)";
        db.execSQL(dbLoaiSach);

        String dbSach = "CREATE TABLE SACH (maSach INTEGER PRIMARY KEY AUTOINCREMENT, tenSach TEXT NOT NULL, giaThue INTEGER NOT NULL, maLoai INTEGER REFERENCES LOAISACH(maLoai) NOT NULL)";
        db.execSQL(dbSach);

        String dbPhieuMuon = "CREATE TABLE PHIEUMUON (mapm INTEGER PRIMARY KEY AUTOINCREMENT, matv INTEGER REFERENCES THANHVIEN(matv), matt TEXT REFERENCES THUTHU(matt), maSach INTEGER REFERENCES SACH(maSach), ngay TEXT NOT NULL, traSach INTEGER NOT NULL, tienthue INTEGER NOT NULL)";
        db.execSQL(dbPhieuMuon);

        // default data
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
        db.execSQL("INSERT INTO THUTHU VALUES ('thuthu01','Nguyen Van Hung','1', 'admin'),('thuthu02','Le Thi Hoa Quynh','2', 'thuthu')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Cao Thi A','2000'),(2,'Tran Van B','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01', 1, '2022-03-19', 1, 2500),(2,1,'thuthu01', 3, '2022-03-19', 0, 2000),(3,2,'thuthu02', 1, '2022-03-19', 1, 2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
}
