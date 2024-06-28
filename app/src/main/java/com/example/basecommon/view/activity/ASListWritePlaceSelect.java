package com.example.basecommon.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.basecommon.R;
import com.example.basecommon.databinding.AsListWritePlaceSelectBinding;
import com.example.basecommon.model.object.CustomLocation;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.view.adapter.LocationAdapter;
import com.example.basecommon.viewModel.LocationViewModel;
import com.example.treeview.model.TreeNode;
import com.example.treeview.view.AndroidTreeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASListWritePlaceSelect extends BaseActivity{
    AsListWritePlaceSelectBinding binding;
    LocationViewModel locationViewModel;
    private Map<String, List<CustomLocation>> CustomLocationMap;
    private int SortCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        binding = DataBindingUtil.setContentView(this, R.layout.as_list_write_place_select);
        Intent intent = getIntent();
        System.out.println(Users.BusinessClassCode);
        this.SortCode = intent.getIntExtra("Sortation",0);
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        CustomLocationMap = new HashMap<>();
        observerViewModel();
        locationViewModel.GetCustomLocationData();
        ButtonAction();
        otherAction();
    }

    private void ButtonAction(){
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomLocationMap = new HashMap<>();
                locationViewModel.GetSearchCustomLocationData(binding.edtSearchWord.getText().toString());
            }
        });
    }

    private void otherAction(){
        binding.edtSearchWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.edtSearchWord.getWindowToken(), 0);

                    CustomLocationMap = new HashMap<>();
                    locationViewModel.GetSearchCustomLocationData(binding.edtSearchWord.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    public void observerViewModel(){
        locationViewModel.CustomLocationList.observe(this, data -> {
            for(CustomLocation location : data){
                if(CustomLocationMap.containsKey(location.CustomerName)){
                    List<CustomLocation> locationList = CustomLocationMap.get(location.CustomerName);
                    // 중복장소 구별 용
                    if(locationList.contains(location.LocationName)){
                        location.LocationName += "(" +location.ContractNo + ")";
                        locationList.add(location);
                    }else {
                        locationList.add(location);
                    }
                    CustomLocationMap.put(location.CustomerName,locationList);
                }else{
                    List<CustomLocation> locationList = new ArrayList<>();
                    locationList.add(location);
                    CustomLocationMap.put(location.CustomerName,locationList);
                }
            }
            settingTree();
        });

        locationViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    startProgress();
                } else {
                    progressOFF2();
                }
            }
        });
    }

    private void settingTree(){
        startProgress();
        LinearLayout linearLayout = findViewById(R.id.layout);
        linearLayout.removeAllViews();
        TreeNode root = TreeNode.root();
        List<TreeNode> ParentList = new ArrayList<>();
        List<String> KeyList = new ArrayList<>(CustomLocationMap.keySet());
        KeyList.sort((s1, s2) -> s1.replace("(주)","").replace("(재)","")
                .compareTo(s2.replace("(주)","").replace("(재)","")));

        for(String key : KeyList) {
            ParentNode.IconTreeItem parentItem = new ParentNode.IconTreeItem();
            TreeNode Parent = new TreeNode(parentItem).setViewHolder(new ParentNode(this, key));

            for(CustomLocation customLocation : CustomLocationMap.get(key)){
                MyHolder.IconTreeItem ChildItem = new MyHolder.IconTreeItem();
                TreeNode Child = new TreeNode(ChildItem).setViewHolder(new MyHolder(this, customLocation.LocationName));

                Child.setClickListener(new TreeNode.TreeNodeClickListener() {
                    @Override
                    public void onClick(TreeNode node, Object value) {
                        Intent intent = new Intent();
                        switch (SortCode){
                            case 1:
                                intent = new Intent(getApplicationContext(),ActivityWorkEdit.class);
                                break;
                            case 2:
                                intent = new Intent(getApplicationContext(),FieldOverview.class);
                                break;
                        }
                        intent.putExtra("LocationNo",customLocation.LocationNo);
                        intent.putExtra("LocationName",customLocation.LocationName);
                        intent.putExtra("ContractNo",customLocation.ContractNo);
                        startActivity(intent);
                    }
                });

                Parent.addChild(Child);
            }

            ParentList.add(Parent);
        }

        root.addChildren(ParentList);
        AndroidTreeView treeView = new AndroidTreeView(this, root);
        linearLayout.addView(treeView.getView());
        progressOFF2();
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


