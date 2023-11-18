package com.cs407.ut.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs407.ut.R;

import java.util.ArrayList;
import java.util.List;

public class LeftListAdapter extends RecyclerView.Adapter<LeftListAdapter.Myholder> {

    private List<String> dataList = new ArrayList<>();

    public LeftListAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_list_item,null);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        String name = dataList.get(position);
        holder.tv_name.setText(name);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class Myholder extends RecyclerView.ViewHolder{
        TextView tv_name;
        public Myholder(@NonNull View itemView){
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
        }
    }
}
