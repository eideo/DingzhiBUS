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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;

import org.xutils.DbManager;

import chenfei.com.activity.DingzhiBusActivity;
import chenfei.com.activity.MainActivity;
import chenfei.com.activity.NearlinesAcitivity;
import chenfei.com.base.R;
import chenfei.com.home.CompanyBusActivity;
import chenfei.com.home.NoStopActivity;
import chenfei.com.home.SchoolBusActivity;
import chenfei.com.home.SmalllogisticsActivity;
import chenfei.com.home.TravelListActivity;

import android.widget.LinearLayout;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private View homefragmentView;
    private RollPagerView mRollViewPager;
    private DbManager db;
    private LinearLayout  xiaochefuwull, banxianbanchell, xiaojianwuliull, zhoumobanchell, zhidachell, dingzhibusll,lvyoubusll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (homefragmentView == null) {
            homefragmentView = inflater.inflate(R.layout.homepagefragmentlayout, container,
                    false);
        }
        MainActivity.InitTitle("定制公交",true,true);
        mRollViewPager = (RollPagerView) homefragmentView.findViewById(R.id.roll_view_pager);
        mRollViewPager.setPlayDelay(3000);
        mRollViewPager.setAnimationDurtion(500);
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.RED, Color.WHITE));
        xiaochefuwull = (LinearLayout) homefragmentView.findViewById(R.id.xiaochefuwull);
        banxianbanchell = (LinearLayout) homefragmentView.findViewById(R.id.banxianbanchell);
        xiaojianwuliull = (LinearLayout) homefragmentView.findViewById(R.id.xiaojianwuliull);
        zhoumobanchell = (LinearLayout) homefragmentView.findViewById(R.id.zhoumobanchell);
        zhidachell = (LinearLayout) homefragmentView.findViewById(R.id.zhidachell);
        dingzhibusll = (LinearLayout) homefragmentView.findViewById(R.id.dingzhibusll);
        lvyoubusll = (LinearLayout) homefragmentView.findViewById(R.id.lvyoubusll);
        xiaochefuwull.setOnClickListener(this);
        banxianbanchell.setOnClickListener(this);
        xiaojianwuliull.setOnClickListener(this);
        zhoumobanchell.setOnClickListener(this);
        zhidachell.setOnClickListener(this);
        dingzhibusll.setOnClickListener(this);
        lvyoubusll.setOnClickListener(this);
        super.onCreateView(inflater, container, savedInstanceState);
        homefragmentView.forceLayout();
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) homefragmentView.getParent();
        if (parent != null) {
            parent.removeView(homefragmentView);
        }
        return homefragmentView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.xiaochefuwull:
                startActivity(new Intent(getActivity(), SchoolBusActivity.class));
                break;
            case R.id.banxianbanchell:
                startActivity(new Intent(getActivity(), NearlinesAcitivity.class));
                break;
            case R.id.xiaojianwuliull:
                startActivity(new Intent(getActivity(), SmalllogisticsActivity.class));
                break;
            case R.id.zhoumobanchell:
                startActivity(new Intent(getActivity(), CompanyBusActivity.class));
                break;
            case R.id.zhidachell:
                startActivity(new Intent(getActivity(), NoStopActivity.class));
                break;
            case R.id.dingzhibusll:
                Intent dingzhiintent=new Intent(getActivity(), DingzhiBusActivity.class);
                startActivity(dingzhiintent);
                break;
            case R.id.lvyoubusll:
                startActivity(new Intent(getActivity(), TravelListActivity.class));
                break;
            default:
                break;

        }

    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5,
        };

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }

    }
}
