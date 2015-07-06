package com.threehalf.tucao.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threehalf.tucao.R;
import com.threehalf.tucao.adapter.TuCaoCommentAdapter;
import com.threehalf.tucao.entity.Comment;
import com.threehalf.tucao.entity.TuCao;
import com.threehalf.tucao.util.ConstantUtil;
import com.threehalf.tucao.util.ImageLoadOptions;
import com.threehalf.tucao.util.ImageLoaderConfig;
import com.threehalf.tucao.view.CircleImageView;
import com.threehalf.tucao.view.EmoticonsTextView;
import com.threehalf.tucao.view.materialdesign.ButtonFloat;
import com.threehalf.tucao.view.xlistview.XListView;
import com.way.ui.emoji.EmojiEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2015/7/1.
 */
public class ActTucaoDetail extends BaseFragmentActivity implements SwipeRefreshLayout.OnRefreshListener, XListView.IXListViewListener {
    private XListView mListView;
    private Toolbar mToolbar;
    private int nowPager = 0;
    private LinearLayout mLlUserInfo;
    private ImageView mIvTuCaoImg;
    private CircleImageView mIvAvatar;
    private TextView mTvPraise, mTvCommentnum, mTvPraisenum, mTvTime, mTvUserName;
    private TuCao tuCaoEntity;
    private EmoticonsTextView mTvContent;
    private boolean isNextPage = false;
    private List<Comment> commentList = new ArrayList();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TuCaoCommentAdapter tuCaoCommentAdapter;
  /*  private LinearLayout mLlCommentBottom;
    private RelativeLayout mLlTuCao;*/
    private EmojiEditText mEtMsg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tuCaoEntity = (TuCao) getIntent().getSerializableExtra("tucao");
        mContext = this;
        setContentView(R.layout.act_tucao_detail);
        initViews();
        initEvents();
        updateUI();
//        mEtMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                controlKeyboardLayout(mLlTuCao, mLlCommentBottom);
//            }
//        });

    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.text_tucao_detail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mListView = (XListView) findViewById(R.id.lv_comment);

        View mView = LayoutInflater.from(this).inflate(R.layout.include_tucao_detail, null, false);
        mLlUserInfo = (LinearLayout) mView.findViewById(R.id.ll_user_info);
        mIvTuCaoImg = (ImageView) mView.findViewById(R.id.iv_tucaoimg);
        mIvAvatar = (CircleImageView) mView.findViewById(R.id.iv_itme_avater);
        mTvUserName = (TextView) mView.findViewById(R.id.tv_item_name);

        mTvCommentnum = (TextView) mView.findViewById(R.id.tv_commentnum);
        mTvPraisenum = (TextView) mView.findViewById(R.id.tv_praise_num);
        mTvTime = (TextView) mView.findViewById(R.id.tv_tucao_time);
        mTvContent = (EmoticonsTextView) mView.findViewById(R.id.tv_tucaocontent);
        mListView.addHeaderView(mView);
        mEtMsg=(EmojiEditText)findViewById(R.id.et_sendmessage);
//        mLlCommentBottom=(LinearLayout)findViewById(R.id.ll_comment_bottom);
//mLlTuCao=(RelativeLayout)findViewById(R.id.ll_tucao_detail);
        tuCaoCommentAdapter = new TuCaoCommentAdapter(commentList, mContext);
        mListView.setAdapter(tuCaoCommentAdapter);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
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

    private void getData() {

        BmobQuery<Comment> localBmobQuery = new BmobQuery<Comment>();
        localBmobQuery.order("-createdAt");
        localBmobQuery.include("userInfo");
        localBmobQuery.addWhereEqualTo("tuCao", this.tuCaoEntity);
        localBmobQuery.setLimit(15);
        localBmobQuery.setSkip(nowPager * 15);
        localBmobQuery.findObjects(mContext, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                if (nowPager == 0) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    commentList = list;
                    if (commentList != null && commentList.size() > 0) {
                        tuCaoCommentAdapter.setList(commentList);
                    }
                } else {
                    if (commentList != null && commentList.size() > 0) {
                        commentList.addAll(list);
                        tuCaoCommentAdapter.setList(commentList);
                    }

                }
                if (list.size() < ConstantUtil.LIST_LIMIT) {
                    isNextPage = false;
                } else {
                    isNextPage = true;
                }
                onLoad();
            }

            @Override
            public void onError(int i, String s) {
                showShortToast(s);
            }
        });
    }

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

    private void updateUI() {
        initImag();

        this.mTvCommentnum.setText(this.tuCaoEntity.getCommentNum() + "条吐槽");
        this.mTvPraisenum.setText(this.tuCaoEntity.getPraiseNum() + "次赞");
        this.mTvContent.setText(this.tuCaoEntity.getTucaoContent());

        if (this.tuCaoEntity.getIsAnonymous() == 0)
            this.mLlUserInfo.setVisibility(View.GONE);
        else {
            this.mLlUserInfo.setVisibility(View.VISIBLE);
            this.mTvUserName.setText(this.tuCaoEntity.getUserInfo().getNick());
            this.mTvTime.setText((this.tuCaoEntity.getCreatedAt()));
            initAvatar();
        }
        getData();

    }

    private void initImag() {
        if (tuCaoEntity.getTucaoImg() != null) {
            ImageLoaderConfig.ImageLoad(mContext).
                    displayImage(tuCaoEntity.getTucaoImg().getFileUrl(mContext),
                            mIvTuCaoImg, ImageLoadOptions.getAvatarOptions());
        }


    }

    private void initAvatar() {
        ImageLoaderConfig.ImageLoad(mContext).
                displayImage(tuCaoEntity.getUserInfo().getAvatar(),
                        mIvAvatar, ImageLoadOptions.getAvatarOptions());

    }
    /**
     * @param root 最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }


    private void onLoad() {
        mListView.setPullLoadEnable(isNextPage);
        mListView.stopRefresh();
        mListView.stopLoadMore();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new java.util.Date());
        mListView.setRefreshTime(date);
    }

}
