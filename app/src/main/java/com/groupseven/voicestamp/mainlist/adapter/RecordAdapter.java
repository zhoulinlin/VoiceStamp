package com.groupseven.voicestamp.mainlist.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.db.bean.Record;

import java.util.List;


public class RecordAdapter extends BaseRecyclerViewAdapter<Record> {

    private OnDeleteClickLister mDeleteClickListener;

    public RecordAdapter(Context context, List<Record> data) {
        super(context, data, R.layout.item_record);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, Record bean, int position) {
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

        ((TextView) holder.getView(R.id.tv_item_title)).setText(bean.getRecordTitle());
        ((TextView) holder.getView(R.id.tv_item_date)).setText(bean.getRecordDate());
        ((TextView) holder.getView(R.id.tv_item_duration)).setText(bean.getDuration());
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
