package com.example.basecommon.view.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.model.object.SupervisorWoImage;
import com.example.basecommon.viewModel.SupervisorWoImageViewModel;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ImageAdapter extends ArrayAdapter<SupervisorWoImage> {
    Context context;
    int layoutResourceId;
    ArrayList data;
    Handler mHandler;
    ProgressDialog mProgressDialog;
    SupervisorWoImage supervisorWoImage;
    SupervisorWoImageViewModel supervisorWoImageViewModel;
    public int removePosition;
    boolean enableFlag = true;

    public ImageAdapter(Context context, int layoutResourceId, ArrayList data, boolean enableFlag){
        super(context,layoutResourceId,data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        this.enableFlag = enableFlag;
    }

    public void setSupervisorWoImageViewModel(SupervisorWoImageViewModel supervisorWoImageViewModel){
        this.supervisorWoImageViewModel = supervisorWoImageViewModel;
    }
    @NonNull
    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_imagerow, null);
        }

        SupervisorWoImage image = (SupervisorWoImage) data.get(position);

        if (image != null) {
            TextView textViewSeqNo = (TextView) row.findViewById(R.id.textViewImageSeqNo);
            TextView textViewImageName = (TextView) row.findViewById(R.id.textViewImageName);
            Button deleteButton = (Button) row.findViewById(R.id.buttonDelete);
            ImageView imageView = (ImageView) row.findViewById(R.id.imageViewSmall);
            deleteButton.setFocusable(false);
            if(!enableFlag)
                deleteButton.setEnabled(false);

            if (textViewImageName != null)
                textViewImageName.setText(((SupervisorWoImage) (data.get(position))).ImageName);
            if (textViewSeqNo != null)
                textViewSeqNo.setText(String.valueOf(position + 1));

            deleteButton.setTag(position);

            try {
                byte[] array5 = Base64.decode(((SupervisorWoImage) data.get(position)).smallImageFile, Base64.DEFAULT);
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(array5, 0, array5.length));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("사진 삭제")
                            .setMessage("사진을 삭제하시겠습니까?")
                            .setCancelable(true)
                            .setPositiveButton
                                    ("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            supervisorWoImageViewModel.DeleteSupervisorWoImage((SupervisorWoImage) data.get(position));
                                        }
                                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });

        }

        return row;
    }
}
