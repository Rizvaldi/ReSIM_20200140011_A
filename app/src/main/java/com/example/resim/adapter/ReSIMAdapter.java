package com.example.resim.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resim.R;
import com.example.resim.user.ReSIM;

import java.util.ArrayList;
import java.util.List;

public class ReSIMAdapter extends RecyclerView.Adapter<ReSIMAdapter.MyViewHolder> {
    private Context context;
    private List<ReSIM> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public  ReSIMAdapter(Context context, List<ReSIM> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReSIMAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dataresim, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ReSIMAdapter.MyViewHolder holder, int position) {
        holder.nik.setText(list.get(position).getNik());
        holder.nohp.setText(list.get(position).getNohp());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  nik,nohp;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nik = itemView.findViewById(R.id.txtnik);
            nohp = itemView.findViewById(R.id.txtnohp);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(dialog != null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
