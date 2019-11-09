package com.groupseven.voicestamp.mainlist.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.db.DBController;
import com.groupseven.voicestamp.db.bean.Record;
import com.groupseven.voicestamp.mainlist.adapter.BaseRecyclerViewAdapter;
import com.groupseven.voicestamp.mainlist.adapter.RecordAdapter;
import com.groupseven.voicestamp.mainlist.views.SlideRecyclerView;
import com.groupseven.voicestamp.recoder.RecordPlayActivity;
import com.groupseven.voicestamp.recoder.RecorderMainActivity;
import com.groupseven.voicestamp.tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private SlideRecyclerView recycler_view_list;
    private List<Record> mRecords;
    private RecordAdapter mRecordAdapter;
    private Button startBtn;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        setStatusBarFullTransparent();
        setFitSystemWindow(false);

        recycler_view_list = (SlideRecyclerView) findViewById(R.id.recycler_view_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        startBtn = findViewById(R.id.record_btn);

        mRecords = DBController.getInstance().getRecordDao().queryRecordList(SharedPreferencesUtil.getUserId(this));

        mRecordAdapter = new RecordAdapter(this, mRecords);
        recycler_view_list.setAdapter(mRecordAdapter);
        mRecordAdapter.setOnDeleteClickListener(new RecordAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {

                if(DBController.getInstance().deleteRecord(mRecords.get(position).getRecordId())){
                    mRecords.remove(position);
                    mRecordAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this,"Delete failed!",Toast.LENGTH_SHORT).show();
                }
                recycler_view_list.closeMenu();
            }
        });


        mRecordAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
                RecordPlayActivity.actionStart(MainActivity.this,mRecords.get(position).getLocalPath(),mRecords.get(position).getRecordId(),position);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecorderMainActivity.actionStart(MainActivity.this);
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mRecords = DBController.getInstance().getRecordDao().queryRecordList(SharedPreferencesUtil.getUserId(this));
        mRecordAdapter.setData(mRecords);
        mRecordAdapter.notifyDataSetChanged();

    }
}
