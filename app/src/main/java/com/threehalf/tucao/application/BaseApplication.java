package com.threehalf.tucao.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Application;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.datatype.BmobGeoPoint;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;

public class BaseApplication extends Application {
	private static BaseApplication mInstance;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public static BmobGeoPoint lastPoint = null;// 上
	public static String addStr = "";
	private Map<String, BmobChatUser> contactList = new HashMap<String, BmobChatUser>();
	private MediaPlayer mMediaPlayer;
	public static BaseApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		initBaidu();
	}
	/*public synchronized MediaPlayer getMediaPlayer() {
		if (mMediaPlayer == null)
			mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		return mMediaPlayer;
	}*/
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			double latitude = location.getLatitude();
			double longtitude = location.getLongitude();
			BmobLog.i("获取坐标" + latitude + "====" + latitude);

			if (lastPoint != null) {
				if (lastPoint.getLatitude() == location.getLatitude()
						&& lastPoint.getLongitude() == location.getLongitude()) {
					BmobLog.i("两次获取坐标相同");// 若两次请求获取到的地理位置坐标是相同的，则不再定位
					mLocationClient.stop();
					return;
				}
			}
			lastPoint = new BmobGeoPoint(longtitude, latitude);
			addStr = location.getCity();
		}
	}

	/**
	 * 初始化百度相关sdk initBaidumap
	 * 
	 * @Title: initBaidumap
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initBaidu() {
		SDKInitializer.initialize(this);
		// 初始化定位sdk
		initBaiduLocClient();
	}

	/**
	 * 初始化百度定位sdk
	 * 
	 * @Title: initBaiduLocClient
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initBaiduLocClient() {
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
	}

	/**
	 * 退出登录,清空缓存数据
	 */
	public void logout() {
		BmobUserManager.getInstance(getApplicationContext()).logout();


	}

	public final String PREF_LONGTITUDE = "longtitude";// 经度
	private String longtitude = "";

	/**
	 * 获取经度
	 * 
	 * @return
	 */
	public String getLongtitude() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		longtitude = preferences.getString(PREF_LONGTITUDE, "");
		return longtitude;
	}

	/**
	 * 设置经度
	 * 
	 * @param pwd
	 */
	public void setLongtitude(String lon) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_LONGTITUDE, lon).commit()) {
			longtitude = lon;
		}
	}

	public final String PREF_LATITUDE = "latitude";// 经度
	private String latitude = "";

	/**
	 * 获取纬度
	 * 
	 * @return
	 */
	public String getLatitude() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		latitude = preferences.getString(PREF_LATITUDE, "");
		return latitude;
	}

	/**
	 * 设置维度
	 * 
	 * @param pwd
	 */
	public void setLatitude(String lat) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		if (editor.putString(PREF_LATITUDE, lat).commit()) {
			latitude = lat;
		}
	}

	/**
	 * list转map 以用户名为key
	 * 
	 * @return Map<String,BmobChatUser>
	 * @throws
	 */
	public static Map<String, BmobChatUser> list2map(List<BmobChatUser> users) {
		Map<String, BmobChatUser> friends = new HashMap<String, BmobChatUser>();
		for (BmobChatUser user : users) {
			friends.put(user.getUsername(), user);
		}
		return friends;
	}

	/**
	 * 获取内存中好友user list
	 * 
	 * @return
	 */
	public Map<String, BmobChatUser> getContactList() {
		return contactList;
	}

	/**
	 * 设置好友user list到内存中
	 * 
	 * @param contactList
	 */
	public void setContactList(Map<String, BmobChatUser> contactList) {
		if (this.contactList != null) {
			this.contactList.clear();
		}
		this.contactList = contactList;
	}

	/**
	 * map转list
	 * 
	 * @Title: map2list
	 * @return List<BmobChatUser>
	 * @throws
	 */
	public static List<BmobChatUser> map2list(Map<String, BmobChatUser> maps) {
		List<BmobChatUser> users = new ArrayList<BmobChatUser>();
		Iterator<Entry<String, BmobChatUser>> iterator = maps.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, BmobChatUser> entry = iterator.next();
			users.add(entry.getValue());
		}
		return users;
	}

}
