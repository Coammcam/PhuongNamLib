package poly.hungnvph40917.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import poly.hungnvph40917.duanmau.DAO.SachDAO;
import poly.hungnvph40917.duanmau.DAO.ThuThuDAO;
import poly.hungnvph40917.duanmau.fragment.DoanhThu;
import poly.hungnvph40917.duanmau.fragment.DoiMatKhau;
import poly.hungnvph40917.duanmau.fragment.QLLoaiSach;
import poly.hungnvph40917.duanmau.fragment.QLPhieuMuonFragment;
import poly.hungnvph40917.duanmau.fragment.QLSach;
import poly.hungnvph40917.duanmau.fragment.QLThanhVien;
import poly.hungnvph40917.duanmau.fragment.Top10;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        View header_layout = navigationView.getHeaderView(0);
        TextView txtTen = header_layout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.mQLPhieuMuon) {
                    fragment = new QLPhieuMuonFragment();
                } else if (item.getItemId() == R.id.mQLLoaiSach) {
                    fragment = new QLLoaiSach();
                } else if (item.getItemId() == R.id.mQLSach) {
                    fragment = new QLSach();
                } else if (item.getItemId() == R.id.mQLThanhVien) {
                    fragment = new QLThanhVien();
                } else if (item.getItemId() == R.id.mTop10) {
                    fragment = new Top10();
                } else if (item.getItemId() == R.id.mDoanhThu) {
                    fragment = new DoanhThu();
                } else if (item.getItemId() == R.id.mDoiMatKhau) {
                    fragment = new Fragment();
                    showDialogDoiMatKhau();
                } else if (item.getItemId() == R.id.mDangXuat) {
                    fragment = new Fragment();
                    onBackPressed();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());

                return false;
            }
        });

        // hien thi chuc nang cho admin
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaiTK = sharedPreferences.getString("loaitaikhoan", "");
        if (loaiTK.equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mDoanhThu).setVisible(false);
            menu.findItem(R.id.mTop10).setVisible(false);
        }
        String hoten = sharedPreferences.getString("hoten", "");
        txtTen.setText("Xin chao, " + hoten);
    }

    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);

        EditText edtOldPass = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtPassNew);
        EditText edtConfirmPass = view.findViewById(R.id.edtConfirmPass);

        builder.setView(view);

        builder.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String oldPass = edtOldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String confirmPass = edtConfirmPass.getText().toString();
                if (newPass.equals(confirmPass)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    String matt = sharedPreferences.getString("matt", "");

                    ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                    boolean check = thuThuDAO.CapNhatMatKhau(matt, oldPass, newPass);
                    if (check) {
                        Toast.makeText(MainActivity.this, "Cap nhat mat khau thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent );
                    } else {
                        Toast.makeText(MainActivity.this, "that bai", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "nhap mat khau khong trung nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}