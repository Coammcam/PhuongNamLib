package poly.hungnvph40917.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.hungnvph40917.duanmau.DAO.PhieuMuonDAO;
import poly.hungnvph40917.duanmau.R;
import poly.hungnvph40917.duanmau.model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {

    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieu_muon, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPM.setText("Ma phieu muon: " + list.get(position).getMapm());
        holder.txtMaTV.setText("Ma thanh vien: " + list.get(position).getMatv());
        holder.txtTenTV.setText("Ten thanh vien: " + list.get(position).getTentv());
        holder.txtMaTT.setText("Ma thu thu: " + list.get(position).getMatt());
        holder.txtTenTT.setText("Ten thu thu: " + list.get(position).getTentt());
        holder.txtMaSach.setText("Ma sach: " + list.get(position).getMasach());
        holder.txtTenSach.setText("Ten sach: " + list.get(position).getTensach());
        holder.txtNgay.setText("Ngay: " + list.get(position).getNgay());
        String trangthai = "";
        if (list.get(position).getTrasach() == 1) {
            trangthai = "Da tra sach";
            holder.btnTraSach.setVisibility(View.GONE);
        } else {
            trangthai = "Chua tra sach";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("Trang thai: " + trangthai);
        holder.txtTien.setText("Tien: " + list.get(position).getTienthue());

        holder.btnTraSach.setOnClickListener(view -> {
            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
            boolean kiemtra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
            if (kiemtra == true) {
                list.clear();
                list = phieuMuonDAO.getDSPhieuMuon();
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "thay doi trang thai that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaPM, txtMaTV, txtTenTV, txtMaTT, txtTenTT, txtMaSach, txtTenSach, txtNgay, txtTrangThai, txtTien;
        Button btnTraSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtTenTT = itemView.findViewById(R.id.txtTenTT);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTien = itemView.findViewById(R.id.txtTien);

            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
