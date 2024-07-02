package com.example.basecommon.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.ActivityWorkeditBinding;
import com.example.basecommon.model.object.Contract;
import com.example.basecommon.model.object.SupervisorWorder;
import com.example.basecommon.model.object.SupervisorWorkType;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.viewModel.SupervisorVIewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityWorkEdit extends BaseActivity{
    public static final int RESULT_TEXTVIEW1 = 0;
    public static final int RESULT_TEXTVIEW2 = 1;
    ActivityWorkeditBinding binding;
    private String LocationName;
    private int mHour1;
    private int mMinute1;
    private int mHour2;
    private int mMinute2;
    SupervisorVIewModel supervisorVIewModel;
    private SupervisorWorder supervisorWorder;
    private Map<String, Integer> NameMap;
    private List<String> WorkName;
    private String key = "";
    private String WorkTypeFullName;
    private String ContractNo;
    private String FromDate;
    private String ToDate;
    private String SearchKeyWord;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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

    private void init(){
        NameMap = new HashMap<>();
        supervisorWorder = new SupervisorWorder();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workedit);
        supervisorVIewModel = new ViewModelProvider(this).get(SupervisorVIewModel.class);
        observerViewModel();
        supervisorVIewModel.GetWorkTypeByBusinessClassCode(Users.BusinessClassCode);
        viewSetting();
    }

    private void observerViewModel(){
        // 로딩바
        supervisorVIewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                // 로딩 중이라는 것을 보여준다.
                if (isLoading) {
                    startProgress();
                } else {
                    progressOFF2();
                }
            }
        });

        supervisorVIewModel.workList.observe(this, data->{
            WorkName = new ArrayList<>();

            for(SupervisorWorkType supervisorWorkType : data){
                WorkName.add(supervisorWorkType.WorkTypeCode + ". " + supervisorWorkType.WorkTypeName);
                NameMap.put(supervisorWorkType.WorkTypeCode + ". " + supervisorWorkType.WorkTypeName, supervisorWorkType.WorkTypeCode);
            }

            ArrayAdapter adapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item,WorkName);

            binding.spinnerWorkType.setAdapter(adapter);

            if(key != null){
                supervisorVIewModel.GetSupervisorWorderSingle(key);
            }
        });

        supervisorVIewModel.supervisorWorderData.observe(this, data->{
            this.key = data.SupervisorWoNo;
            binding.textViewManageNo.setText(this.key);
            binding.btnDelete.setVisibility(View.VISIBLE);
            binding.btnASItem.setVisibility(View.VISIBLE);
            binding.btnImageControl.setVisibility(View.VISIBLE);
        });

        supervisorVIewModel.SingleSupervisorWorder.observe(this, data->{
            binding.textViewRealDate.setText(data.WorkDate);
            binding.textViewTime1.setText(data.StartTime);
            binding.textViewTime2.setText(data.EndTime);
            binding.spinnerWorkType.setSelection(WorkName.indexOf(WorkTypeFullName));
            binding.textViewDong.setText(data.Dong);
            binding.textView1.setText(data.WorkDescription1);
            binding.textView2.setText(data.WorkDescription2);
            binding.btnNext.setText("저장하기");
        });
    }

    @SuppressLint("NewApi")
    private void viewSetting(){
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        if(supervisorWorder.SupervisorWoNo != null){
            ContractNo  = intent.getStringExtra("ContractNo");
            FromDate = intent.getStringExtra("FromDate");
            ToDate = intent.getStringExtra("ToDate");
            SearchKeyWord = intent.getStringExtra("SearchKeyWord");
        }

        if(key == null){
            binding.textViewManageNo.setText("작업일보를 먼저 등록을 해주세요.");
            binding.btnDelete.setVisibility(View.INVISIBLE);
            binding.btnASItem.setVisibility(View.INVISIBLE);
            binding.btnImageControl.setVisibility(View.INVISIBLE);

        }else{
            binding.textViewManageNo.setText(key);
            binding.btnDelete.setVisibility(View.VISIBLE);
            binding.btnASItem.setVisibility(View.VISIBLE);
            binding.btnImageControl.setVisibility(View.VISIBLE);
            WorkTypeFullName = intent.getStringExtra("WorkTypeFullName");
            binding.btnNext.setText("저장하기");
        }

        this.LocationName = intent.getStringExtra("LocationName");

        binding.textViewCustomer.setText(this.LocationName);
        binding.btnNext.setText("작업일보 생성");
        binding.textViewRealDate.setText(LocalDate.now().toString().substring(0,10));
        binding.textViewTime1.setText("오전 9:00");
        binding.textViewTime2.setText("오후 5:00");
        supervisorWorder.ContractNo = intent.getStringExtra("ContractNo");
        binding.textViewTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimeDialog(R.id.textViewTime1);
            }
        });
        binding.textViewTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimeDialog(R.id.textViewTime2);
            }
        });

        binding.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallEditTextActivity("작업내용",v,RESULT_TEXTVIEW1);
                Log.i(this.toString(),"작업내용 클릭");
            }
        });
        binding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallEditTextActivity("특이사항",v,RESULT_TEXTVIEW2);
                Log.i(this.toString(),"특이사항 클릭");
            }
        });

        // 작업일보 Insert 및 Update
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==null){
                    new AlertDialog.Builder(ActivityWorkEdit.this)
                            .setTitle("작업일보 생성")
                            .setMessage("작업일보를 생성하시겠습니까?")
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SaveData();
                                            supervisorVIewModel.SetSupervisorWorderData(supervisorWorder);
                                        }
                                    })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressOFF2();
                                    dialog.cancel();
                                }
                            }).show();
                }else{
                    new AlertDialog.Builder(ActivityWorkEdit.this)
                            .setTitle("저장하기")
                            .setMessage("저장하시겠습니까?")
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            supervisorWorder.SupervisorWoNo = key;
                                            SaveData();
                                            supervisorVIewModel.UpdateSupervisorWorderData(supervisorWorder);
                                        }
                                    })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressOFF2();
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==null){
                    Toast.makeText(getApplicationContext(),"먼저 작업일보를 등록하여 주세요",Toast.LENGTH_LONG);
                    progressOFF2();
                    return;
                }

                new AlertDialog.Builder(ActivityWorkEdit.this)
                        .setTitle("삭제하기")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        supervisorWorder.SupervisorWoNo = key;
                                        supervisorVIewModel.DeleteSupervisorWorderData(supervisorWorder);
                                        Intent BackIntent = new Intent(getApplicationContext(), WorkingListVIew.class);
                                        BackIntent.putExtra("SearchKeyWord",SearchKeyWord);
                                        BackIntent.putExtra("fromDate",FromDate);
                                        BackIntent.putExtra("toDate",ToDate);
                                        startActivity(BackIntent);
                                    }
                                })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressOFF2();
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        binding.btnImageControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==null){
                    Toast.makeText(getApplicationContext(),"먼저 작업일보를 등록하여 주세요",Toast.LENGTH_LONG);
                    progressOFF2();
                    return;
                }else{
                    Intent PhotoIntent = new Intent(getApplicationContext(), ListImage.class);
                    PhotoIntent.putExtra("key",key);
                    startActivity(PhotoIntent);
                }
            }
        });
        binding.spinnerWorkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supervisorWorder.WorkTypeCode = NameMap.get(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // AS 추가
        binding.btnASItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==null){
                    Toast.makeText(getApplicationContext(),"먼저 작업일보를 등록하여 주세요",Toast.LENGTH_LONG);
                    progressOFF2();
                    return;
                }else{
                    Intent i = new Intent(getApplicationContext(), ASListView.class);
                    i.putExtra("LocationName",binding.textViewCustomer.getText().toString());
                    i.putExtra("SupervisorWoNo",key);
                    i.putExtra("ContractNo",ContractNo);
                    i.putExtra("Dong", binding.textViewDong.getText().toString());
                    startActivity(i);
                }
            }
        });

        binding.btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==null){
                    Toast.makeText(getApplicationContext(),"먼저 작업일보를 등록하여 주세요",Toast.LENGTH_LONG);
                    progressOFF2();
                    return;
                }else{
                    Intent i = new Intent(getApplicationContext(), ASListView.class);
                    i.putExtra("LocationName",binding.textViewCustomer.getText().toString());
                    i.putExtra("SupervisorWoNo",key);
                    i.putExtra("ContractNo",ContractNo);
                    i.putExtra("Dong", binding.textViewDong.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    public void SaveData(){
        supervisorWorder.SupervisorCode = Users.SupervisorCode;
        supervisorWorder.WorkDate = binding.textViewRealDate.getText().toString();
        supervisorWorder.StartTime = binding.textViewRealDate.getText().toString() + " " + binding.textViewTime1.getText().toString();
        supervisorWorder.EndTime = binding.textViewRealDate.getText().toString() + " " + binding.textViewTime2.getText().toString();
        supervisorWorder.WorkDescription1 = binding.textView1.getText().toString();
        supervisorWorder.WorkDescription2 = binding.textView2.getText().toString();
        supervisorWorder.WorkDescription3 = "";
        supervisorWorder.BusinessClassCode = Users.BusinessClassCode;
        supervisorWorder.Dong = binding.textViewDong.getText().toString();
    }

    private void ShowTimeDialog(int viewId){
        if (viewId == R.id.textViewTime1)
            new TimePickerDialog(this, mTimeSetListener1, mHour1, mMinute1, true).show();
        else
            new TimePickerDialog(this, mTimeSetListener2, mHour2, mMinute2, true).show();

        progressOFF2();
    }

    TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour1 = hourOfDay;
            mMinute1 = minute;

            Date date = new Date();
            date.setHours(mHour1);
            date.setMinutes(mMinute1);
            String data = new SimpleDateFormat("HH:mm").format(date);
            binding.textViewTime1.setText(data);
        }
    };

    TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour2 = hourOfDay;
            mMinute2 = minute;

            Date date = new Date();
            date.setHours(mHour2);
            date.setMinutes(mMinute2);
            String data = new SimpleDateFormat("HH:mm").format(date);
            binding.textViewTime2.setText(data);
        }
    };

    private void CallEditTextActivity(String title, View view, int resultID){
        TextView textView = (TextView) view;
        String Content = textView.getText().toString();
        Intent intent = new Intent(this, EditTextActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",Content);
        startActivityForResult(intent,resultID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_TEXTVIEW1 && resultCode == RESULT_OK && null != data){
            Log.i("onActivityResult","결과 받아옴");
            String content = data.getStringExtra("content");
            binding.textView1.setText(content);
        } else if (requestCode == RESULT_TEXTVIEW2 && resultCode == RESULT_OK && null != data) {
            Log.i("onActivityResult","결과 받아옴");
            String content = data.getStringExtra("content");
            binding.textView2.setText(content);
        }
    }
}
