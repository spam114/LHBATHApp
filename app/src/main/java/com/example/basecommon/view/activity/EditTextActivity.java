package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.basecommon.R;
import com.example.basecommon.databinding.EditTextActivityBinding;

public class EditTextActivity extends BaseActivity{
    EditTextActivityBinding binding;
    private String titleBackUp;
    private String contentBackUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.edit_text_activity);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        titleBackUp = intent.getStringExtra("title");
        contentBackUp = intent.getStringExtra("content");
        binding.textViewtitle.setText(titleBackUp);
        binding.editTextContent.setText(contentBackUp);
        progressOFF2();
    }

    public void mOnClick(View view){
        int id = view.getId();
        if (id == R.id.btnCancel) {
            makeResultIntent(contentBackUp, 5);
        } else if (id == R.id.btnOK) {
            makeResultIntent(binding.editTextContent.getText().toString(), RESULT_OK);
        }
    }

    private void makeResultIntent(String content, int resultCode){
        Intent intent = getIntent();
        intent.putExtra("content",binding.editTextContent.getText().toString());
        setResult(resultCode,intent);
        finish();
    }
}
