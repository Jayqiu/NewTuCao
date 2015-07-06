package com.threehalf.tucao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.threehalf.tucao.R;
import com.threehalf.tucao.activity.ActMe;
import com.threehalf.tucao.entity.TuCao;
import com.threehalf.tucao.util.ImageLoadOptions;
import com.threehalf.tucao.util.ImageLoaderConfig;

/**
 * Created by jayqiu on 2015/3/27.
 */
public class NewesAdapter extends BaseAdapter {
    private Context mContext;
    private List<TuCao> mList;

    public NewesAdapter(Context context, List<TuCao> mList) {
        this.mContext = context;
        this.mList = mList;
    }
    public void setList(List<TuCao> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mList==null  ?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_newes_layout, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.mTvUserName = (TextView) convertView.findViewById(R.id.tv_item_name);
            viewHolder. mTvContent = (TextView) convertView.findViewById(R.id.tv_item_tucaocontent);
            viewHolder.mIvTocaoImg = (ImageView) convertView
                    .findViewById(R.id.tv_item_tucaoimg);
            viewHolder. mIvAvatar = (ImageView) convertView.findViewById(R.id.iv_itme_hottest_avater);
            viewHolder.mTvPraiseNum=(TextView)convertView.findViewById(R.id.tv_itme_hottest_praise_num);
            viewHolder.mTvTuCaoNum=(TextView)convertView.findViewById(R.id.tv_item_hotttest_commentnum);
            viewHolder.mLlUserInfo=(LinearLayout)convertView.findViewById(R.id.ll_user_info);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
   final      TuCao tuCao = mList.get(position);
        viewHolder.mTvUserName.setText(tuCao.getUserInfo().getNick());
        viewHolder.mTvContent.setText(tuCao.getTucaoContent());
        if (tuCao.getTucaoImg() != null) {
            viewHolder.mIvTocaoImg.setVisibility(View.VISIBLE);
            ImageLoaderConfig.ImageLoad(mContext).displayImage(tuCao.getTucaoImg().getFileUrl(mContext),  viewHolder.mIvTocaoImg, ImageLoadOptions.getImgOptions());
        }else {
            viewHolder.mIvTocaoImg.setVisibility(View.GONE);
        }
        viewHolder.mLlUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putSerializable("userEntity",tuCao.getUserInfo());
                Intent intent =new Intent(mContext, ActMe.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        viewHolder.mTvPraiseNum.setText(tuCao.getPraiseNum()+"赞");
        viewHolder.mTvTuCaoNum.setText(tuCao.getCommentNum()+"次吐槽");
        ImageLoaderConfig.ImageLoad(mContext).displayImage(tuCao.getUserInfo().getAvatar(), viewHolder.mIvAvatar, ImageLoadOptions.getAvatarOptions());
        return convertView;
    }

    public static class ViewHolder  {
        private TextView mTvUserName;
        private TextView mTvContent;
        private ImageView mIvTocaoImg;
        private ImageView mIvAvatar;
        private TextView mTvPraiseNum;
        private TextView mTvTuCaoNum;
        private LinearLayout mLlUserInfo;




    }
}
