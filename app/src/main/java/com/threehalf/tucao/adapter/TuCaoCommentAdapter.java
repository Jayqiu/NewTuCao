package com.threehalf.tucao.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.threehalf.tucao.R;
import com.threehalf.tucao.entity.Comment;
import com.threehalf.tucao.util.ImageLoadOptions;
import com.threehalf.tucao.util.ImageLoaderConfig;
import com.threehalf.tucao.view.CircleImageView;
import com.threehalf.tucao.view.EmoticonsTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/1.
 */
public class TuCaoCommentAdapter extends BaseAdapter {
    private List<Comment> commetList;
    private Context mContext;
    public TuCaoCommentAdapter( List<Comment> commetList,Context context){
        this.commetList=commetList;
        this.mContext=context;
    }
    public void setList( List<Comment> commetList){
        this.commetList=commetList;
        notifyDataSetChanged();
    }
    @Override
    public Object getItem(int position) {
        return commetList.get(position);
    }

    @Override
    public int getCount() {
        return commetList==null ?0:commetList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup paramViewGroup) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.item_comment_list, paramViewGroup, false);
            viewHolder=new ViewHolder();
            viewHolder.mIvAvatar=(CircleImageView)convertView.findViewById(R.id.iv_itme_avater);
            viewHolder.mTvName=(TextView)convertView.findViewById(R.id.tv_item_name);
            viewHolder.mTvContent=(EmoticonsTextView)convertView.findViewById(R.id.tv_item_tucaocomment);
            viewHolder.mTvTime=(TextView)convertView.findViewById(R.id.tv_item_ctime);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)   convertView.getTag();
        }
        Comment comment=commetList.get(position);
        viewHolder.mTvName.setText(comment.getUserInfo().getNick());
        viewHolder.mTvTime.setText(comment.getCreatedAt());
        String commentStr=comment.getCommentContent();
        if (comment.getCommentType() == 1001){
            commentStr = "回复:<font color='blue'>@" + comment.getReplyUserNick() + ":</font><br>" + commentStr;
        }
        viewHolder.mTvContent.setText(Html.fromHtml(commentStr));
        ImageLoaderConfig.ImageLoad(mContext).displayImage(comment.getUserInfo().getAvatar(), viewHolder.mIvAvatar, ImageLoadOptions.getAvatarOptions());
        return convertView;
    }
    private class ViewHolder{
        private CircleImageView mIvAvatar;
        private TextView mTvName;
        private EmoticonsTextView mTvContent;
        private TextView mTvTime;
    }
}
