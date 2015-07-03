package com.threehalf.tucao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.threehalf.tucao.R;

/**
 * Created by Administrator on 2015/3/27.
 */
public class MenuAdapter extends BaseAdapter {
    private ArrayList<String> mLMenu;
    private Context mContext;
    private int cur_pos = 0;

    public MenuAdapter(Context context, ArrayList<String> menuList) {
        this.mContext = context;
        this.mLMenu = menuList;
    }

    @Override
    public int getCount() {
        return mLMenu == null ? 0 : mLMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return mLMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuViewHolder menuViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_layout, parent, false);
            menuViewHolder = new MenuViewHolder();
            menuViewHolder.mLLMenu = (LinearLayout) convertView.findViewById(R.id.ll_menu);
            menuViewHolder.mTvMenuName = (TextView) convertView.findViewById(R.id.tv_menu_name);
            convertView.setTag(menuViewHolder);
        } else {
            menuViewHolder = (MenuViewHolder) convertView.getTag();
        }
        menuViewHolder.mTvMenuName.setText(mLMenu.get(position));
        if (cur_pos == position) {
            menuViewHolder.mLLMenu.setBackgroundResource(R.color.dividers);
        }else {
            menuViewHolder.mLLMenu.setBackgroundResource(R.color.white);
        }
        return convertView;
    }

    public void setCurPosition(int position) {


        if (position>0){
            this.cur_pos = position-1;
        }
        notifyDataSetChanged();
    }

    public class MenuViewHolder {
        private LinearLayout mLLMenu;
        private TextView mTvMenuName;
    }
}

