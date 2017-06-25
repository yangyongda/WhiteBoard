package com.fjsd.yyd.whiteboard.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.fjsd.yyd.whiteboard.R;
import com.fjsd.yyd.whiteboard.adapter.RecycleAdapter;
import com.fjsd.yyd.whiteboard.util.AppContextUtil;
import com.fjsd.yyd.whiteboard.util.FileUtil;
import com.fjsd.yyd.whiteboard.util.OperationUtils;
import com.fjsd.yyd.whiteboard.util.SdCardStatus;
import com.fjsd.yyd.whiteboard.util.StoreUtil;

import java.io.File;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;
    private ImageView mIvAdd;
    private RecycleAdapter mAdapter;
    private ArrayList<String> filenames;
    private ArrayList<String> filepaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContextUtil.init(this);              //初始化全局变量
        SdCardStatus.init(StoreUtil.CACHE_DIR);  //初始化默认存储地址
        OperationUtils.getInstance().init();     //白板操作公共类
        setContentView(R.layout.activity_main);
        loadData();
        initView();
    }

    private void initView() {
        mRv = (RecyclerView)findViewById(R.id.rv_wb);
        mIvAdd = (ImageView)findViewById(R.id.iv_wb_add) ;
        mRv.setLayoutManager(new LinearLayoutManager(this));
        //点击adapter则选择以前的板书继续编辑
        mAdapter = new RecycleAdapter(this,filenames,filepaths);
        mRv.setAdapter(mAdapter);
        //按下添加按钮则新建一个新的板书
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WhiteBoardActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }

    private void loadData() {
        File folder = new File(StoreUtil.getWbPath());
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final File[] files = folder.listFiles();
        if (files!= null && files.length > 0) {
            filenames = new ArrayList<String>();
            filepaths = new ArrayList<String>();
            for (File f : files) {
                filenames.add(FileUtil.getFileName(f));
                filepaths.add(f.getAbsolutePath());
            }
        }
    }
}
