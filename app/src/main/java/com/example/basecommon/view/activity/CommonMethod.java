package com.example.basecommon.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.example.basecommon.R;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.view.PreferenceManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CommonMethod {
    public static String keyResult;

    public static boolean onCreateOptionsMenu(BaseActivity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.itemBusiness).setVisible(false);
        menu.findItem(R.id.itemQR).setVisible(false);
        menu.findItem(R.id.itemKeyboard).setVisible(false);
        return true;
    }

    /**
     * 상단바 사업장 숨기기
     *
     * @param activity
     * @param menu
     * @return
     */
    public static boolean onCreateOptionsMenu2(BaseActivity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.itemBusiness).setVisible(false);
        return true;
    }

    public static boolean onCreateOptionsMenu3(BaseActivity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.itemBusiness).setVisible(false);
        menu.findItem(R.id.itemKeyboard).setVisible(false);
        menu.findItem(R.id.itemQR).setVisible(false);
        return true;
    }

    /**
     * 상단바 전부사용
     *
     * @param activity
     * @param menu
     * @return
     */
    public static boolean onCreateOptionsMenu4(BaseActivity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public static void setBar(BaseActivity activity) {
        Drawable drawable = activity.getResources().getDrawable(R.drawable.baseline_menu_black_36);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmap, 36, 36, true));
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(newdrawable);
    }



    public static FloatingNavigationView setFloatingNavigationView(BaseActivity activity) {
        View fnvHeader;
        FloatingNavigationView floatingNavigationView = (FloatingNavigationView) activity.findViewById(R.id.floating_navigation_view);
        fnvHeader = floatingNavigationView.getHeaderView(0);

        Menu menu = floatingNavigationView.getMenu();
        MenuItem m1 = menu.findItem(R.id.logOut);

        if(Users.Language == 0){
            m1.setTitle("로그아웃");
        }else{
            m1.setTitle("Sign Out");
        }

        SpannableString  s1 = new SpannableString(m1.getTitle());
        s1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s1.length(),0);
        m1.setTitle(s1);

        TextView txtUserName = fnvHeader.findViewById(R.id.txtUserName);
        txtUserName.setText(Users.UserName);
        TextView txtUserID = fnvHeader.findViewById(R.id.txtUserID);
        txtUserID.setText(Users.UserID);
        ImageView userImage = fnvHeader.findViewById(R.id.userImage);
        TextView txtCostCenter = fnvHeader.findViewById(R.id.txtCostCenter);
        txtCostCenter.setText(Users.CostCenterName);

        if(Users.UserImage.equals("fail")){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120,120);
            userImage.setLayoutParams(layoutParams);
            userImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.logo3));
        }else{
            try{
                byte[] array5 = Base64.decode(Users.UserImage, Base64.DEFAULT);
                userImage.setBackground(new ShapeDrawable(new OvalShape()));
                userImage.setClipToOutline(true);
                userImage.setImageBitmap(BitmapFactory.decodeByteArray(array5,0,array5.length));
            }catch (Exception e){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120,120);
                userImage.setLayoutParams(layoutParams);
                userImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.logo3));
            }
        }
        ImageView imvClose = fnvHeader.findViewById(R.id.imvClose);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingNavigationView.close();
            }
        });

        floatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.logOut){
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(activity,R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog);

                    String titleKor = "로그아웃";
                    String titleEng = "Sign Out";
                    String messageKor = "로그아웃 하시겠습니까?";
                    String messageEng = "Are you sure you want to Sign out?";
                    String poButtonKor = "확인";
                    String poButtonEng = "OK";
                    String negaButtonKor = "취소";
                    String negaButtonEng = "Cancel";

                    String strTitle;
                    String strMessage;
                    String strPoButton;
                    String strNegaButton;

                    if(Users.Language == 0){
                        strTitle = titleKor;
                        strMessage = messageKor;
                        strPoButton = poButtonKor;
                        strNegaButton = negaButtonKor;
                    }else{
                        strTitle = titleEng;
                        strMessage = messageEng;
                        strPoButton = poButtonEng;
                        strNegaButton = negaButtonEng;
                    }

                    alertDialogBuilder.setTitle(strTitle);
                    alertDialogBuilder.setMessage(strMessage);
                    alertDialogBuilder.setPositiveButton(strPoButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PreferenceManager.setBoolean(activity,"AutoLogin",false);
                            PreferenceManager.setString(activity,"ID","");
                            PreferenceManager.setString(activity,"PW","");
                            PreferenceManager.setString(activity,"PCCode","");
                            Intent intent = new Intent(activity,LoginActivity.class);
                            intent.putExtra("FirstFlag", false);

                            activity.startActivity(intent);
                            activity.finish();
                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.setNegativeButton(strNegaButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.show();
                }
                return false;
            }
        });
        return floatingNavigationView;

    }

    public static boolean onOptionsItemSelected(BaseActivity activity, MenuItem item, ActivityResultLauncher<Intent> resultLauncher, int type) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            activity.showFloatingNavigationView();
            return true;
        } else {
            return false;
        }
    }


}
