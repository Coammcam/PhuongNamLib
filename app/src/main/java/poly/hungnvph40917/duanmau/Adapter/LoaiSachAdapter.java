package poly.hungnvph40917.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.hungnvph40917.duanmau.DAO.LoaiSachDAO;
import poly.hungnvph40917.duanmau.R;
import poly.hungnvph40917.duanmau.model.ItemClick;
import poly.hungnvph40917.duanmau.model.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;

    private ItemClick itemClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenLoai.setText("Ten loai: " + list.get(position).getTenloai());
        holder.txtMaLoai.setText("Ma loai: " + String.valueOf(list.get(position).getId()));

        holder.ivDel.setOnClickListener(view -> {
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getId());
            switch (check) {
                case 1:
                    list.clear();
                    list = loaiSachDAO.getDsLoaiSach();
                    notifyDataSetChanged();
                    break;
                case -1:
                    Toast.makeText(context, "khong the xoa loai sach nay` vi da co sach thuoc the loai nay`", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(context, "xoa loai sach khong thanh cong", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoai, txtTenLoai;
        ImageView ivDel, ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
