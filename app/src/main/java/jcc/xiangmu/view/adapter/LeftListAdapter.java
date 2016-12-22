package jcc.xiangmu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jcc.xiangmu.R;
import jcc.xiangmu.module.been.LeftItem;

/**
 * Created by Administrator on 2016/10/29.
 */

public class LeftListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<LeftItem>list;

    public LeftListAdapter(Context context, List<LeftItem> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView==null){
           convertView = inflater.inflate(R.layout.left_list_item_layout,parent,false);
            holder = new Holder();
            holder.tv = (TextView) convertView.findViewById(R.id.left_item_tv);
            holder.img = (ImageView) convertView.findViewById(R.id.left_item_iv);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getTitle());
        holder.img.setImageResource(list.get(position).getImgid());
        return convertView;
    }
    class Holder{
        private TextView tv;
        private ImageView img;
    }
}
