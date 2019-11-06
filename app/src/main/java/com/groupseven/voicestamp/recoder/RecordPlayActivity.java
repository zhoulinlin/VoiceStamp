package com.groupseven.voicestamp.recoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.groupseven.voicestamp.R;
import com.groupseven.voicestamp.login.ui.login.LoginActivity;
import com.groupseven.voicestamp.mainlist.adapter.InventoryAdapter;
import com.groupseven.voicestamp.mainlist.bean.Inventory;
import com.groupseven.voicestamp.mainlist.views.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecordPlayActivity extends AppCompatActivity {

    private VoiceManager voiceManager;

    private static final String LOCAL_PATH ="local_path";

    private SlideRecyclerView recycler_view_list;
    private List<Inventory> mInventories;
    private InventoryAdapter mInventoryAdapter;

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
            public void recFinish() {

//                mBtPlay.setVisibility(View.VISIBLE);
            }
        });

        voiceManager.sessionPlay(true, mRecPath);

    }


    private void initList(){
        recycler_view_list =  findViewById(R.id.recycler_view_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mInventories = new ArrayList<>();
        Inventory inventory;
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            inventory = new Inventory();
            inventory.setItemDesc("测试数据" + i);
            inventory.setQuantity(random.nextInt(100000));
            inventory.setItemCode("0120816");
            inventory.setDate("20180219");
            inventory.setVolume(random.nextFloat());
            mInventories.add(inventory);
        }
        mInventoryAdapter = new InventoryAdapter(this, mInventories);
        recycler_view_list.setAdapter(mInventoryAdapter);
        mInventoryAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                mInventories.remove(position);
                mInventoryAdapter.notifyDataSetChanged();
                recycler_view_list.closeMenu();
            }
        });


    }
}
