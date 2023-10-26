package poly.hungnvph40917.duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import poly.hungnvph40917.duanmau.DAO.SachDAO;
import poly.hungnvph40917.duanmau.R;
import poly.hungnvph40917.duanmau.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;
    ArrayList<HashMap<String, Object>> listHM;

    SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sach, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Ma sach: " + list.get(position).getMaSach());
        holder.txtTenSach.setText("Ten sach: " + list.get(position).getTenSach());
        holder.txtGiaThue.setText("Gia Thue: " + list.get(position).getGiaThue());
        holder.txtMaLoai.setText("Ma loai: " + list.get(position).getMaLoai());
        holder.txtTenLoai.setText("Ten loai: " + list.get(position).getTenloai());

        holder.ivEdit.setOnClickListener(view -> {
            showDialog(list.get(holder.getAdapterPosition()));
        });

        holder.ivDel.setOnClickListener(view -> {
            int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMaSach());

            switch (check) {
                case 1:
                    Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    break;
                case 0:
                    Toast.makeText(context, "xoa that bai", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(context, "khong duoc xoa sach nay vi co trong phieu muon", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }

    private void showDialog(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_sua_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTensach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        TextView txtMaSach = view.findViewById(R.id.txtMaSach);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        txtMaSach.setText("Ma sach: " + sach.getMaSach());
        edtTenSach.setText(sach.getTenSach());
        edtTien.setText(String.valueOf(sach.getGiaThue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM, android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiSach.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;
        for (HashMap<String, Object> item: listHM) {
            if ((int)item.get("maloai") == sach.getMaLoai()) {
                position = index;
            } else {
                index++;
            }
        }
        spnLoaiSach.setSelection(position);


        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edtTenSach.getText().toString();
                int tien = Integer.parseInt(edtTien.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                boolean check = sachDAO.capNhatThongTinSach(sach.getMaSach(), tensach, tien, maloai);
                if (check) {
                    Toast.makeText(context, "Cap nhat loai sach thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(context, "Cap nhat loai sach that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadData() {
        listHM.clear();
        list = sachDAO.getDsDauSach();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSach, txtTenSach, txtGiaThue, txtMaLoai, txtTenLoai;

        ImageView ivEdit, ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);

            ivDel = itemView.findViewById(R.id.ivDel);
            ivEdit = itemView.findViewById(R.id.ivEdit);

        }
    }
}
