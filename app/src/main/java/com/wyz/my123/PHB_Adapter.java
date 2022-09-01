package com.wyz.my123;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyz.my123.room.entity.User;

import java.util.List;

public class PHB_Adapter extends BaseAdapter {

    private List<User> data;
    private Context context;

    public PHB_Adapter(List<User> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        TextView textView = view.findViewById(R.id.phb_count);
        textView.setText("100 - i");
        /*设置第一名的item*/
        if (i == 0){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            viewHolder.textView = view.findViewById(R.id.tv);
            view.setTag(viewHolder);
            /*第二名*/
        }else if (i == 1){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            viewHolder.textView = view.findViewById(R.id.tv);
            viewHolder.imageView = view.findViewById(R.id.iv);
            /*更换第二名的图标*/
            viewHolder.imageView.setImageResource(R.drawable.pg2);
            view.setTag(viewHolder);
            /*第三名*/
        }else if (i == 2){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            viewHolder.textView = view.findViewById(R.id.tv);
            viewHolder.imageView = view.findViewById(R.id.iv);
            viewHolder.imageView.setImageResource(R.drawable.pg3);
            view.setTag(viewHolder);
            /*之后名次的item样式*/
        }else {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item_2, viewGroup, false);
            /*通过id设置名次*/
            TextView tv = view.findViewById(R.id.tv_2);
            tv.setText(i+1+"");
            viewHolder.textView = view.findViewById(R.id.tv);
            view.setTag(viewHolder);
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(data.get(i).getUserName());

        return view;
    }

    private final class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
