package com.threehalf.tucao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

import com.threehalf.tucao.R;
import com.threehalf.tucao.application.AppCache;
import com.threehalf.tucao.entity.User;
import com.threehalf.tucao.fragment.HotTuCaoFragment;
import com.threehalf.tucao.fragment.LeftMenuFragment;
import com.threehalf.tucao.fragment.NewTuCaoMainFragment;
import com.threehalf.tucao.fragment.SettingFragment;
import com.threehalf.tucao.util.ToastUtil;
import com.threehalf.tucao.view.materialdesign.ButtonFloat;


public class ActMain extends BaseFragmentActivity implements View.OnClickListener,
        LeftMenuFragment.OnMenuFragmentListener, Toolbar.OnMenuItemClickListener {
    private Toolbar mToolbar;
    private ButtonFloat mBtnAdd;
    private DrawerLayout mMenuDrawer;
    private FrameLayout mMenuFlayout;
    private NewTuCaoMainFragment newToCaoMainFragment;
    private HotTuCaoFragment  hotTuCaoFragment;
    private SettingFragment settingFragment;
    private static Boolean isExit = false;
    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfo = AppCache.getInstance(mApplication).getLoginInfo();
        initViews();
        initEvents();
        initData();
    }

    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnAdd=(ButtonFloat)findViewById(R.id.btn_add);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        //
        mMenuDrawer = (DrawerLayout) findViewById(R.id.menu_drawer);
        mMenuFlayout = (FrameLayout) findViewById(R.id.menu_frame);

        LeftMenuFragment leftMenuFragment = new LeftMenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.menu_frame, leftMenuFragment);
        fragmentTransaction.commitAllowingStateLoss();
        leftMenuFragment.setMenuFragmentListener(this);

    }

    @Override
    protected void initEvents() {
        mMenuDrawer.setOnClickListener(this);
        mToolbar.setOnMenuItemClickListener(this);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
         userInfo = AppCache.getInstance(mApplication).getLoginInfo();
    }

    private void initData() {
        if (newToCaoMainFragment == null) {
            newToCaoMainFragment = NewTuCaoMainFragment.newInstance("", "");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, newToCaoMainFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_frame:

                break;
            case R.id.btn_add:
                if (TextUtils.isEmpty(userInfo.getObjectId())) {
                    startActivity(ActLogin.class, null, false);
                } else {
                    startActivity(ActReleaseTuCao.class, null, false);
                }
                break;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.action_edit:
                if (TextUtils.isEmpty(userInfo.getObjectId())) {
                    startActivity(ActLogin.class, null, false);
                } else {
                    startActivity(ActReleaseTuCao.class, null, false);
                }

                break;


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mMenuDrawer.isDrawerVisible(GravityCompat.START)) {
            mMenuDrawer.closeDrawers();

        } else {
            exitBy2Click();
        }
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtil.showCustomToast(ActMain.this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
        }
    }

    @Override
    public void onMenuFragmentr(int position) {
        mMenuDrawer.closeDrawers();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 1:
            if (newToCaoMainFragment==null){
                    newToCaoMainFragment = NewTuCaoMainFragment.newInstance("", "");
                }
                mToolbar.setTitle(R.string.app_name);
                mBtnAdd.setVisibility(View.VISIBLE);
                fragmentTransaction.replace(R.id.content_frame, newToCaoMainFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case 2:
                if (hotTuCaoFragment==null){
                    hotTuCaoFragment = HotTuCaoFragment.newInstance("", "");
                }
                mToolbar.setTitle(R.string.text_hot_tucao);
                mBtnAdd.setVisibility(View.VISIBLE);
                fragmentTransaction.replace(R.id.content_frame, hotTuCaoFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case 3:
                startActivity(ActFeedback.class,null,false);
                break;
            case 4:
                if (settingFragment==null){
                    settingFragment=SettingFragment.newInstance("","");
                }
                mBtnAdd.setVisibility(View.GONE);
                mToolbar.setTitle(R.string.text_setting);
                fragmentTransaction.replace(R.id.content_frame, settingFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case 5:
                mBtnAdd.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(userInfo.getObjectId())){//退出登录

                }else {
                    startActivity(ActLogin.class,null,false);
                }
                break;
        }
    }

}
