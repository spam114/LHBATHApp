package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.basecommon.R;
import com.example.basecommon.databinding.AsListWriteAddBinding;

public class ASListWriteAdd extends BaseActivity{
    AsListWriteAddBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setBar();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this,R.layout.as_list_write_add);
        ButtonEvent();
    }

    private void ButtonEvent(){
        binding.HomeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        binding.Housing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CommonListActivity.class);
                intent.putExtra("Title","하우징");
                intent.putExtra("StandardNo",1);
                startActivity(intent);
            }
        });

        binding.FloorTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommonListActivity.class);
                startActivity(intent);
            }
        });

        binding.SecondFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.OtherAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
