package com.threehalf.tucao.view.smarttab;

/**
 * Created by Administrator on 2015/6/26.
 */
import android.content.Context;

import java.util.ArrayList;

public abstract class PagerItems<T extends PagerItem> extends ArrayList<T> {

    private final Context context;

    protected PagerItems(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
