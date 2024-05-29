package com.example.basecommon.view.activity;

import android.os.Handler;
import android.view.MenuItem;

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


}
