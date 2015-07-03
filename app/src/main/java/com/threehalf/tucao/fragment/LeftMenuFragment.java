package com.threehalf.tucao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.threehalf.tucao.R;
import com.threehalf.tucao.adapter.MenuAdapter;
import com.threehalf.tucao.application.AppCache;
import com.threehalf.tucao.entity.User;


public class LeftMenuFragment extends Fragment {
    private View mView;
    private ListView mLvMenu;
    private ArrayList<String> mLMenu = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private OnMenuFragmentListener menuFragmentListener;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= AppCache.getInstance(this.getActivity()).getLoginInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.menu_left, container, false);
        initView();
        return mView;


    }

    @Override
    public void onResume() {
        super.onResume();
        user= AppCache.getInstance(this.getActivity()).getLoginInfo();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEven();
    }

    private void initView() {
        mLvMenu = (ListView) mView.findViewById(R.id.lv_menu);
    }

    private void initData() {

        mLMenu.add("新槽");
        mLMenu.add("热槽");
        mLMenu.add("吐槽我们");
        mLMenu.add("设置");

        if (!TextUtils.isEmpty(user.getObjectId())){
            View meView = this.getLayoutInflater(null).inflate(R.layout.include_me_layout, null);
            mLvMenu.addHeaderView(meView);

            mLMenu.add("退出登录");
        }else {
            mLMenu.add("马上登录");
        }

        menuAdapter = new MenuAdapter(getActivity(), mLMenu);
        mLvMenu.setAdapter(menuAdapter);
        menuAdapter.setCurPosition(1);
    }

    private void initEven() {
        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (menuFragmentListener != null) {
                    int nowPosition;
                    if (!TextUtils.isEmpty(user.getObjectId())){
                        nowPosition=position;
                    }else {
                        nowPosition=position+1;
                    }
                    menuFragmentListener.onMenuFragmentr(nowPosition);
                    menuAdapter.setCurPosition(nowPosition);

                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuFragmentListener = null;
    }
    public void setMenuFragmentListener(OnMenuFragmentListener menuFragmentListener) {
        this.menuFragmentListener = menuFragmentListener;

    }

    public interface OnMenuFragmentListener {
        public void onMenuFragmentr(int position);
    }
}
