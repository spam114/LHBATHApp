package com.example.basecommon.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.basecommon.R;
import com.example.basecommon.databinding.RepairDocumentActivityBinding;
import com.example.basecommon.model.SaveFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class RepairTestActivity extends BaseActivity{

    RepairDocumentActivityBinding binding;
    private SaveFile saveFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.repair_document_activity);

        binding.repairContentOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.repairContentSaveArea.setDrawingCacheEnabled(true);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();

                String FileName = sdf.format(date) + ".png";
                Bitmap capture = Bitmap.createBitmap(binding.repairContentSaveArea.getDrawingCache());
                binding.repairContentSaveArea.setDrawingCacheEnabled(false);

                File Dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                String output = Dir.getPath() + "/" + sdf.format(date) + ".pdf";

                if(!Dir.exists()){
                    Dir.mkdirs();
                }
                try {
                    saveFile.PdfSave(Dir,capture,FileName,output);

                    Toast.makeText(getApplicationContext(),"저장되었습니다.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(intent);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        binding.repairContentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        LocalDate now = LocalDate.now();
        binding.repairDate.setText(now.toString());
    }
}
