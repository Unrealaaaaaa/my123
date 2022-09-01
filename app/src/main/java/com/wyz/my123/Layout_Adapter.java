package com.wyz.my123;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class Layout_Adapter extends PagerAdapter {

    private List<View> mListView;

    public Layout_Adapter(List<View> mListView) {
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        return mListView.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mListView.get(position), 0);
        return mListView.get(position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mListView.get(position));
    }
}
