package com.example.basecommon.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.EventLog;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.basecommon.R;
import com.example.basecommon.databinding.TestCameraActivityBinding;

import java.time.LocalDate;

public class CameraTestActivity extends BaseActivity{
    TestCameraActivityBinding binding;
    Bitmap bitmap;
    private String titleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this,R.layout.test_camera_activity);
        setBar();
        Intent intent = getIntent();
        titleName = intent.getStringExtra("titleName");
        setBarTitle(titleName);
        ButtonEvent();
        EditTextSetting(intent);
    }

    public void ButtonEvent(){
        binding.CameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(intent);
            }
        });

        binding.ASBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void EditTextSetting(Intent intent){
        String construction = intent.getStringExtra("construction");
        String constructionContent = intent.getStringExtra("constructionContent");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.ASDate.setText(LocalDate.now().toString());
        }

        binding.GongJong.setText(construction);
        binding.ASContext.setText(constructionContent);
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle extras = result.getData().getExtras();

                        bitmap = (Bitmap) extras.get("data");

                        binding.CameraImage.setImageBitmap(bitmap);
                    }
                }
            }
    );


}
