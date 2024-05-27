package com.example.basecommon.view.activity;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.basecommon.CommonApplication;
import com.example.basecommon.model.object.MainMenuItem;

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

    public void showFloatingNavigationView(){}

    public ArrayList<MainMenuItem> getMainMenuItem(){return CommonApplication.getInstance().getMainMenuItem();}
}
