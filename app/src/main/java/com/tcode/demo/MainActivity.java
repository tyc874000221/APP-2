package com.tcode.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import RefreshUtil.DividerRVDecoration;
import RefreshUtil.SHSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SHSwipeRefreshLayout shSwipeRefreshLayout;
    private RAdapter rAdapter;

    private List<String> mDatas;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initData();
        initView();

    }

    private void init()
    {
        mContext = this;
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        shSwipeRefreshLayout = (SHSwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
    }

    private void initView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rAdapter = new RAdapter(mContext,mDatas);

        recyclerView.setAdapter(rAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerRVDecoration(this, DividerRVDecoration.VERTICAL_LIST));


        //item点击事件
        rAdapter.setOnItemClickLitener(new RAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(mContext, "点击了第"+(position+1)+"项"+"---内容是"+mDatas.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

                Toast.makeText(mContext, "长按了第"+(position+1)+"项"+"---内容是"+mDatas.get(position), Toast.LENGTH_LONG).show();
            }
        });


        shSwipeRefreshLayout.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {

                moreData();

                shSwipeRefreshLayout.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        rAdapter.notifyDataSetChanged();
                        shSwipeRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }

            @Override
            public void onLoading() {

                mDatas.add("加载了一个item");

                shSwipeRefreshLayout.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        rAdapter.notifyDataSetChanged();
                        shSwipeRefreshLayout.finishLoadmore();
                    }
                }, 2000);

            }

            @Override
            public void onRefreshPulStateChange(float percent, int state) {

                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        shSwipeRefreshLayout.setRefreshViewText("下拉刷新");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        shSwipeRefreshLayout.setRefreshViewText("松开刷新");
                        break;
                    case SHSwipeRefreshLayout.START:
                        shSwipeRefreshLayout.setRefreshViewText("正在刷新");
                        break;
                }

            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {

                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                        shSwipeRefreshLayout.setLoaderViewText("上拉加载");
                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                        shSwipeRefreshLayout.setLoaderViewText("松开加载");
                        break;
                    case SHSwipeRefreshLayout.START:
                        shSwipeRefreshLayout.setLoaderViewText("正在加载");
                        break;
                }

            }
        });

    }

    private void initData()
    {
        mDatas = new ArrayList();
        for (int i = 'A'; i < 'Z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    private void moreData()
    {
        mDatas.clear();
        for (int i = 'a'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

}
