/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chenfei.com.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import chenfei.com.adapter.GuanfangLinelistadapter;
import chenfei.com.adapter.RunningLinelistadapter;
import chenfei.com.base.ApiInterface;
import chenfei.com.base.R;
import chenfei.com.category.GetLinesInfo;
import chenfei.com.utils.DialogHelper;
import chenfei.com.utils.ToastUtils;
import okhttp3.Call;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */
public class GuanfangLineFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private View guanfangfragment;
    private RecyclerView mRecyclerView;
    private GuanfangLinelistadapter madpter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private  int TOTAL_COUNTER = 1000;
    private static final int PAGE_SIZE = 10;
    private int delayMillis = 1000;
    private int mCurrentCounter = 0;
    private List<GetLinesInfo.DataBean> mData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (guanfangfragment == null) {
            guanfangfragment = inflater.inflate(R.layout.fragment_line, container,
                    false);
        }
        mRecyclerView = (RecyclerView) guanfangfragment.findViewById(R.id.line_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) guanfangfragment.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        super.onCreateView(inflater, container, savedInstanceState);
        guanfangfragment.forceLayout();

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) guanfangfragment.getParent();
        if (parent != null) {
            parent.removeView(guanfangfragment);
        }
        return guanfangfragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        GetDaibanList();
    }

    @Override
    public void onRefresh() {
        OkHttpUtils.get()
                .addParams("type", "1")
                .url(ApiInterface.Getlinelists)     // 请求方式和请求url
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(getActivity(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String s) {
                        DialogHelper.dismissLoadingDialog();
                        GetLinesInfo mgetlinesinfo=new GetLinesInfo();
                        mgetlinesinfo=JSON.parseObject(s,GetLinesInfo.class);
                        mData = new ArrayList<GetLinesInfo.DataBean>();
                        mData = mgetlinesinfo.getData();
                       // TOTAL_COUNTER=mData.size();
                        initAdapter();
                        mRecyclerView.setAdapter(madpter);
                    }
                });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    madpter.notifyDataChangedAfterLoadMore(false);
                    View view = getActivity().getLayoutInflater().inflate(R.layout.no_moredata, (ViewGroup) mRecyclerView.getParent(), false);
                    madpter.addFooterView(view);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            madpter.notifyDataChangedAfterLoadMore(getData(mData, PAGE_SIZE), true);
                            mCurrentCounter = madpter.getData().size();
                        }
                    }, delayMillis);
                }
            }


        });
    }

    private void initAdapter() {
        madpter = new GuanfangLinelistadapter(getActivity(), R.layout.guanfanglineitem, mData, PAGE_SIZE);
        madpter.openLoadAnimation();
        mRecyclerView.setAdapter(madpter);
        mCurrentCounter = madpter.getData().size();
        madpter.setOnLoadMoreListener(this);
        madpter.openLoadMore(PAGE_SIZE, true);//or call mQuickAdapter.setPageSize(PAGE_SIZE);  mQuickAdapter.openLoadMore(true);
        madpter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    public static List<GetLinesInfo.DataBean> getData(List<GetLinesInfo.DataBean> data, int dataSize) {
        List<GetLinesInfo.DataBean> newlist = new ArrayList<GetLinesInfo.DataBean>();
        if (data.size() < dataSize) {
            newlist = data;
        } else {
            for (int i = 0; i < dataSize; i++) {
                newlist.add(data.get(i));
            }
        }
        return newlist;
    }

    private void GetDaibanList() {
        OkHttpUtils.get()
                .addParams("type", "1")
                .url(ApiInterface.Getlinelists)     // 请求方式和请求url
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        DialogHelper.dismissLoadingDialog();
                        ToastUtils.showLong(getActivity(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String s) {
                        DialogHelper.dismissLoadingDialog();
                        GetLinesInfo mgetlinesinfo=new GetLinesInfo();
                        mgetlinesinfo=JSON.parseObject(s,GetLinesInfo.class);
                        mData = new ArrayList<GetLinesInfo.DataBean>();
                        TOTAL_COUNTER=mData.size();
                        mData = mgetlinesinfo.getData();
                        initAdapter();
                        mRecyclerView.setAdapter(madpter);
                    }
                });
    }
}
