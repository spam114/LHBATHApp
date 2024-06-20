package com.example.basecommon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.basecommon.R;
import com.example.basecommon.databinding.ActivityAsItemTreeBinding;
import com.example.basecommon.model.object.SupervisorASItemStandard;
import com.example.basecommon.model.object.Users;
import com.example.basecommon.viewModel.SupervisorASItemStandardViewModel;
import com.example.treeview.model.TreeNode;
import com.example.treeview.view.AndroidTreeView;
//import com.example.treeview.model.TreeNode;
//import com.example.treeview.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AsItemTreeActivity extends BaseActivity{
    ActivityAsItemTreeBinding binding;
    SupervisorASItemStandardViewModel supervisorASItemStandardViewModel;
    private HashMap <Integer, SupervisorASItemStandard> ParentMap = new HashMap<>();
    private HashMap <Integer, List<SupervisorASItemStandard>> FirstChild = new HashMap<>();
    private HashMap <Integer, List<SupervisorASItemStandard>> SecondChild = new HashMap<>();
    private List<SupervisorASItemStandard> CommonList = new ArrayList<>();
    private String TitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_as_item_tree);
        setBar();
        init();
    }

    private void init(){
        Intent intent = getIntent();
        TitleName = intent.getStringExtra("LocationName") + " → A/S추가";
        setBarTitle(TitleName);
        supervisorASItemStandardViewModel = new ViewModelProvider(this).get(SupervisorASItemStandardViewModel.class);
        observerViewModel();
        supervisorASItemStandardViewModel.GetSupervisorASItemStandardParent();
    }

    private void TreeViewSetting(){
        LinearLayout linearLayout = findViewById(R.id.layout);
        TreeNode root = TreeNode.root();
        Map<Integer,TreeNode> ParentTree = new HashMap<>();

        for(SupervisorASItemStandard item : ParentMap.values()){
            ParentNode.IconTreeItem parentItem = new ParentNode.IconTreeItem();
            TreeNode Parent = new TreeNode(parentItem).setViewHolder(new ParentNode(this, item.Name));
            ParentTree.put(item.StandardNo, Parent);

            if(item.Name.equals("기타")){
                Parent.setClickListener(new TreeNode.TreeNodeClickListener() {
                    @Override
                    public void onClick(TreeNode node, Object value) {
                        Intent intent = new Intent(getApplicationContext(),CameraTestActivity.class);
                        intent.putExtra("titleName",TitleName + " → 기타");
                        startActivity(intent);
                    }
                });
            }
        }


        for(List<SupervisorASItemStandard> Values : FirstChild.values()){
            for(SupervisorASItemStandard first :Values){

                // 2차 기구류 전용
                if(first.ParentNo == 3){
                    TreeNode ParentNode = ParentTree.get(first.ParentNo);
                    MyHolder.IconTreeItem FirstItem = new MyHolder.IconTreeItem();
                    TreeNode ChildFirst = new TreeNode(FirstItem).setViewHolder(new MyHolder(this, first.Name));

                    for(SupervisorASItemStandard item : CommonList){
                        SecondHolder.IconTreeItem SecondChildItem = new SecondHolder.IconTreeItem();
                        TreeNode ChildSecond = new TreeNode(SecondChildItem).setViewHolder(new SecondHolder(this, item.Name));
                        ChildSecond.setClickListener(new TreeNode.TreeNodeClickListener() {
                            @Override
                            public void onClick(TreeNode node, Object value) {
                                Intent intent = new Intent(getApplicationContext(),CameraTestActivity.class);
                                intent.putExtra("titleName",
                                            TitleName + " -> "
                                                + ParentMap.get(first.ParentNo).Name + " -> "
                                                + first.Name  + " -> " + item.Name);
                                intent.putExtra("construction",ParentMap.get(first.ParentNo).Name);
                                if(first.Name.equals("기타")){
                                    if(item.Name.equals("기타")){
                                        intent.putExtra("constructionContent","");
                                    }else {
                                        intent.putExtra("constructionContent", item.Name);
                                    }
                                }else{
                                    intent.putExtra("constructionContent",item.Name + " " + first.Name);
                                }
                                startActivity(intent);
                            }
                        });
                        ChildFirst.addChild(ChildSecond);
                    }
                    ParentNode.addChildren(ChildFirst);
                    continue;
                }

                TreeNode ParentNode = ParentTree.get(first.ParentNo);
                MyHolder.IconTreeItem FirstItem = new MyHolder.IconTreeItem();
                TreeNode ChildFirst = new TreeNode(FirstItem).setViewHolder(new MyHolder(this, first.Name));

                // 기타 처리
                if(first.Name.equals("기타")){
                    ChildFirst.setClickListener(new TreeNode.TreeNodeClickListener() {
                        @Override
                        public void onClick(TreeNode node, Object value) {
                            Intent intent = new Intent(getApplicationContext(),CameraTestActivity.class);
                            intent.putExtra("titleName",
                                    TitleName + " -> "
                                            + ParentMap.get(first.ParentNo).Name + " -> "
                                            + first.Name);
                            intent.putExtra("construction",ParentMap.get(first.ParentNo).Name);
                            intent.putExtra("constructionContent","");
                            startActivity(intent);
                        }
                    });
                    ParentNode.addChildren(ChildFirst);
                    continue;
                }

                List<TreeNode> SecondChildren = new ArrayList<>();

                // 나머지 항목들 처리
                for(SupervisorASItemStandard second : SecondChild.get(first.StandardNo)){
                    SecondHolder.IconTreeItem SecondChildItem = new SecondHolder.IconTreeItem();
                    TreeNode ChildSecond = new TreeNode(SecondChildItem).setViewHolder(new SecondHolder(this, second.Name));
                    ChildSecond.setClickListener(new TreeNode.TreeNodeClickListener() {
                        @Override
                        public void onClick(TreeNode node, Object value) {
                            Intent intent = new Intent(getApplicationContext(),CameraTestActivity.class);
                            intent.putExtra("titleName",
                                        TitleName + " -> "
                                            + ParentMap.get(first.ParentNo).Name + " -> "
                                            + first.Name  + " -> " + second.Name);
                            intent.putExtra("construction",ParentMap.get(first.ParentNo).Name);
                            if(second.Name.equals("기타")){
                                intent.putExtra("constructionContent",first.Name);
                            }else{
                                intent.putExtra("constructionContent",first.Name  + " " + second.Name);
                            }
                            startActivity(intent);
                        }
                    });
                    SecondChildren.add(ChildSecond);
                }

                ChildFirst.addChildren(SecondChildren);
                ParentNode.addChildren(ChildFirst);
            }
        }

        root.addChildren(ParentTree.values());

        AndroidTreeView treeView = new AndroidTreeView(this,root);
        treeView.setDefaultAnimation(true);
        linearLayout.addView(treeView.getView());
    }

    private void observerViewModel(){
        supervisorASItemStandardViewModel.SupervisorASItemStandards.observe(this, data-> {
            if(data != null){
                for(SupervisorASItemStandard item : data){
                    if(item.ParentNo == 0){
                        ParentMap.put(item.StandardNo,item);
                    }else if(item.ParentNo == (-1)){
                        CommonList.add(item);
                    }else{
                        if(ParentMap.containsKey(item.ParentNo)){
                            if(FirstChild.containsKey(item.ParentNo)){
                                List<SupervisorASItemStandard> list = FirstChild.get(item.ParentNo);
                                list.add(item);
                                FirstChild.put(item.ParentNo,list);
                            }else{
                                List<SupervisorASItemStandard> list = new ArrayList<>();
                                list.add(item);
                                FirstChild.put(item.ParentNo,list);
                            }
                        }else{
                            if(SecondChild.containsKey(item.ParentNo)){
                                List<SupervisorASItemStandard> list = SecondChild.get(item.ParentNo);
                                list.add(item);
                                SecondChild.put(item.ParentNo,list);
                            }else{
                                List<SupervisorASItemStandard> list = new ArrayList<>();
                                list.add(item);
                                SecondChild.put(item.ParentNo,list);
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(this, Users.Language == 0 ? "서버 연결 오류" : "Server connection error", Toast.LENGTH_SHORT).show();
                finish();
            }
            TreeViewSetting();
            System.out.println("---");
        });
    }
}

class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem> {
    String AsName;

    public MyHolder(Context context, String AsName){
        super(context);
        this.AsName = AsName;
    }

    @Override
    public View createNodeView(TreeNode node, MyHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_profile_node,null,false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(this.AsName);

        return view;
    }

    public static class IconTreeItem{
        public int icon;
        public String text;
    }
}

class SecondHolder extends TreeNode.BaseNodeViewHolder<SecondHolder.IconTreeItem> {
    String AsName;

    public SecondHolder(Context context, String AsName){
        super(context);
        this.AsName = AsName;
    }
    @Override
    public View createNodeView(TreeNode node, SecondHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_profile_second_node,null,false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(this.AsName);

        return view;
    }

    public static class IconTreeItem{
        public int icon;
        public String text;
    }
}

class ParentNode extends TreeNode.BaseNodeViewHolder<ParentNode.IconTreeItem>{
    String ASName;
    public ParentNode(Context context, String LocationName){
        super(context);
        this.ASName = LocationName;
    }

    @Override
    public View createNodeView(TreeNode node, ParentNode.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_parent_node,null,false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(this.ASName);

        return view;
    }

    public static class IconTreeItem{
        public int icon;
        public String text;
    }
}
