package com.cs407.ut;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {

    private ArrayList<ItemDataClass> dataList;
    private Context context;

    public AccountAdapter(ArrayList<ItemDataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .load(dataList.get(position).getImageURL())
                .into(holder.recyclerImage);
        holder.recyclerName.setText(dataList.get(position).getItmeName());

        ItemDataClass item = dataList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", item.getItmeName());
                intent.putExtra("price", item.getItemPrice());
                intent.putExtra("description", item.getItemDescriptions());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView recyclerImage;
        TextView recyclerName;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerName = itemView.findViewById(R.id.recyclerName);
        }
    }
}
