package com.cs407.ut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private boolean isEditMode = false;
    private OnItemRemoveListener removeListener;

    public interface OnItemRemoveListener {
        void onItemRemoved(ItemDataClass item);
    }

    public AccountAdapter(ArrayList<ItemDataClass> dataList, Context context, OnItemRemoveListener removeListener) {
        this.dataList = dataList;
        this.context = context;
        this.removeListener = removeListener;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
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

        holder.itemView.setOnClickListener(v -> {
            if (isEditMode) {
                // Remove the item from the list and notify the adapter
                dataList.remove(position);
                notifyItemRemoved(position);

                // Notify the activity (or fragment) to handle additional removal logic
                if (removeListener != null) {
                    removeListener.onItemRemoved(item);
                }

                // Remove the item from SharedPreferences
                SharedPreferences prefs = context.getSharedPreferences("SavedItems", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove(item.getItmeName());
                editor.apply();
            } else {
                // Normal behavior - start DetailActivity with item details
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", item.getItmeName());
                intent.putExtra("price", item.getItemPrice());
                intent.putExtra("description", item.getItemDescriptions());
                intent.putExtra("category", item.getItemCategory());
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
