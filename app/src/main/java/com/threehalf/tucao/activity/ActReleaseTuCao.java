package com.threehalf.tucao.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threehalf.tucao.R;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.FileNotFoundException;

import com.threehalf.tucao.adapter.SimpleListDialogAdapter;
import com.threehalf.tucao.application.AppCache;
import com.threehalf.tucao.view.EmoticonsEditText;

import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.threehalf.tucao.entity.TuCao;
import com.threehalf.tucao.entity.User;
import com.threehalf.tucao.view.baseview.BaseArrayListAdapter;
import com.threehalf.tucao.view.baseview.BaseDialog;
import com.threehalf.tucao.view.baseview.SimpleListDialog;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 发布吐槽
 */
public class ActReleaseTuCao extends BaseFragmentActivity implements Toolbar.OnMenuItemClickListener {
    private Toolbar mToolbar;
    private Button mBtnChatEmo;
    private String imagUrl = null;
    private LinearLayout layout_emo;
    private Context mContext;
    private EmoticonsEditText mEtContent;
    private ImageView mImTuCao;
    private String mPicturePath;
    private TextView mTvTextNum;
    private ViewPager pager_emo;
    private String subject;
    private TuCao tuCaoEntity;
    private User userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tucao);
        userEntity = AppCache.getInstance(mApplication).getLoginInfo();
        this.initViews();
        this.initEvents();
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        this.mImTuCao = (ImageView)findViewById(R.id.iv_tucao);
        mEtContent=(EmoticonsEditText)findViewById(R.id.et_content);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initEvents() {
//        this.mEtContent.addTextChangedListener(this);
        this.mImTuCao.setOnClickListener(this);
//        this.mBtnChatEmo.setOnClickListener(this);
        this.mEtContent.setOnClickListener(this);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tocao_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_send:
                    showPublishDialog();
                break;
        }
        return true;
    }

    private void showPublishDialog() {

        String[] publishStr = getResources().getStringArray(R.array.is_anonymous_items);
        SimpleListDialog simpleListDialog = new SimpleListDialog(ActReleaseTuCao.this);
        simpleListDialog.setTitle(getResources().getString(R.string.app_name));
        simpleListDialog.setAdapter(new SimpleListDialogAdapter(ActReleaseTuCao.this,publishStr));
        simpleListDialog.setOnSimpleListItemClickListener(new SimpleListDialog.onSimpleListItemClickListener() {
            @Override
            public void onItemClick(int paramAnonymousInt) {
                if (paramAnonymousInt == 0){
                    if (userEntity.getGold() < 2L) {
                        showCustomToast("亲,匿名吐槽需要2金币,你的金币不足耶！");
                        return;
                    }

                }
                try {
                    saveTucaoImage(paramAnonymousInt);
                } catch (FileNotFoundException localFileNotFoundException) {
                    localFileNotFoundException.printStackTrace();
                    showCustomToast("没有找到图片...");
                }
            }
        });


        simpleListDialog.show();

    }

    /**
     * 发布吐槽
     *
     * @param paramInt
     */
    private void addTuCaoForUser(final int paramInt) {
        BmobRelation localBmobRelation = new BmobRelation();
        localBmobRelation.add(this.tuCaoEntity);
        this.userEntity.increment("tucaoNum", Integer.valueOf(1));
        this.userEntity.increment("tucaoGrade", Integer.valueOf(5));
        this.userEntity.setTucao(localBmobRelation);
        if (paramInt == 0)
            this.userEntity.increment("gold", Integer.valueOf(-2));
        this.userEntity.update(this.mApplication, new UpdateListener() {
            public void onFailure(int paramAnonymousInt, String paramAnonymousString) {
                ActReleaseTuCao.this.dismissLoadingDialog();
                Log.i("save===>", paramAnonymousString);
                ActReleaseTuCao.this.showCustomToast(paramAnonymousString);
            }

            public void onSuccess() {
                dismissLoadingDialog();
                showCustomToast("吐槽成功了");
                userEntity.setTucaoNum(1L + userEntity.getTucaoNum());
                userEntity.setTucaoGrade(5L + userEntity.getTucaoGrade());
                if (paramInt == 0)
                    userEntity.setGold(userEntity.getGold() - 2L);

                onBackPressed();
            }
        });
    }

    private void tuCao(final int paramInt, BmobFile paramBmobFile) {
        showLoadingDialog("吐槽中...");
        tuCaoEntity = new TuCao();
        String str = this.mEtContent.getText().toString().trim();
        this.tuCaoEntity.setIsAnonymous(paramInt);
        this.tuCaoEntity.setCommentNum(0L);
        this.tuCaoEntity.setPraiseNum(0L);
        this.tuCaoEntity.setTucaoContent(str);
        this.tuCaoEntity.setTucaoImg(paramBmobFile);
        this.tuCaoEntity.setUserInfo(this.userEntity);
        this.tuCaoEntity.setUserId(this.userManager.getCurrentUserObjectId());
        this.tuCaoEntity.save(this.mApplication, new SaveListener() {
            public void onFailure(int paramAnonymousInt, String paramAnonymousString) {
                dismissLoadingDialog();
                Log.i("save===>", paramAnonymousString);
                showCustomToast(paramAnonymousString);
            }

            public void onSuccess() {
                addTuCaoForUser(paramInt);
            }
        });
    }

    /**
     * 保存图片
     *
     * @param paramInt
     * @throws FileNotFoundException
     */
    private void saveTucaoImage(final int paramInt)
            throws FileNotFoundException {
        if ((this.mPicturePath != null) && (this.mPicturePath.length() > 0)) {
            final BmobFile localBmobFile = new BmobFile(new File(this.mPicturePath));
            showLoadingDialog("吐槽中...");
            localBmobFile.upload(this, new UploadFileListener() {
                public void onFailure(int paramAnonymousInt, String paramAnonymousString) {
                    Log.i("save= img==>", paramAnonymousString);
                    dismissLoadingDialog();
                }

                public void onProgress(Integer paramAnonymousInteger) {
                    super.onProgress(paramAnonymousInteger);
                    Log.i("url====>", paramAnonymousInteger.toString());
                }

                public void onStart() {
                    super.onStart();
                }

                public void onSuccess() {
                    imagUrl = localBmobFile.getFileUrl(ActReleaseTuCao.this);
                    tuCao(paramInt, localBmobFile);
                }
            });
            return;
        }else{
            tuCao(paramInt, null);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }
}
