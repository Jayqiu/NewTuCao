package com.threehalf.tucao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.threehalf.tucao.R;
import com.threehalf.tucao.activity.ActSetingMsg;

/**
 * Created by Administrator on 2015/6/30.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private LinearLayout mLlSettingMsg;
    private  static  SettingFragment settingFragment;
    public static SettingFragment newInstance(String param1, String param2) {
        if (settingFragment==null){
            settingFragment= new SettingFragment();
        }
        return settingFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting, null);
        initView();
        initEvent();
        return mView;
    }
    private void initView(){
        mLlSettingMsg=(LinearLayout)mView.findViewById(R.id.ll_setting_msg);
    }
    private void initEvent(){
        mLlSettingMsg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_setting_msg:
                startActivity(new Intent(getActivity(), ActSetingMsg.class));
            break;
        }
    }
}
