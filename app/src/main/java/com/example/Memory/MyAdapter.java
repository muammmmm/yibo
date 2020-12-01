package com.example.Memory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class MyAdapter extends BaseAdapter {//适配器，不能给每条数据都建一个view，所以用适配器来完成数据填充
    private Context mContext;//上下文
    private List<User> mList;
    private LinearLayout mlayout;

    public MyAdapter(Context mContext, List<User> mList){
        this.mContext=mContext;
        this.mList=mList;
    }
    @Override
    public int getCount() {//得到大小
        return mList.size();
    }
    @Override
    public Object getItem(int position) {//位置
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {//位置id，即顺序
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);//布局服务将Context实例化为View对象
        mlayout=(LinearLayout) inflater.inflate(R.layout.listre,null);
        //初始化各控件
        TextView title=(TextView)mlayout.findViewById(R.id.list_title);
        TextView content=(TextView)mlayout.findViewById(R.id.list_content);
        TextView time=(TextView)mlayout.findViewById(R.id.list_time);

        title.setText(mList.get(position).getTitle());//传入你的数据
        content.setText(mList.get(position).getContent());
        time.setText(mList.get(position).getTime());
        return mlayout;
    }
}

