package com.threehalf.tucao.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.threehalf.tucao.R;

/**
 * Created by Administrator on 2015/3/28.
 */
public class ToastUtil {


    /**
     * 短暂显示Toast提示(来自res) *
     */
    public static void showShortToast(Context mContext, int resId) {
        Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示(来自String) *
     */
    public static void showShortToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast提示(来自res) *
     */
    public static void showLongToast(Context mContext, int resId) {
        Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示(来自String) *
     */
    public static void showLongToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示自定义Toast提示(来自res) *
     */
    public static void showCustomToast(Context mContext, int resId) {
        View toastRoot = LayoutInflater.from(mContext)
                .inflate(R.layout.common_toast, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text))
                .setText(mContext.getString(resId));
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

    /**
     * 显示自定义Toast提示(来自String) *
     */
    public static void showCustomToast(Context mContext, String text) {
        View toastRoot = LayoutInflater.from(mContext)
                .inflate(R.layout.common_toast, null);
        ((TextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }

}
