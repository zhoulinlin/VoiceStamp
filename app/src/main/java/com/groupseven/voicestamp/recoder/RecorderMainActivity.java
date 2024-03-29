package com.groupseven.voicestamp.recoder;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.db.DBController;
import com.groupseven.voicestamp.db.bean.RecTag;
import com.groupseven.voicestamp.db.bean.Record;
import com.groupseven.voicestamp.login.data.LoginDataSource;
import com.groupseven.voicestamp.login.data.LoginRepository;
import com.groupseven.voicestamp.mainlist.activity.BaseActivity;
import com.groupseven.voicestamp.mainlist.activity.MainActivity;
import com.groupseven.voicestamp.recoder.utils.CommonTools;
import com.groupseven.voicestamp.tools.DialogFactory;
import com.groupseven.voicestamp.tools.MessageDialog;
import com.groupseven.voicestamp.tools.SharedPreferencesUtil;
import com.groupseven.voicestamp.tools.TagEditDialog;


public class RecorderMainActivity extends BaseActivity {

    private VoiceManager voiceManager;
    private String mRecPath = "";
    private TagEditDialog tagDialog;

    private MessageDialog dialog;
    private Button btTag;
    private String recordId = CommonTools.getRandomId();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RecorderMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);

        setStatusBarFullTransparent();
        setFitSystemWindow(false);

        btTag = findViewById(R.id.btn_tag);

        btTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                final long time  = voiceManager.getTime();
//
//                dialog = DialogFactory.editDiaglog(RecorderMainActivity.this, R.string.tag_input_hint, "Save", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        saveTag(dialog.getEditText().getText().toString(),time,CommonTools.getDate());
//                    }
//                },R.string.tag_title,CommonTools.getDate());

                final long time  = voiceManager.getTime();

                tagDialog = DialogFactory.createTagEditDialog(RecorderMainActivity.this, "Save", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveTag(tagDialog.getEdittext1(),tagDialog.getEdittext2(),time);
                    }
                }, R.string.tag_title);

                tagDialog.setEditText1(CommonTools.getDate())
                        .setEditText2(CommonTools.getDate())
                        .disableSection3();

                tagDialog.show();;

            }
        });

        voiceManager = new VoiceManager(RecorderMainActivity.this, "/com.groupseven.voicestamp/audio");

        voiceManager.setVoiceListener(new VoiceCallBack() {
            @Override
            public void voicePath(String path) {
                mRecPath = path;
            }

            @Override
            public void recFinish(final String path,final String duration) {
                Log.d("RecorderMainActivity","path:" + mRecPath);

                dialog = DialogFactory.editDiaglog(RecorderMainActivity.this, 0, "Save", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveRecord(path,duration,dialog.getEditText().getText().toString());
                        MainActivity.actionStart(RecorderMainActivity.this);
                        finish();
                    }
                },R.string.voice_title,CommonTools.getDate());
            }
        });

        voiceManager.sessionRecord(true);

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.actionStart(RecorderMainActivity.this);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(voiceManager != null){
//            voiceManager.
        }
    }

    private void saveTag(String title,String content, long time){

        RecTag tag = new RecTag();
        tag.setTagType("text");
        tag.setTagTitle(title);
        tag.setTagDate(time);
        tag.setTagContent(content);
        tag.setRecordId(recordId);
        tag.setExtInfo(CommonTools.getRandomId());

        if(!DBController.getInstance().getTagDao().insertTag(tag)){
            Log.e("RecorderMainActivity","saveTag failed:" + tag);
        }else{
            Log.i("RecorderMainActivity","saveTag success:" + tag);
        }

    }




    private void saveRecord(String path,String duration,String title){

        Record record = new Record();

        record.setUserId(LoginRepository.getInstance(new LoginDataSource()).getUser().getUniqueKey());
        record.setRecordTitle(title);
        record.setRecordId(recordId);
        record.setRecordDate(CommonTools.getDate());
        record.setLocalPath(path);
        record.setDuration(duration);

        if(!DBController.getInstance().getRecordDao().insertRecord(record)){
            Log.e("RecorderMainActivity","saveRecord failed:" + path);
        }else{
            Log.i("RecorderMainActivity","saveRecord success:" + record);
        }
    }



}
