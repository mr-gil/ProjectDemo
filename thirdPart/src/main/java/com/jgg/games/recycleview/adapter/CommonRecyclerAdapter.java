package com.jgg.games.recycleview.adapter;

import android.content.Context;
import android.view.LayoutInflater;


import com.jgg.games.recycleview.base.*;
import com.jgg.games.recycleview.base.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonRecyclerAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public CommonRecyclerAdapter(final Context context, final int layoutId) {
        this(context, new ArrayList<T>(), layoutId);
    }

    public CommonRecyclerAdapter(final Context context, List<T> datas, final int layoutId) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(RecyclerViewHolder holder, T t, int position) {
                CommonRecyclerAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(RecyclerViewHolder holder, T t, int position);


}
