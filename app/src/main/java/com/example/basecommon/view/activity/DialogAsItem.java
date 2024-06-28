package com.example.basecommon.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.DialogAsItemBinding;
import com.example.basecommon.model.object.SupervisorAS;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.viewModel.SupervisorASItemStandardViewModel;
import com.example.basecommon.viewModel.SupervisorAsViewModel;

import java.time.LocalDate;
import java.util.HashMap;

public class DialogAsItem extends BaseActivity{
    public static final int RESULT_ITEM = 0;
    DialogAsItemBinding binding;
    SupervisorAsViewModel supervisorAsViewModel;
    SupervisorASItemStandardViewModel supervisorASItemStandardViewModel;
    String LocationName;
    String SupervisorWoNo;
    String Dong;
    String SupervisorAsNo;
    final int RequestCode = 0;
    int firstNumber;
    int SecondNumber;
    int ThirdNumber;
    String construction;
    String contractNo;
    String constructionContent;
    SupervisorAS supervisorAS;
    private HashMap<Integer,String> ASItemNameMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this,R.layout.dialog_as_item);

        supervisorAS = new SupervisorAS();

        Intent GetIntent = getIntent();
        LocationName = GetIntent.getStringExtra("LocationName");
        SupervisorWoNo = GetIntent.getStringExtra("SupervisorWoNo");
        Dong = GetIntent.getStringExtra("Dong");
        contractNo = GetIntent.getStringExtra("ContractNo");
        SupervisorAsNo = GetIntent.getStringExtra("SupervisorAsNo");

        ViewSetting();
        TouchEvent();

        supervisorAsViewModel = new ViewModelProvider(this).get(SupervisorAsViewModel.class);
        supervisorASItemStandardViewModel = new ViewModelProvider(this).get(SupervisorASItemStandardViewModel.class);
        observerViewModel();
        supervisorASItemStandardViewModel.GetSupervisorASItemStandardParent();
    }

    @SuppressLint("NewApi")
    private void ViewSetting(){
        binding.textViewRealDate.setText(LocalDate.now().toString());
        binding.edtDong.setText(Dong);
        if(SupervisorAsNo == null){
            binding.btnOK.setText("추가하기");
        }else{
            binding.btnOK.setText("저장하기");
        }

        if(SupervisorAsNo != null){
            binding.txtSuperVisorAsNo.setText(SupervisorAsNo);
        }

    }

    private void TouchEvent(){
        binding.txtAsItemType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent treeIntent = new Intent(getApplicationContext(), AsItemTreeActivity.class);
                startActivityForResult(treeIntent,RequestCode);
            }
        });
        
        //저장 및 추가 버튼
        binding.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SupervisorAsNo == null){
                    new AlertDialog.Builder(DialogAsItem.this)
                            .setTitle("AS 기록 생성")
                            .setMessage("AS 기록을 추가하시겠습니까?")
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            saveData();
                                            if(SupervisorWoNo != null){
                                                supervisorAS.SupervisorWoNo = SupervisorWoNo;
                                            }else{
                                                supervisorAS.SupervisorWoNo = "";
                                            }
                                            supervisorAsViewModel.InsertSupervisorAS(supervisorAS);
                                            Toast.makeText(getApplicationContext(),"등록되었습니다.",Toast.LENGTH_LONG).show();
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
                    new AlertDialog.Builder(DialogAsItem.this)
                            .setTitle("AS 기록 저장")
                            .setMessage("AS 기록을 저장하시겠습니까?")
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            saveData();
                                            supervisorAS.SupervisorASNo = SupervisorAsNo;
                                            supervisorAsViewModel.UpdateSupervisorAS(supervisorAS);
                                            Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_LONG).show();
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
    
        // 삭제 버튼
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SupervisorAsNo != null){
                    new AlertDialog.Builder(DialogAsItem.this)
                            .setTitle("AS 기록 삭제")
                            .setMessage("AS 기록을 삭제하시겠습니까?")
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            supervisorAS.SupervisorASNo = SupervisorAsNo;
                                            supervisorAsViewModel.DeleteSupervisorAS(supervisorAS);
                                            Intent intent = new Intent(getApplicationContext(), ASListView.class);
                                            intent.putExtra("LocationName", LocationName);
                                            intent.putExtra("SupervisorWoNo", SupervisorWoNo);
                                            intent.putExtra("Dong",Dong);
                                            intent.putExtra("ContractNo",contractNo);
                                            startActivity(intent);
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
                    Toast.makeText(getApplicationContext(),"먼저 AS 작업을 등록을 진행하여 주세요.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveData(){
        supervisorAS.Dong = binding.edtDong.getText().toString();
        supervisorAS.Ho = binding.edtHo.getText().toString();
        supervisorAS.HoLocation = binding.edtLocation.getText().toString();
        supervisorAS.ItemType = firstNumber;
        supervisorAS.Item = SecondNumber;
        supervisorAS.ItemSpecs = binding.edtPartSpec.getText().toString();
        supervisorAS.Quantity = Integer.parseInt(binding.edtQty.getText().toString());
        supervisorAS.Reason = ThirdNumber;
        supervisorAS.ASType = Integer.toString(ThirdNumber);
        supervisorAS.Remark = binding.edtComplain.getText().toString();
        supervisorAS.Actions = binding.edtAction.getText().toString();
        supervisorAS.ActionEmployee = binding.edtActionEmployee.getText().toString();
        supervisorAS.InsertTime = binding.textViewRealDate.getText().toString();
        supervisorAS.InsertUser = Users.SupervisorCode;
        supervisorAS.SupervisorCode = Integer.toString(Users.SupervisorCode);
        supervisorAS.ContractNo = contractNo;
    }

    private void observerViewModel(){
        supervisorAsViewModel.SingleSupervisorAS.observe(this, data->{
            SupervisorAsNo = data.SupervisorASNo;
            binding.textViewRealDate.setText(data.InsertTime.substring(0,10));
            binding.edtDong.setText(data.Dong);
            binding.edtHo.setText(data.Ho);
            binding.edtLocation.setText(data.HoLocation);
            binding.txtAsItemType.setText(ASItemNameMap.get(data.ItemType) + " -> " + ASItemNameMap.get(data.Item) + " -> " + ASItemNameMap.get(data.Reason));
            binding.edtPartSpec.setText(data.ItemSpecs);
            binding.edtQty.setText(Integer.toString(data.Quantity));
            binding.edtComplain.setText(data.Remark);
            binding.edtAction.setText(data.Actions);
            binding.edtActionEmployee.setText(data.ActionEmployee);
            firstNumber = data.ItemType;
            SecondNumber = data.Item;
            ThirdNumber = data.Reason;
            binding.btnOK.setText("저장하기");
            binding.txtSuperVisorAsNo.setText(SupervisorAsNo);
        });

        supervisorASItemStandardViewModel.SupervisorASItemStandards.observe(this, data ->{
            ASItemNameMap = new HashMap<>();

            for(SupervisorASItemStandard item : data){
                ASItemNameMap.put(item.StandardNo, item.Name);
            }

            if(this.SupervisorAsNo != null){
                supervisorAsViewModel.GetSupervisorAsBySupervisorAsNo(SupervisorAsNo);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_ITEM && resultCode == RESULT_OK && null != data){
            construction = data.getStringExtra("construction");
            firstNumber = data.getIntExtra("FirstNumber",0);
            SecondNumber = data.getIntExtra("SecondNumber",0);
            ThirdNumber = data.getIntExtra("ThirdNumber",0);
            if(data.getStringExtra("constructionContent") != null){
                binding.txtAsItemType.setText(construction + " -> " + data.getStringExtra("constructionContent"));
            }else{
                binding.txtAsItemType.setText(construction);
            }
        }
    }
}
