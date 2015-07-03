package com.threehalf.tucao.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.threehalf.tucao.R;

/**
 * Created by Administrator on 2015/6/30.
 */
public class ActFeedback extends  BaseFragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feedback);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }
}
