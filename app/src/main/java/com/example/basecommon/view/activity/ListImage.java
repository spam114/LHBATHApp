package com.example.basecommon.view.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.DialogPhotoBinding;
import com.example.basecommon.databinding.ListviewImageBinding;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.view.adapter.ImageAdapter;
import com.example.basecommon.viewModel.SupervisorWoImageViewModel;
import com.itextpdf.text.pdf.qrcode.ByteArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListImage extends BaseActivity{
    ListviewImageBinding binding;
    SupervisorWoImageViewModel supervisorWoImageViewModel;
    boolean enableFlag = true;
    private String key;
    int RESULT_GALLERY2 = 0;
    int RESULT_MULTI_PICTURE = 3;
    String imgPath = "";
    Dialog dialog;
    byte[] byteArray = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.listview_image);
        Intent intent = getIntent();
        this.key = intent.getStringExtra("key");
        supervisorWoImageViewModel = new ViewModelProvider(this).get(SupervisorWoImageViewModel.class);
        observerViewModel();
        supervisorWoImageViewModel.GetSupervisorWoImageData(this.key);
        viewSetting();
    }

    private void observerViewModel(){
        supervisorWoImageViewModel.ImageList.observe(this, data->{
            ArrayList<SupervisorWoImage> Images = new ArrayList<>(data);
            ImageAdapter adapter = new ImageAdapter(this, R.layout.listview_imagerow, Images,enableFlag);
            adapter.setSupervisorWoImageViewModel(supervisorWoImageViewModel);
            binding.listViewImage.setAdapter(adapter);
            binding.listViewImage.setFocusable(false);
            progressOFF2();
        });
    }

    private void viewSetting(){
        View Header = (View) getLayoutInflater().inflate(R.layout.listview_image_header, null);
        binding.listViewImage.addHeaderView(Header);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT < 19) {

                    Intent intent2 = new Intent();
                    intent2.setType("image/*");
                    intent2.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent2, "Select Picture"), RESULT_GALLERY2);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_MULTI_PICTURE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_GALLERY2 && resultCode == RESULT_OK && null != data){
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);
            int column_index = cursor.getColumnIndex(proj[0]);
            cursor.moveToFirst();
            imgPath = cursor.getString(column_index);
            imgPath = imgPath.substring(imgPath.lastIndexOf("/") + 1);
            Uri uri = data.getData();
        }else if(requestCode == RESULT_MULTI_PICTURE && resultCode == RESULT_OK && null != data){
            ArrayList<Uri> uriList = new ArrayList<>();
            if(data.getClipData() == null){
                Uri imageUri = data.getData();
                uriList.add(imageUri);
            }else{
                ClipData clipData = data.getClipData();

                if(clipData.getItemCount() > 10){
                    Toast.makeText(getApplicationContext(),"사진은 10까지 선택 가능합니다.",Toast.LENGTH_LONG).show();
                }else{
                    for(int i = 0; i < clipData.getItemCount(); i++){
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        uriList.add(imageUri);
                    }
                }
            }
            updateImageMulti(uriList);
        }
    }

    private void updateImageMulti(ArrayList<Uri> uriArrayList){
        List<SupervisorWoImage> WoImageList = new ArrayList<>();

        try {
            for(Uri uri : uriArrayList){
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bStream);
                byteArray = bStream.toByteArray();

                SupervisorWoImage WoImage = new SupervisorWoImage();
                WoImage.ImageFile = CompressImage(Base64.encodeToString(byteArray,Base64.DEFAULT));
                WoImage.ImageName = uri.getLastPathSegment();
                WoImage.SupervisorWoNo = key;
                WoImage.SeqNo = "0";
                WoImage.smallImageFile = WoImage.ImageFile;

                WoImageList.add(WoImage);
            }

            supervisorWoImageViewModel.InsertSupervisorWoImageMulti(WoImageList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String CompressImage(String jsonString){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        byte[] decodedString = Base64.decode(jsonString,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length,options);

        int targetWidth = 1000;
        int targetHeight = (int)(decodedByte.getHeight() * targetWidth/(double)decodedByte.getWidth());

        Bitmap resized = Bitmap.createScaledBitmap(decodedByte, targetWidth,targetHeight,true);
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        resized.compress(Bitmap.CompressFormat.JPEG, 80 , bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
}
