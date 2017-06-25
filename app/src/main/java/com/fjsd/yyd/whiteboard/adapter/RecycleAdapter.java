package com.fjsd.yyd.whiteboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjsd.yyd.whiteboard.R;
import com.fjsd.yyd.whiteboard.activity.WhiteBoardActivity;
import com.fjsd.yyd.whiteboard.util.StoreUtil;

import java.util.ArrayList;
import java.util.Collections;



/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.TextViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private static Context mContext;
    private ArrayList<String> mFilenames;
    private static ArrayList<String> mFilepaths;


    public RecycleAdapter(Context context, ArrayList<String> filenames, ArrayList<String> filepaths) {
        mContext = context;
        mFilenames = filenames;
        mFilepaths = filepaths;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /*
    * 添加N个数据item
    * */
    public void addTitles(ArrayList<String> filnames,ArrayList<String> filepaths ) {
        mFilenames.clear();
        mFilepaths.clear();
        mFilenames.addAll(filnames);
        mFilepaths.addAll(filepaths);
        notifyItemRangeInserted(0, mFilenames.size()); //在0到titles.length位置添加titles.length-0项数据
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(mLayoutInflater.inflate(R.layout.wb_item,parent,false));
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.mTextView.setText(mFilenames.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilenames != null ? mFilenames.size() : 0;
    }


    public static class TextViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        RecycleAdapter mAdapter;
        public TextViewHolder(View view) {
            super(view);  //一定要添加这行
            mTextView = (TextView) view.findViewById(R.id.tv_wb_name);
            view.setOnClickListener(new View.OnClickListener() {  //设置item监听器
                @Override
                public void onClick(View v) {
                    StoreUtil.readWhiteBoardPoints(mFilepaths.get(getAdapterPosition()));  //读取保存的白板文件，还原之前的板书
                    Intent intent = new Intent(mContext, WhiteBoardActivity.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                }
            });
        }
    }


}
