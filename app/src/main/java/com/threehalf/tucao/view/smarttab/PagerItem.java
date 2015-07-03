package com.threehalf.tucao.view.smarttab;

/**
 * Created by Administrator on 2015/6/26.
 */
public abstract class PagerItem {

    protected static final float DEFAULT_WIDTH = 1.f;

    private final CharSequence title;
    private final float width;

    protected PagerItem(CharSequence title, float width) {
        this.title = title;
        this.width = width;
    }

    public CharSequence getTitle() {
        return title;
    }

    public float getWidth() {
        return width;
    }

}
