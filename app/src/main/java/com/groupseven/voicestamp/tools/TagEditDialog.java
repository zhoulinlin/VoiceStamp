package com.groupseven.voicestamp.tools;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.groupseven.voicestamp.R;

public class TagEditDialog extends ABaseDialog{

    private View contentView;

    private TextView tvMessage1,tvMessage2,tvMessage3;

    private EditText editText1,editText2,editText3;

    @Override
    public View createContentView() {
        return contentView;
    }

    public TagEditDialog(Context context) {
        super(context);
        contentView = LayoutInflater.from(getContext()).inflate(R.layout.edit_tag_layout, null);
        tvMessage1 = contentView.findViewById(R.id.tv_message1);
        tvMessage2 = contentView.findViewById(R.id.tv_message2);
        tvMessage3 = contentView.findViewById(R.id.tv_message3);

        editText1 = contentView.findViewById(R.id.edittext1);
        editText2 = contentView.findViewById(R.id.edittext2);
        editText3 = contentView.findViewById(R.id.edittext3);
        setVerticalLine1Visible(true);
    }


    public TagEditDialog setText1(String text){
        tvMessage1.setText(text);
        return this;
    }

    public TagEditDialog setText2(String text){
        tvMessage2.setText(text);
        return this;
    }

    public TagEditDialog setText3(String text){
        tvMessage3.setText(text);
        return this;
    }

    public TagEditDialog setEditText1(String text){
        editText1.setText(text);
        return this;
    }

    public TagEditDialog setEditText2(String text){
        editText2.setText(text);
        return this;
    }

    public TagEditDialog setEditText3(String text){
        editText3.setText(text);
        return this;
    }

    public String getEdittext1(){
        return editText1.getText().toString();
    }

    public String getEdittext2(){
        return editText2.getText().toString();
    }

    public String getEdittext3(){
        return editText3.getText().toString();
    }

    public void setEditListener(TextWatcher watcher){
        editText3.addTextChangedListener(watcher);
    }


    public void disableSection3(){
        tvMessage3.setVisibility(View.GONE);
        editText3.setVisibility(View.GONE);
    }

}
