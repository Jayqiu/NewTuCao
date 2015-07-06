package com.threehalf.tucao.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.threehalf.tucao.R;
import com.threehalf.tucao.adapter.NewesAdapter;
import com.threehalf.tucao.entity.Comment;
import com.threehalf.tucao.entity.TuCao;
import com.threehalf.tucao.entity.User;
import com.threehalf.tucao.util.ConstantUtil;
import com.threehalf.tucao.util.ImageLoadOptions;
import com.threehalf.tucao.util.ImageLoaderConfig;
import com.threehalf.tucao.view.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2015/7/6.
 */
public class ActMe extends BaseFragmentActivity implements SwipeRefreshLayout.OnRefreshListener, XListView.IXListViewListener {
    private XListView mListView;
    private Toolbar mToolbar;
    private int nowPager = 0;
    private TextView mTvUserDes, mTvUserNick,mTvTuCaoNum,mTvUserGrade;
    private ImageView mIvAvatar;
    private boolean isNextPage = false;
    private List<TuCao> newesEntityList = new ArrayList<TuCao>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private User userEntity;
    private boolean refreshFlag = false;
    private NewesAdapter newesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_me);
        mContext = this;
        userEntity = (User) getIntent().getSerializableExtra("userEntity");
        initViews();
        initEvents();
        initData();
        getData();

    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(userEntity.getNick() + "的吐槽");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView = (XListView) findViewById(R.id.lv_me_tocao);

        View view = getLayoutInflater().inflate(R.layout.include_me_item, null);
        mIvAvatar = (ImageView) view.findViewById(R.id.user_avatar);
        mTvUserDes = (TextView) view.findViewById(R.id.user_des);
        mTvUserNick = (TextView) view.findViewById(R.id.user_name);
        mTvTuCaoNum=(TextView) view.findViewById(R.id.user_tucao_num);
        mTvUserGrade=(TextView) view.findViewById(R.id.user_grade);
        mListView.addHeaderView(view);


    }

    private void initData() {

        mTvUserNick.setText(userEntity.getNick());
        mTvUserDes.setText(userEntity.getSignature());
        mTvTuCaoNum.setText(userEntity.getTucaoNum()+"次吐槽");
        mTvUserGrade.setText(userEntity.getTucaoGrade()+"点经验");
        ImageLoaderConfig.ImageLoad(mContext).displayImage(userEntity.getAvatar(), mIvAvatar, ImageLoadOptions.getAvatarOptions());

    }

    @Override
    protected void initEvents() {
        mListView.setXListViewListener(this);
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {

        BmobQuery<TuCao> localBmobQuery = new BmobQuery<TuCao>();
        localBmobQuery.setLimit(15);
        localBmobQuery.setSkip(nowPager * 15);
        localBmobQuery.include("userInfo");
        localBmobQuery.order("-createdAt");
        localBmobQuery.addWhereEqualTo("userInfo", this.userEntity);
        localBmobQuery.findObjects(mContext, new FindListener<TuCao>() {
            @Override
            public void onSuccess(List<TuCao> tuCaoList) {
                if (nowPager == 0) {
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
                                    newesEntityList = tuCaoList;
                                    return;
                                }
                            }
                        }

                        newesEntityList = tuCaoList;
                        newesAdapter = new NewesAdapter(mContext, newesEntityList);
                        mListView.setAdapter(newesAdapter);
                    }
                } else {
                    if (tuCaoList != null && tuCaoList.size() > 0) {
                        //
                        ActMe.this.newesEntityList.addAll(tuCaoList);

                        newesAdapter.setList(ActMe.this.newesEntityList);
                    } else {
                        showSnackBar("没有更多吐槽了");
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
                showSnackBar(s);
            }
        });
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
                    nowPager = 0;
                    getData();

                    break;

            }
        }
    };

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPager++;
                getData();
            }
        }, 1000);
    }

    private void onLoad() {
        mListView.setPullLoadEnable(isNextPage);
        mListView.stopRefresh();
        mListView.stopLoadMore();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new java.util.Date());
        mListView.setRefreshTime(date);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
