package com.example.basecommon.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends BaseActivity {
    int fromYear = 0;
    int fromMonth = 0;
    int fromDay = 0;
    String searchDateType = "요청일";
    int toYear = 0;
    int toMonth = 0;
    int toDay = 0;
    ActivityMainBinding binding;
    AppVersionViewModel appVersionViewModel;
    BackPressControl backpressed;
    DrawerLayout drawerLayout;
    SupervisorVIewModel supervisorVIewModel;
    TextView txtDate;
    DatePicker upDatePicker;
    DatePicker downDatePicker;
    Spinner searchTypeSpinner;
    Button btnFromDate;
    Button btnToDate;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        appVersionViewModel.GetNoticeData2();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        binding.btnFromDate.setText(LocalDate.now().toString());
        binding.btnToDate.setText(LocalDate.now().toString());
        Calendar cal = new GregorianCalendar();
        binding.txtDate.setText("작업일");
        fromYear = cal.get(Calendar.YEAR);
        fromMonth = cal.get(Calendar.MONTH);
        fromDay = cal.get(Calendar.DATE);

        toYear = fromYear;
        toMonth = fromMonth;
        toDay = fromDay;
        ButtonEvent();
    }

    private void ButtonEvent(){
        binding.testRepair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RepairTestActivity.class);
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

        binding.ReadWorkingReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkingListVIew.class);
                intent.putExtra("SearchKeyWord",binding.txtDate.getText().toString());
                intent.putExtra("fromDate",binding.btnFromDate.getText().toString());
                intent.putExtra("toDate",binding.btnToDate.getText().toString());
                startActivity(intent);
            }
        });

        binding.btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_double_date_picker,null);
                Spinner searchSpinner = dialogView.findViewById(R.id.spinnerSearchType);
                searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.RED);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fromYear = upDatePicker.getYear();
                        fromMonth = upDatePicker.getMonth();
                        fromDay = upDatePicker.getDayOfMonth();

                        toYear = downDatePicker.getYear();
                        toMonth = downDatePicker.getMonth();
                        toDay = downDatePicker.getDayOfMonth();
                        int selectedNum = searchTypeSpinner.getSelectedItemPosition();
                        if (selectedNum == 0)
                            searchDateType = "요청일";
                        else if (selectedNum == 1)
                            searchDateType = "희망일";
                        else
                            searchDateType = "작업일";

                        String strReq = searchDateType;
                        String strFromDate = fromYear + "-" + (fromMonth + 1) + "-" + fromDay;
                        String strToDate = toYear + "-" + (toMonth + 1) + "-" + toDay;
                        binding.txtDate.setText(strReq);
                        binding.btnFromDate.setText(strFromDate);
                        binding.btnToDate.setText(strToDate);
                        progressOFF2();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressOFF2();
                    }
                });

                AlertDialog dialog = builder.create();
                //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
                dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정
                //Dialog 보이기

                dialog.show();

                upDatePicker = dialog.findViewById(R.id.start_date);
                downDatePicker = dialog.findViewById(R.id.end_date);
                searchTypeSpinner = dialog.findViewById(R.id.spinnerSearchType);
                upDatePicker.updateDate(fromYear, fromMonth, fromDay);
                downDatePicker.updateDate(toYear, toMonth, toDay);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                String[] typeList = {"요청일", "희망일", "작업일"};
                int searchNum;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        typeList);
                searchTypeSpinner.setAdapter(adapter);

                if (searchDateType.equals("요청일")) {
                    searchNum = 0; //요청
                } else if (searchDateType.equals("희망일")) {
                    searchNum = 1; //희망
                } else {
                    searchNum = 2; //작업
                }
                searchTypeSpinner.setSelection(searchNum);
            }
        });

        binding.btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_double_date_picker,null);
                Spinner searchSpinner = dialogView.findViewById(R.id.spinnerSearchType);
                searchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.RED);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fromYear = upDatePicker.getYear();
                        fromMonth = upDatePicker.getMonth();
                        fromDay = upDatePicker.getDayOfMonth();

                        toYear = downDatePicker.getYear();
                        toMonth = downDatePicker.getMonth();
                        toDay = downDatePicker.getDayOfMonth();
                        int selectedNum = searchTypeSpinner.getSelectedItemPosition();
                        if (selectedNum == 0)
                            searchDateType = "요청일";
                        else if (selectedNum == 1)
                            searchDateType = "희망일";
                        else
                            searchDateType = "작업일";

                        String strReq = searchDateType;
                        String strFromDate = fromYear + "-" + (fromMonth + 1) + "-" + fromDay;
                        String strToDate = toYear + "-" + (toMonth + 1) + "-" + toDay;
                        binding.txtDate.setText(strReq);
                        binding.btnFromDate.setText(strFromDate);
                        binding.btnToDate.setText(strToDate);
                        progressOFF2();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressOFF2();
                    }
                });

                AlertDialog dialog = builder.create();
                //Dialog의 바깥쪽을 터치했을 때 Dialog를 없앨지 설정
                dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정
                //Dialog 보이기

                dialog.show();

                upDatePicker = dialog.findViewById(R.id.start_date);
                downDatePicker = dialog.findViewById(R.id.end_date);
                searchTypeSpinner = dialog.findViewById(R.id.spinnerSearchType);
                upDatePicker.updateDate(fromYear, fromMonth, fromDay);
                downDatePicker.updateDate(toYear, toMonth, toDay);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                String[] typeList = {"요청일", "희망일", "작업일"};
                int searchNum;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        typeList);
                searchTypeSpinner.setAdapter(adapter);

                if (searchDateType.equals("요청일")) {
                    searchNum = 0; //요청
                } else if (searchDateType.equals("희망일")) {
                    searchNum = 1; //희망
                } else {
                    searchNum = 2; //작업
                }
                searchTypeSpinner.setSelection(searchNum);
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

        supervisorVIewModel.loading.observe(this,isLoading->{
            if (isLoading != null) {
                // 로딩 중이라는 것을 보여준다.
                if (isLoading) {
                    startProgress();
                } else {
                    progressOFF2();
                }
            }
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