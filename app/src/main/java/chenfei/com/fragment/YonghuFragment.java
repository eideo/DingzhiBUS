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
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import chenfei.com.activity.MainActivity;
import chenfei.com.activity.UserInfoActivity;
import chenfei.com.base.BaseActivity;
import chenfei.com.base.R;

/**
 * Created by Iiro Krankka (http://github.com/roughike)
 */
public class YonghuFragment extends Fragment {
    private View YonghuView;
    @OnClick(R.id.grzltv) void grzltv() {
        Intent grzlintent=new Intent(getActivity(), UserInfoActivity.class);
        startActivity(grzlintent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (YonghuView == null) {
            YonghuView = inflater.inflate(R.layout.fragment_yonghu, container,
                    false);
        }
        MainActivity.InitTitle("用户信息",true,false);
        super.onCreateView(inflater, container, savedInstanceState);
        YonghuView.forceLayout();
        ViewGroup parent = (ViewGroup) YonghuView.getParent();
        if (parent != null) {
            parent.removeView(YonghuView);
        }
        ButterKnife.bind(this, YonghuView);
        return YonghuView;
    }

}
