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
import com.groupseven.voicestamp.db.DBController;
import com.groupseven.voicestamp.db.bean.RecTag;
import com.groupseven.voicestamp.db.bean.Record;
import com.groupseven.voicestamp.mainlist.activity.BaseActivity;
import com.groupseven.voicestamp.mainlist.adapter.RecordAdapter;
import com.groupseven.voicestamp.mainlist.adapter.TagsAdapter;
import com.groupseven.voicestamp.mainlist.views.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecordPlayActivity extends BaseActivity {

    private VoiceManager voiceManager;

    private static final String LOCAL_PATH ="local_path";

    private static final String RECORD_ID ="record_id";

    private SlideRecyclerView recycler_view_list;

    private List<RecTag> mTags;

    private TagsAdapter mTagAdapter;

    private String mRecordId;

    public static void actionStart(Context context,String localPath,String recordId) {
        Intent intent = new Intent(context, RecordPlayActivity.class);
        intent.putExtra(LOCAL_PATH,localPath);
        intent.putExtra(RECORD_ID,recordId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_play);

        setStatusBarFullTransparent();
        setFitSystemWindow(false);

        String mRecPath = getIntent().getStringExtra(LOCAL_PATH);

        mRecordId = getIntent().getStringExtra(RECORD_ID);

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
        mTags = DBController.getInstance().getTagDao().queryTagList(mRecordId);

        mTagAdapter = new TagsAdapter(this, mTags);

        recycler_view_list.setAdapter(mTagAdapter);

        mTagAdapter.setOnDeleteClickListener(new TagsAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                if(DBController.getInstance().getTagDao().deleteTagByExtInfo(mTags.get(position).getExtInfo())){
                    mTags.remove(position);
                    mTagAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(RecordPlayActivity.this,"Delete tag failed!",Toast.LENGTH_SHORT).show();
                }

                recycler_view_list.closeMenu();
            }
        });
    }
}
