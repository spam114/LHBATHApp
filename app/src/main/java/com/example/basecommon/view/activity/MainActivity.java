package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.basecommon.R;
import com.example.basecommon.databinding.ActivityMainBinding;
import com.example.basecommon.model.object.MainMenuItem;
import com.example.basecommon.model.object.SearchCondition;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.view.BackPressControl;
import com.example.basecommon.viewModel.AppVersionViewModel;
import com.example.basecommon.viewModel.LocationViewModel;
import com.example.basecommon.viewModel.SupervisorVIewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    AppVersionViewModel appVersionViewModel;
    BackPressControl backpressed;
    DrawerLayout drawerLayout;
    SupervisorVIewModel supervisorVIewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        appVersionViewModel.GetNoticeData2();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        appVersionViewModel = new ViewModelProvider(this).get(AppVersionViewModel.class);
        supervisorVIewModel = new ViewModelProvider(this).get(SupervisorVIewModel.class);
        setBar();
        observerViewModel();
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
        Users.PhoneNumber = "010-4189-2224";
        supervisorVIewModel.GetLeaderType(Users.PhoneNumber);
        backpressed = new BackPressControl(this);
        ButtonEvent();
    }

    private void ButtonEvent(){
        binding.CameraTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraTestActivity.class);
                startActivity(intent);
            }
        });

        binding.testRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RepairTestActivity.class);
                startActivity(intent);
            }
        });
        binding.WriteWorkingReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ASListWritePlaceSelect.class);
                intent.putExtra("Sortation",1);
                startActivity(intent);
            }
        });
        binding.WriteASReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ASListWritePlaceSelect.class);
                intent.putExtra("Sortation",2);
                startActivity(intent);
            }
        });
    }

    public void observerViewModel() {

        //에러발생
        appVersionViewModel.errorMsg.observe(this, models -> {
            if (models != null) {
                Toast.makeText(this, models, Toast.LENGTH_SHORT).show();
                progressOFF2();
            }
        });

        appVersionViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                // 로딩 중이라는 것을 보여준다.
                if (isLoading) {
                    startProgress();
                } else {
                    progressOFF2();
                }
            }
        });

        supervisorVIewModel.supervisorData.observe(this, data -> {
            Users.LeaderType = data.LeaderType;
        });
    }

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

    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            backpressed.onBackPressed();
        }
    };

}