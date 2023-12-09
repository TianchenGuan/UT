package com.cs407.ut.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

// 定义一个泛型类 BindHolder，继承自 RecyclerView.ViewHolder
public class BindHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {
    // 定义一个 VB 类型的变量 vb
    private VB vb;
    // 定义一个构造函数，参数为 VB 类型的变量 vb
    public BindHolder(VB vb) {
        // 调用父类的构造函数，传入 vb.getRoot() 作为参数
        super(vb.getRoot());
        // 将 vb 赋值给成员变量 this.vb
        this.vb = vb;
    }
    // 定义一个公共方法，返回 VB 类型的变量 vb
    public VB getVb() {
        return vb;
    }
}