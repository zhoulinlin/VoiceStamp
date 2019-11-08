package com.groupseven.voicestamp.recoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.db.bean.Record;
import com.groupseven.voicestamp.mainlist.adapter.RecordAdapter;
import com.groupseven.voicestamp.mainlist.views.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecordPlayActivity extends AppCompatActivity {

    private VoiceManager voiceManager;

    private static final String LOCAL_PATH ="local_path";

    private SlideRecyclerView recycler_view_list;
    private List<Record> mInventories;
    private RecordAdapter mRecordAdapter;

    public static void actionStart(Context context,String localPath) {
        Intent intent = new Intent(context, RecordPlayActivity.class);
        intent.putExtra(LOCAL_PATH,localPath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_play);

        String mRecPath = getIntent().getStringExtra(LOCAL_PATH);

        if(TextUtils.isEmpty(mRecPath)){
            Toast.makeText(this,"error!",Toast.LENGTH_SHORT).show();
            return;
        }

        initList();

        voiceManager = new VoiceManager(RecordPlayActivity.this, "/com.groupseven.voicestamp/audio");

        voiceManager.setVoiceListener(new VoiceCallBack() {
            @Override
            public void voicePath(String path) {
//                mRecPath = path;
            }

            @Override
            public void recFinish(String path,String duration) {

            }
        });

        voiceManager.sessionPlay(true, mRecPath);
    }


    private void initList(){
        recycler_view_list =  findViewById(R.id.recycler_view_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mInventories = new ArrayList<>();
        Record inventory;
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            inventory = new Record();
            inventory.setRecordTitle("测试数据" + i);
            inventory.setDuration(""+random.nextInt(100000));
            inventory.setLocalPath("0120816");
            inventory.setRecordId("20180219");
            inventory.setUserId(""+random.nextFloat());
            mInventories.add(inventory);
        }
        mRecordAdapter = new RecordAdapter(this, mInventories);
        recycler_view_list.setAdapter(mRecordAdapter);
        mRecordAdapter.setOnDeleteClickListener(new RecordAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                mInventories.remove(position);
                mRecordAdapter.notifyDataSetChanged();
                recycler_view_list.closeMenu();
            }
        });
    }
}
