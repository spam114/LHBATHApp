package com.example.basecommon.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.WorkingListViewBinding;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.model.object.WorkListSearch;
import com.example.basecommon.model.object.WorkingList;
import com.example.basecommon.view.adapter.WorkingListAdapter;
import com.example.basecommon.viewModel.SupervisorVIewModel;

import java.util.ArrayList;

public class WorkingListVIew extends BaseActivity{
    WorkingListViewBinding binding;
    SupervisorVIewModel supervisorVIewModel;
    String SearchKeyWord;
    String FromDate;
    String ToDate;
    boolean EnableFlag = true;
    ArrayList<WorkingList> workingListArrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.working_list_view);
        supervisorVIewModel = new ViewModelProvider(this).get(SupervisorVIewModel.class);
        Intent intent = getIntent();
        SearchKeyWord = intent.getStringExtra("SearchKeyWord");
        FromDate = intent.getStringExtra("fromDate");
        ToDate = intent.getStringExtra("toDate");

        WorkListSearch workListSearch = new WorkListSearch();
        workListSearch.SupervisorCode = Users.SupervisorCode;
        workListSearch.SearchKeyWord = SearchKeyWord;
        workListSearch.FromDate = FromDate;
        workListSearch.ToDate = ToDate;

        observerViewModel();
        supervisorVIewModel.GetWorkingList(workListSearch);
        viewSetting();
    }

    private void observerViewModel(){
        supervisorVIewModel.MyWorkingList.observe(this, data->{
            workingListArrayList = new ArrayList<>(data);
            WorkingListAdapter adapter = new WorkingListAdapter(this, R.layout.working_list_view_row,workingListArrayList);
            binding.listView1.setOnItemClickListener(itemClickListener);
            binding.listView1.setAdapter(adapter);
            binding.listView1.setFocusable(false);
            progressOFF2();
        });

        supervisorVIewModel.loading.observe(this, isLoading->{
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

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startProgress();
            Intent intent = new Intent(getApplicationContext(), ActivityWorkEdit.class);
            intent.putExtra("key",workingListArrayList.get(position).SupervisorWoNo);
            intent.putExtra("LocationName",workingListArrayList.get(position).LocationName);
            intent.putExtra("LocationNo",workingListArrayList.get(position).LocationNo);
            intent.putExtra("ContractNo",workingListArrayList.get(position).ContractNo);
            intent.putExtra("SearchKeyWord",SearchKeyWord);
            intent.putExtra("FromDate",FromDate);
            intent.putExtra("ToDate",ToDate);
            intent.putExtra("WorkTypeFullName",workingListArrayList.get(position).WorkTypeCode + ". " + workingListArrayList.get(position).WorkTypeName);
            progressOFF2();
            startActivity(intent);

        }
    };
    private void viewSetting(){
        binding.textViewUserName.setText(Users.UserName);
        binding.btnIlbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ASListWritePlaceSelect.class);
                intent.putExtra("Sortation",1);
                startActivity(intent);
            }
        });
        
        binding.btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WorkingListVIew.this)
                        .setTitle("도움말")
                        .setMessage("작업일보가 보이지 않을 경우 메인화면으로 돌아기서 날짜를 재확인을 하여주세요.")
                        .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressOFF2();
                                        dialog.cancel();
                                    }
                                }
                        ).show();
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
}
