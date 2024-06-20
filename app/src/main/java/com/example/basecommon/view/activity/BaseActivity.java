package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.R;
import com.example.basecommon.model.object.MainMenuItem;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> resultLauncher;
    public void progressON() {
        CommonApplication.getInstance().progressON(this, null);
    }

    public void HideKeyBoard() {
        CommonApplication.getInstance().HideKeyBoard(this);
    }

    public void progressON(String message) {
        CommonApplication.getInstance().progressON(this, message);
    }

    public void progressON(String message, Handler handler) {
        CommonApplication.getInstance().progressON(this, message, handler);
    }

    public void progressOFF2() {
        CommonApplication.getInstance().progressOFF2();
    }

    public void getKeyInResult(String result){}

    public void showNavigationView(){}

    public ArrayList<MainMenuItem> getMainMenuItem(){return CommonApplication.getInstance().getMainMenuItem();}

    public void setBar() {
        CommonMethod.setNavigationView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return CommonMethod.onOptionsItemSelected(this, item, resultLauncher, 2);
    }

    public void setBarTitle(String title){
        TextView textView = findViewById(R.id.ToolbarTitle);
        textView.setText(title);
    }
}
