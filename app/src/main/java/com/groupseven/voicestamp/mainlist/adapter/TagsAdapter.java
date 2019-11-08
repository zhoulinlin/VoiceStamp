package com.groupseven.voicestamp.mainlist.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.db.bean.RecTag;
import com.groupseven.voicestamp.db.bean.Record;

import java.util.List;


public class TagsAdapter extends BaseRecyclerViewAdapter<RecTag> {

    private OnDeleteClickLister mDeleteClickListener;

    public TagsAdapter(Context context, List<RecTag> data) {
        super(context, data, R.layout.item_tag);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, RecTag bean, int position) {

        View view = holder.getView(R.id.btn_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }

        ((TextView) holder.getView(R.id.tv_item_title)).setText(bean.getTagTitle());
        ((TextView) holder.getView(R.id.tv_item_detail)).setText(bean.getTagContent());
        ((TextView) holder.getView(R.id.tv_item_duration)).setText(bean.getTagDate());
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
