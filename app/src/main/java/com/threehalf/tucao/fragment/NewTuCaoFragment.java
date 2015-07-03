package com.threehalf.tucao.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import com.threehalf.tucao.R;
import com.threehalf.tucao.activity.ActTucaoDetail;
import com.threehalf.tucao.adapter.NewesAdapter;
import com.threehalf.tucao.entity.TuCao;
import com.threehalf.tucao.util.ConstantUtil;
import com.threehalf.tucao.view.xlistview.XListView;

public class NewTuCaoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,XListView.IXListViewListener ,AdapterView.OnItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<TuCao> newesEntityList = new ArrayList<TuCao>();
    private List<TuCao> newesEntityListTmpe;
    private boolean isNextPage = false;
    private boolean refreshFlag = false;
    private String mParam1;
    private String mParam2;
    private View mView;
    private Context mContext;
    private XListView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewesAdapter newesAdapter;
    private int nowPager = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private  static NewTuCaoFragment fragment;
    public static NewTuCaoFragment newInstance(String param1, String param2) {
        if(fragment==null){
            fragment = new NewTuCaoFragment();
        }
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public NewTuCaoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_item, null);

        initView();

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(nowPager);
    }

    private void initView() {
        mRecyclerView = (XListView) mView.findViewById(R.id.rl_view);


        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setXListViewListener(this);
        mRecyclerView.setPullRefreshEnable(false);
        mRecyclerView.setPullLoadEnable(true);
        mRecyclerView.setOnItemClickListener(this);

    }

    @Override
    public void onRefresh() {

        mHandler.sendEmptyMessageDelayed(1000, 2000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                   nowPager=0;
                    getData(nowPager);

                    break;

            }
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private void getData(final int page) {
        BmobQuery<TuCao> bmobQuery = new BmobQuery<TuCao>();
        ArrayList<BmobQuery<TuCao>> queries = new ArrayList<BmobQuery<TuCao>>();
        bmobQuery.order("-createdAt");
        bmobQuery.include("userInfo");
        bmobQuery.setLimit(ConstantUtil.LIST_LIMIT);
        bmobQuery.setSkip(page * ConstantUtil.LIST_LIMIT);
        //

        bmobQuery.findObjects(mContext, new FindListener<TuCao>() {
            @Override
            public void onSuccess(List<TuCao> tuCaoList) {
                if (page == 0) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (tuCaoList != null & tuCaoList.size() > 0) {
                        if (!refreshFlag) {
                            if (newesEntityList != null
                                    && newesEntityList.size() > 0) {
                                if (!tuCaoList
                                        .get(0)
                                        .getObjectId()
                                        .equals(newesEntityList.get(0)
                                                .getObjectId())) {
//                                    mTvNewToast.setVisibility(View.VISIBLE);
                                    newesEntityListTmpe = tuCaoList;
                                    return;
                                }
                            }
                        }

                        newesEntityList = tuCaoList;
                        newesAdapter = new NewesAdapter(getActivity(), newesEntityList);
                        mRecyclerView.setAdapter(newesAdapter);
                    }
                } else {
                    if (tuCaoList != null && tuCaoList.size() > 0) {
                        //
                        NewTuCaoFragment.this.newesEntityList.addAll(tuCaoList);
//                        AppFileCache.getInstance(mContext).saveNewsTuCao(
//                                NewestFragment.this.newesEntityList);
                        newesAdapter.setList(NewTuCaoFragment.this.newesEntityList);
                    } else {
//                        showCustomToast("没有更多吐槽了");
                    }

                }
                if (tuCaoList.size() < ConstantUtil.LIST_LIMIT) {
                    isNextPage = false;
                } else {
                    isNextPage = true;
                }
                onLoad();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), " s-------------------->" + s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPager++;
                getData(nowPager);
            }
        }, 1000);
    }

    private void onLoad() {
        mRecyclerView.setPullLoadEnable(isNextPage);
        mRecyclerView.stopRefresh();
        mRecyclerView.stopLoadMore();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new java.util.Date());
        mRecyclerView.setRefreshTime(date);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TuCao tuCao=(TuCao)parent.getItemAtPosition(position);
        if(tuCao!=null){
         Intent intent=   new Intent(getActivity(), ActTucaoDetail.class);
            Bundle bundle= new Bundle();
            bundle.putSerializable("tucao",tuCao);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
