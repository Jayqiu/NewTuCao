package com.threehalf.tucao.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.threehalf.tucao.R;
import com.threehalf.tucao.application.AppCache;
import com.threehalf.tucao.entity.User;
import com.threehalf.tucao.util.EncryptUtil;
import com.threehalf.tucao.util.ToolUtil;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author jayqiu
 */
public class ActLogin extends BaseFragmentActivity {
    private static Handler mHandler;
    BmobChatUser currentUser;
    private String loginTypeStr;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private EditText mEtPhone;
    private EditText mEtPwd;
    private String nameStr;
    private String pwdStr;
    //    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    private User userEntity;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initViews();
        initEvents();
        this.userEntity = AppCache.getInstance(this.mApplication).getLoginInfo();
        if (this.userEntity != null) {
            this.mEtPhone.setText(this.userEntity.getUsername());
        }
    }

    @Override
    protected void initEvents() {
        this.mBtnRegister.setOnClickListener(this);
        this.mBtnLogin.setOnClickListener(this);
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
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.login_text);
        this.mBtnRegister = ((Button) findViewById(R.id.btn_register));
        this.mBtnLogin = ((Button) findViewById(R.id.btn_login));
        this.mEtPhone = ((EditText) findViewById(R.id.et_login_name));
        this.mEtPwd = ((EditText) findViewById(R.id.et_login_pwd));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                if (validate()){
                    login();
                }
                break;
        }
    }
    private void login(){
        showLoadingDialog("正在登录...");
        User user = new User();
        user.setUsername(nameStr);
        user.setPassword(EncryptUtil.encrypt(pwdStr));
        userManager.login(user,new SaveListener() {
            @Override
            public void onSuccess() {
                getUserInfo();
            }
            @Override
            public void onFailure(int i, String s) {
                dismissLoadingDialog();
                showSnackBar(s);
            }
        });
    }
    private void getUserInfo(){
        userManager.bindInstallationForRegister(userManager.getCurrentUserName());
        BmobQuery bmobQuery= new BmobQuery();
        bmobQuery.getObject(mApplication,userManager.getCurrentUserObjectId(),new GetListener<User>(){
            @Override
            public void onSuccess(User user) {
                dismissLoadingDialog();
                AppCache.getInstance(mApplication).saveUserInfo(user);
                onBackPressed();

            }

            @Override
            public void onFailure(int i, String s) {
                dismissLoadingDialog();
                showSnackBar(s);
            }
        });
    }
    public boolean validate() {
        nameStr = this.mEtPhone.getText().toString().trim();
        pwdStr=mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            showSnackBar("请输入登录邮箱");
            this.mEtPhone.requestFocus();
            return false;
        }else{
            if(ToolUtil.isEmail(nameStr)){
                if (TextUtils.isEmpty(pwdStr)) {
                    showSnackBar("请输入登录密码");

                    this.mEtPwd.requestFocus();
                    return false;
                }else{
                    return true;
                }

            }else {
                showSnackBar("请输入正确的邮箱");
                return false;
            }
        }
    }
}
