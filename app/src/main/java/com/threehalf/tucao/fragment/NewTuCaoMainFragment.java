package com.threehalf.tucao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threehalf.tucao.R;
import com.threehalf.tucao.view.smarttab.FragmentPagerItemAdapter;
import com.threehalf.tucao.view.smarttab.FragmentPagerItems;
import com.threehalf.tucao.view.smarttab.SmartTabLayout;

/**
 * Created by Administrator on 2015/6/26.
 */
public class NewTuCaoMainFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SmartTabLayout mSmartTab;
    private ViewPager mViewPager;
    private View mView;
    public static NewTuCaoMainFragment newInstance(String param1, String param2) {
        NewTuCaoMainFragment fragment = new NewTuCaoMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public NewTuCaoMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_new_tucao_main, container, false);
        initView();
        return mView;
    }
    private void initView(){
        mSmartTab=(SmartTabLayout)mView.findViewById(R.id.viewpagertab);
        mViewPager=(ViewPager)mView.findViewById(R.id.viewpager);
        FragmentPagerItemAdapter fragmentPagerItemAdapter= new FragmentPagerItemAdapter(getChildFragmentManager(),
                FragmentPagerItems.with(this.getActivity())
                        .add(R.string.pag1_text, NewTuCaoFragment.class)
                        .add(R.string.pag2_text, NewTuCaoFragment.class)
                        .add(R.string.pag3_text, NewTuCaoFragment.class)
                        .add(R.string.pag4_text, NewTuCaoFragment.class)
                        .create());
        mViewPager.setAdapter(fragmentPagerItemAdapter);
        mSmartTab.setViewPager(mViewPager);
    }
}
