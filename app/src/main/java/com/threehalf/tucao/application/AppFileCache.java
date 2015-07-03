package com.threehalf.tucao.application;

import android.content.Context;

import com.threehalf.tucao.entity.TuCao;

import java.io.File;
import java.util.List;


public class AppFileCache {
	private Context mContext;
	private static AppFileCache appFileCache;
	private String obj="tucao";

	public AppFileCache(Context context) {
		this.mContext = context;
		try {
			obj = AppCache.getInstance(context).getLoginInfo().getObjectId();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	public static AppFileCache getInstance(Context context) {
		if (appFileCache == null) {
			appFileCache = new AppFileCache(context);
		}
		
		return appFileCache;
	}

	/*@SuppressWarnings("unchecked")
	public List<TuCao> getNewsToCao() {
		List<TuCao> newesEntityList = null;
		File file = new File(PathUtilManager.getSdCardRootPath()
				+ PathUtilManager.FILE_CACHE_DIR
				+ obj
				+ PathUtilManager.NEWS_TOCAO_CACHE_FILE_NAME);
		FileToolUtil fileToolUtil = new FileToolUtil(file, mContext);
		try {
			String date = fileToolUtil.readFiles();
			byte[] base64Bytes = Base64.decodeBase64(date.getBytes());
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(bais);
				newesEntityList = (List<TuCao>) ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Exception", "getLoginInfo---Exception");
			}
			return newesEntityList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i("FileNotFoundException",
					PathUtilManager.NEWS_TOCAO_CACHE_FILE_NAME
							+ "path not find");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException", PathUtilManager.NEWS_TOCAO_CACHE_FILE_NAME
					+ "read IOException");
			return null;
		}

	}

	public boolean saveNewsTuCao(List<TuCao> newesEntityList) {
		File file = new File(PathUtilManager.getSdCardRootPath()
				+ PathUtilManager.FILE_CACHE_DIR
				+ obj
				+ PathUtilManager.NEWS_TOCAO_CACHE_FILE_NAME);
		FileToolUtil fileToolUtil = new FileToolUtil(file, mContext);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(newesEntityList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将Product对象放到OutputStream中
		String content = new String(Base64.encodeBase64(baos.toByteArray()));
		try {
			fileToolUtil.writeFiles(content);
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Exception", PathUtilManager.NEWS_TOCAO_CACHE_FILE_NAME
					+ "write IOException");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TuCao> getHottesToCao() {
		List<TuCao> newesEntityList = null;
		File file = new File(PathUtilManager.getSdCardRootPath()
				+ PathUtilManager.FILE_CACHE_DIR
				+obj
				+ PathUtilManager.HOTTEST_TOCAO_CACHE_FILE_NAME);
		FileToolUtil fileToolUtil = new FileToolUtil(file, mContext);
		try {
			String date = fileToolUtil.readFiles();
			byte[] base64Bytes = Base64.decodeBase64(date.getBytes());
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(bais);
				newesEntityList = (List<TuCao>) ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("Exception", "getLoginInfo---Exception");
			}
			return newesEntityList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.i("FileNotFoundException",
					PathUtilManager.HOTTEST_TOCAO_CACHE_FILE_NAME
							+ "path not find");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			Log.i("IOException", PathUtilManager.HOTTEST_TOCAO_CACHE_FILE_NAME
					+ "read IOException");
			return null;
		}

	}

	public boolean saveHottesTuCao(List<TuCao> newesEntityList) {
		File file = new File(PathUtilManager.getSdCardRootPath()
				+ PathUtilManager.FILE_CACHE_DIR
				+ obj
				+ PathUtilManager.HOTTEST_TOCAO_CACHE_FILE_NAME);
		FileToolUtil fileToolUtil = new FileToolUtil(file, mContext);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(baos);
				oos.writeObject(newesEntityList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 将Product对象放到OutputStream中
			String content = new String(Base64.encodeBase64(baos.toByteArray()));
			fileToolUtil.writeFiles(content);
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Exception", PathUtilManager.HOTTEST_TOCAO_CACHE_FILE_NAME
					+ "write IOException");
			return false;
		}
	}
*/
}
