package com.groupseven.voicestamp.mainlist.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
    }

    /**
     * 获取需要的View，如果已经存在引用则直接获取，如果不存在则重新加载并保存
     *
     * @param viewId id
     * @return 需要的View
     */
    View getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }
}
