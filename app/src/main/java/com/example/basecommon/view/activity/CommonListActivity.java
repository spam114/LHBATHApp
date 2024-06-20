package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.basecommon.R;
import com.example.basecommon.databinding.ActivityCommonListviewBinding;

import java.util.ArrayList;
import java.util.List;

public class CommonListActivity extends BaseActivity{
    ActivityCommonListviewBinding binding;
    private int parent;
    private String Title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setBar();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_listview);
        Intent intent = getIntent();

    }

    private void ListBinding(){

    }
}
