package poly.hungnvph40917.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.hungnvph40917.duanmau.Adapter.LoaiSachAdapter;
import poly.hungnvph40917.duanmau.DAO.LoaiSachDAO;
import poly.hungnvph40917.duanmau.R;
import poly.hungnvph40917.duanmau.model.ItemClick;
import poly.hungnvph40917.duanmau.model.LoaiSach;

public class QLLoaiSach extends Fragment {

    RecyclerView recyclerLoaiSach;
    LoaiSachDAO dao;
    EditText editLoaiSach;
    int maloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);

        recyclerLoaiSach = view.findViewById(R.id.recyclerLoaiSach);
        editLoaiSach = view.findViewById(R.id.edtLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnSua = view.findViewById(R.id.btnSua);

        dao = new LoaiSachDAO(getContext());

        loadData();

        btnThem.setOnClickListener(view1 -> {
            String tenloai = editLoaiSach.getText().toString();

            if (dao.themLoaiSach(tenloai) == true) {
                Toast.makeText(getContext(), "them loai sach thanh cong", Toast.LENGTH_SHORT).show();
                loadData();
                editLoaiSach.setText("");
            } else {
                Toast.makeText(getContext(), "them loai sach that bai", Toast.LENGTH_SHORT).show();
            }
        });

        btnSua.setOnClickListener(view1 -> {
            String tenLoai = editLoaiSach.getText().toString();
            LoaiSach loaiSach = new LoaiSach(maloai, tenLoai);
            if (dao.thayDoiLoaiSach(loaiSach)) {
                loadData();
                editLoaiSach.setText("");
            } else {
                Toast.makeText(getContext(), "thay doi thong tin khong thanh cong", Toast.LENGTH_SHORT).show();
            };
        });

        return view;
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(linearLayoutManager);

        ArrayList<LoaiSach> list = dao.getDsLoaiSach();
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClick(LoaiSach loaiSach) {
                editLoaiSach.setText(loaiSach.getTenloai());
                maloai = loaiSach.getId();
            }
        });
        recyclerLoaiSach.setAdapter(adapter);
    }
}
