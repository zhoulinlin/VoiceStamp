package com.groupseven.voicestamp.recoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.mainlist.activity.MainActivity;


public class RecorderMainActivity extends AppCompatActivity {

//    private RelativeLayout mLayoutPlay;
//    private Button mBtPlay;
    private VoiceManager voiceManager;
    private String mRecPath = "";

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RecorderMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);

//        mLayoutPlay = (RelativeLayout) findViewById(R.id.layout_play);
//        mBtPlay = (Button) findViewById(R.id.button_play);

        voiceManager = new VoiceManager(RecorderMainActivity.this, "/com.groupseven.voicestamp/audio");

        voiceManager.setVoiceListener(new VoiceCallBack() {
            @Override
            public void voicePath(String path) {
                mRecPath = path;
            }

            @Override
            public void recFinish() {
                Log.d("recFinish","path:" + mRecPath);
                RecordPlayActivity.actionStart(RecorderMainActivity.this,mRecPath);
//                mBtPlay.setVisibility(View.VISIBLE);
            }
        });

        voiceManager.sessionRecord(true);

//        mBtPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mLayoutPlay.setVisibility(View.VISIBLE);
//                voiceManager.sessionPlay(true, mRecPath);
//            }
//        });
    }


}
