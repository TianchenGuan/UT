package com.cs407.ut.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;
// 定义一个抽象类 BindAdapter，继承自 RecyclerView.Adapter<BindHolder<VB>>
// VB 是 ViewBinding 的泛型，Data 是数据的泛型
public abstract class BindAdapter<VB extends ViewBinding, Data> extends RecyclerView.Adapter<BindHolder<VB>> {
    // 定义一个数据列表，用于存储数据
    private List<Data> data = new ArrayList<>();

    // 定义一个公共方法，用于获取数据列表
    public List<Data> getData() {
        return data;
    }

    // 重写 onCreateViewHolder 方法，用于创建 ViewHolder
    @NonNull
    @Override
    public BindHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 调用 createHolder 方法创建 ViewHolder
        return new BindHolder<>(createHolder(parent));
    }

    // 定义一个抽象方法，用于创建 ViewHolder
    public abstract VB createHolder(ViewGroup parent);

    // 重写 onBindViewHolder 方法，用于绑定数据
    @Override
    public void onBindViewHolder(@NonNull BindHolder<VB> holder, int position) {
        // 获取当前位置的数据
        Data d = data.get(position);
        // 调用 bind 方法绑定数据
        bind(holder.getVb(), d, position);
    }

    // 定义一个抽象方法，用于绑定数据
    public abstract void bind(VB vb, Data data, int position);

    // 重写 getItemCount 方法，用于获取数据列表的大小
    @Override
    public int getItemCount() {
        return data.size();
    }
}
