package com.xiangri.dongdong.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public abstract class RecycleAdapter<T> extends RecyclerView.Adapter<ViewHolder> {


    private List<T> list = new ArrayList<>();
    private Context mcontext;

    public RecycleAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setList(List<T> datas) {
        this.list.clear();
        this.list.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(mcontext).inflate(getLayoutId(), null);
        return new ViewHolder(view, mcontext);
    }

    protected abstract int getLayoutId();

    protected abstract void convert(ViewHolder viewHolder, T t, int postion);

    public abstract void convertPrent(ViewHolder viewHolder, List<T> t, int postion);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("Tag", i + "");
        convert(viewHolder, list.get(i), i);
        convertPrent(viewHolder,list,i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}