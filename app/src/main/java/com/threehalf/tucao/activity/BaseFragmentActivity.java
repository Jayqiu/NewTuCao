package com.threehalf.tucao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.util.BmobLog;
import com.threehalf.tucao.R;
import  com.threehalf.tucao.view.FlippingLoadingDialog;
import com.threehalf.tucao.application.BaseApplication;
import  com.threehalf.tucao.R.anim;
import com.threehalf.tucao.widgets.SnackBar;


public abstract class BaseFragmentActivity extends ActionBarActivity implements View.OnClickListener{
	/**
	 * 屏幕的宽度、高度、密度
	 */
	protected BaseApplication mApplication;
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;
	protected FlippingLoadingDialog mLoadingDialog;

	protected BmobUserManager userManager;
	protected BmobChatManager manager;
	protected Context mContext;
	protected SnackBar snackBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		mContext = this;
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		mApplication = (BaseApplication) getApplication();
		mLoadingDialog = new FlippingLoadingDialog(this, "请求提交");
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;

	}



	/** 初始化视�? **/
	protected abstract void initViews();

	/** 初始化事�? **/
	protected abstract void initEvents();

	protected void showLoadingDialog(String text) {
		if (text != null) {
			mLoadingDialog.setText(text);
		}
		mLoadingDialog.show();
	}

	protected void dismissLoadingDialog() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}
	protected void showSnackBar(String msg){
		snackBar	= new SnackBar(this, msg);
		snackBar.show();
	}
	protected void showSnackBar(int resId){
		snackBar	= new SnackBar(this, getString(resId));
		snackBar.show();
	}
	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	/** 显示自定义Toast提示(来自res) **/
	protected void showCustomToast(int resId) {
		View toastRoot = LayoutInflater.from(BaseFragmentActivity.this)
				.inflate(R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text))
				.setText(getString(resId));
		Toast toast = new Toast(BaseFragmentActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** 显示自定义Toast提示(来自String) **/
	protected void showCustomToast(String text) {
		View toastRoot = LayoutInflater.from(BaseFragmentActivity.this)
				.inflate(R.layout.common_toast, null);
		((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
		Toast toast = new Toast(BaseFragmentActivity.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastRoot);
		toast.show();
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle, boolean isfinish) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		finishWithAnim(isfinish);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		startActivity(action, null, false);
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle, boolean isfinish) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		finishWithAnim(isfinish);
	}

	public void finishWithAnim(boolean isfinish) {
		if (isfinish) {
			this.finish();
		}
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finishWithAnim(true);
	}

    @Override
    public void onClick(View v) {

    }
    /**
	 * 用于登陆或者自动登陆情况下的用户资料及好友资料的检测更新
	 * 
	 * @Title: updateUserInfos
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	/*public void updateUserInfos() {
		// 更新地理位置信息
		// updateUserLocation();
		// 查询该用户的好友列表(这个好友列表是去除黑名单用户的哦),目前支持的查询好友个数为100，如需修改请在调用这个方法前设置BmobConfig.LIMIT_CONTACTS即可。
		// 这里默认采取的是登陆成功之后即将好于列表存储到数据库中，并更新到当前内存中,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0 == BmobConfig.CODE_COMMON_NONE) {
					ShowLog(arg1);
				} else {
					ShowLog("查询好友列表失败：" + arg1);
				}
			}

			@Override
			public void onSuccess(List<BmobChatUser> arg0) {
				// TODO Auto-generated method stub
				// 保存到application中方便比较
				BaseApplication.getInstance().setContactList(
						BaseApplication.list2map(arg0));
			}
		});
	}*/

	/**
	 * 打Log ShowLog
	 * 
	 * @return void
	 * @throws
	 */
	public void ShowLog(String msg) {
		BmobLog.i(msg);
	}

	/**
	 * 隐藏软键盘 hideSoftInputView
	 * 
	 * @Title: hideSoftInputView
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this
				.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 更新用户的经纬度信息
	 * 
	 * @Title: uploadLocation
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	/*public void updateUserLocation() {
		if (BaseApplication.lastPoint != null) {
			String saveLatitude = mApplication.getLatitude();
			String saveLongtitude = mApplication.getLongtitude();
			String newLat = String.valueOf(BaseApplication.lastPoint
					.getLatitude());
			String newLong = String.valueOf(BaseApplication.lastPoint
					.getLongitude());
			// ShowLog("saveLatitude ="+saveLatitude+",saveLongtitude = "+saveLongtitude);
			// ShowLog("newLat ="+newLat+",newLong = "+newLong);
			if (!saveLatitude.equals(newLat) || !saveLongtitude.equals(newLong)) {// 只有位置有变化就更新当前位置，达到实时更新的目的

				if (userManager.getCurrentUser() != null) {
					final User user = AppCache.getInstance(mContext)
							.getLoginInfo();
					user.setLocation(BaseApplication.lastPoint);
					user.setAddress(BaseApplication.addStr);
					user.update(this, new UpdateListener() {
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							BaseApplication.getInstance().setLatitude(
									String.valueOf(user.getLocation()
											.getLatitude()));
							BaseApplication.getInstance().setLongtitude(
									String.valueOf(user.getLocation()
											.getLongitude()));
							// ShowLog("经纬度更新成功");
						}

						@Override
						public void onFailure(int code, String msg) {
							// TODO Auto-generated method stub
							// ShowLog("经纬度更新 失败:"+msg);
						}
					});
				}

			} else {
				// ShowLog("用户位置未发生过变化");
			}
		}
	}*/

}
