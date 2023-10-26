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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import poly.hungnvph40917.duanmau.DAO.ThanhVienDAO;
import poly.hungnvph40917.duanmau.R;
import poly.hungnvph40917.duanmau.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTV.setText("Ma thanh vien: " + list.get(position).getMatv());
        holder.txtHoTen.setText("Ho ten: " + list.get(position).getHoten());
        holder.txtNamSinh.setText("Nam sinh: " + list.get(position).getNamsinh());

        holder.ivEdit.setOnClickListener(view -> {
            showDialogCapNhatThongTin(list.get(holder.getAdapterPosition()));
        });

        holder.ivDel.setOnClickListener(view -> {
            int check = thanhVienDAO.xoaThongTin(list.get(holder.getAdapterPosition()).getMatv());
            switch (check) {
                case 1:
                    Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                    break;
                case 0:
                    Toast.makeText(context, "xoa that bai", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(context, "thanh vien ton tai phieu muon, khong duoc phep xoa", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaTV, txtHoTen, txtNamSinh;
        ImageView ivEdit, ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);

            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    private void showDialogCapNhatThongTin(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_chinh_sua_thanh_vien, null);
        builder.setView(view);

        TextView txtMaTV = view.findViewById(R.id.txtMaTV);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        txtMaTV.setText("Ma thanh vien: " + thanhVien.getMatv());
        edtHoTen.setText(thanhVien.getHoten());
        edtNamSinh.setText(thanhVien.getNamsinh());

        builder.setNegativeButton("Cap nhat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String hoten = edtHoTen.getText().toString();
                String namsinh = edtNamSinh.getText().toString();

                int id = thanhVien.getMatv();

                boolean check = thanhVienDAO.capNhatThongTinThanhVien(id, hoten, namsinh);
                if (check) {
                    Toast.makeText(context, "Cap nhat thong tin thanh cong", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(context, "cap nhat thong tin khong thanh cong", Toast.LENGTH_SHORT).show();
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
        list.clear();
        list = thanhVienDAO.getDsThanhVien();
        notifyDataSetChanged();
    }
}
