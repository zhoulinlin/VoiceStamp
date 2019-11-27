package com.groupseven.voicestamp.recoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.db.DBController;
import com.groupseven.voicestamp.db.bean.RecTag;
import com.groupseven.voicestamp.db.bean.Record;
import com.groupseven.voicestamp.mainlist.activity.BaseActivity;
import com.groupseven.voicestamp.mainlist.activity.MainActivity;
import com.groupseven.voicestamp.mainlist.adapter.BaseRecyclerViewAdapter;
import com.groupseven.voicestamp.mainlist.adapter.RecordAdapter;
import com.groupseven.voicestamp.mainlist.adapter.TagsAdapter;
import com.groupseven.voicestamp.mainlist.views.SlideRecyclerView;
import com.groupseven.voicestamp.recoder.utils.CommonTools;
import com.groupseven.voicestamp.tools.DialogFactory;
import com.groupseven.voicestamp.tools.MessageDialog;
import com.groupseven.voicestamp.tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecordPlayActivity extends BaseActivity {

    private VoiceManager voiceManager;

    private static final String LOCAL_PATH ="local_path";

    private static final String RECORD_ID ="record_id";

    private static final String RECORD_POSITION ="record_position";

    private SlideRecyclerView recycler_view_list;

    private List<RecTag> mTags;

    private TagsAdapter mTagAdapter;

    private String mRecordId;

    private List<Record> mRecords;

    private int curPosition;

    private MessageDialog dialog;

    public static void actionStart(Context context,String localPath,String recordId,int position) {
        Intent intent = new Intent(context, RecordPlayActivity.class);
        intent.putExtra(LOCAL_PATH,localPath);
        intent.putExtra(RECORD_ID,recordId);
        intent.putExtra(RECORD_POSITION,position);
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

        curPosition = getIntent().getIntExtra(RECORD_POSITION,-1);

        if(TextUtils.isEmpty(mRecPath)){
            Toast.makeText(this,"error!",Toast.LENGTH_SHORT).show();
            return;
        }

        voiceManager = new VoiceManager(RecordPlayActivity.this, "/com.groupseven.voicestamp/audio");

        initList();


        voiceManager.setVoiceListener(new VoiceCallBack() {
            @Override
            public void voicePath(String path) {
//                mRecPath = path;
            }

            @Override
            public void recFinish(String path,String duration) {

            }
        });

        findViewById(R.id.play_back).setOnClickListener(voiceManager.getmRewListener());

        findViewById(R.id.play_quick).setOnClickListener(voiceManager.getmFfwdListener());

        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNeighbor(true);
            }
        });

        findViewById(R.id.iv_pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNeighbor(false);
            }
        });

        voiceManager.sessionPlay(true, mRecPath);

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.actionStart(RecorderMainActivity.this);
                finish();
            }
        });
    }

    private void playNeighbor(boolean isNext){

        if(mRecords == null || mRecords.size() == 0){
            mRecords = DBController.getInstance().getRecordDao().queryRecordList();
        }

        if(mRecords == null || mRecords.size() == 0){
            Toast.makeText(RecordPlayActivity.this,"Play next error!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(isNext && mRecords.size()-1 == curPosition){
            Toast.makeText(RecordPlayActivity.this,"This is the last one !",Toast.LENGTH_SHORT).show();
            return;
        }


        if(!isNext && 0 == curPosition){
            Toast.makeText(RecordPlayActivity.this,"This is the first one !",Toast.LENGTH_SHORT).show();
            return;
        }


        if (isNext) {
            ++curPosition;
        } else {
            --curPosition;
        }

        Record record = mRecords.get(curPosition);
        voiceManager.stopPlaying();
        mRecordId = record.getRecordId();
        voiceManager.sessionPlay(true, record.getLocalPath());
        initList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(voiceManager != null){
            voiceManager.stopPlaying();
        }
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

        mTagAdapter.setOnItemClickListener(voiceManager.getmQuickPlay());

        mTagAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final int pos = (int)view.getTag();

                RecTag tag = mTagAdapter.getData().get(pos);

                dialog = DialogFactory.editDiaglog(RecordPlayActivity.this, R.string.tag_input_hint, "Update", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        updateTag(mTagAdapter.getData().get(pos),dialog.getEditText().getText().toString());
                    }
                }, R.string.update_tag,tag.getTagContent());


                return true;
            }
        });
    }

    private void updateTag(RecTag tag,String content){

        tag.setTagContent(content);

        if(!DBController.getInstance().getTagDao().updateTag(tag)){
            Log.e("RecordPlayActivity","updateTag failed:" + tag);
        }else{
            Log.i("RecordPlayActivity","updateTag success:" + tag);
            mTagAdapter.notifyDataSetChanged();
        }

    }
}
