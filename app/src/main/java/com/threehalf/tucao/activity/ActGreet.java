package com.threehalf.tucao.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.threehalf.tucao.R;
import cn.bmob.im.BmobChat;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import  com.threehalf.tucao.util.ConstantUtil;
public class ActGreet extends BaseFragmentActivity{
	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;
	private TextView mTvAppName;
	private Animation mAnimation;

	private LinearLayout mLlGreet;
	// 定位获取当前用户的地理位置

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_greet);
	/*	AdManager.getInstance(this).init("5f7af9b31a9710ca",
				"7037f4fcc296ce6a", false);*/

		// 可设置调试模式，当为true的时候，会在logcat的BmobChat下输出一些日志，包括推送服务是否正常运行，如果服务端返回错误，也会一并打印出来。方便开发者调试
		BmobChat.DEBUG_MODE = true;
		// BmobIM SDK初始化--只需要这一段代码即可完成初始化
		// 请到Bmob官网(http://www.bmob.cn/)申请ApplicationId,具体地址:http://docs.bmob.cn/android/faststart/index.html?menukey=fast_start&key=start_android
//		BmobChat.getInstance(this).init(ConstantUtil.APPLICATIONID);
		// 初始化BmobSDK
		Bmob.initialize(this, ConstantUtil.APPLICATIONID);
		BmobInstallation.getCurrentInstallation(this).save();
		// 开启定位
		initLocClient();

		initViews();
		initEvents();

//		ShareSDK.initSDK(this);

	}

	private void isLogin() {
		/*if (userManager.getCurrentUser() != null) {
			// 每次自动登陆的时候就需要更新下当前位置和好友的资料，因为好友的头像，昵称啥的是经常变动的
			updateUserInfos();
		}*/
		mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startActivity(ActMain.class, null, true);
				break;
			case GO_LOGIN:
//				startActivity(ActLogin.class, null, true);
				break;
			}
		}
	};

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		mTvAppName = (TextView) findViewById(R.id.tv_app_name);
		mLlGreet = (LinearLayout) findViewById(R.id.ll_greet);
		mAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);

	}

	protected void initEvents() {
		// TODO Auto-generated method stub
		mLlGreet.setAnimation(mAnimation);
		mAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				isLogin();
			}
		});
	}



	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		// SpotManager.getInstance(ActGreet.this).disMiss(false);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// SpotManager.getInstance(this).unregisterSceenReceiver();
		super.onDestroy();
	}

	private void initLocClient() {
//		mLocationClient = BaseApplication.getInstance().mLocationClient;
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式:高精度模式
//		option.setCoorType("bd09ll"); // 设置坐标类型:百度经纬度
//		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms:低于1000为手动定位一次，大于或等于1000则为定时定位
//		option.setIsNeedAddress(false);// 不需要包含地址信息
//		mLocationClient.setLocOption(option);
//		mLocationClient.start();
	}
}
