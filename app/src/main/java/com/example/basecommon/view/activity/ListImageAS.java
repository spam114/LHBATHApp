package com.example.basecommon.view.activity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.ListviewImageAsAddBinding;
import com.example.basecommon.model.object.SupervisorComplaintImage;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.view.adapter.ComplainImageAdapter;
import com.example.basecommon.view.adapter.ImageAdapter;
import com.example.basecommon.viewModel.SupervisorComplaintImageViewModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListImageAS extends BaseActivity{
    ListviewImageAsAddBinding binding;
    SupervisorComplaintImageViewModel complaintImageViewModel;
    private List<SupervisorComplaintImage> Images;
    private String ItemNo;
    boolean enableFlag = true;
    int RESULT_GALLERY2 = 0;
    int RESULT_MULTI_PICTURE = 3;
    String imgPath= "";
    byte[] byteArray = null;
    String key;

    private void startProgress() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressOFF2();
            }
        }, 5000);
        progressON("Loading...", handler);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.listview_image_as_add);
        complaintImageViewModel = new ViewModelProvider(this).get(SupervisorComplaintImageViewModel.class);
        observerViewModel();
        Intent GetIntent = getIntent();
        ItemNo = GetIntent.getStringExtra("ItemNo");
        if(ItemNo != null){
            key = ItemNo;
            complaintImageViewModel.GetSupervisorComplaintImageList(ItemNo);
        }
        ButtonEvent();
    }

    private void observerViewModel(){
        complaintImageViewModel.Images.observe(this, data -> {
            ArrayList<SupervisorComplaintImage> Images = new ArrayList<>(data);
            ComplainImageAdapter adapter = new ComplainImageAdapter(this, R.layout.listview_imagerow, Images,enableFlag);
            adapter.setComplainViewModel(complaintImageViewModel);
            binding.listViewImage.setAdapter(adapter);
            binding.listViewImage.setFocusable(false);
            progressOFF2();
        });
        complaintImageViewModel.loading.observe(this, isLoading -> {
            if(isLoading){
                startProgress();
            }else{
                progressOFF2();
            }
        });
    }

    private void ButtonEvent(){
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
        List<SupervisorComplaintImage> ImageList = new ArrayList<>();

        try {
            for(Uri uri : uriArrayList){
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bStream);
                byteArray = bStream.toByteArray();

                SupervisorComplaintImage Image = new SupervisorComplaintImage();
                Image.ImageFile = CompressImage(Base64.encodeToString(byteArray,Base64.DEFAULT));
                Image.ImageName = uri.getLastPathSegment();
                Image.itemNo = key;
                Image.No = 0;
                Image.Type = 2;
                Image.InsertUser = Users.SupervisorCode;
                Image.UpdateUser = Users.SupervisorCode;

                ImageList.add(Image);
            }

            complaintImageViewModel.InsertSupervisorComplainImageMulti(ImageList);
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
