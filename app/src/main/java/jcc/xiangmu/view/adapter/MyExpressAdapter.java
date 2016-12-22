package jcc.xiangmu.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jcc.xiangmu.R;
import jcc.xiangmu.module.been.ExpressBeen;

/**
 * Created by Administrator on 2016/10/15.
 */
public class MyExpressAdapter extends BaseAdapter {
    private List<ExpressBeen>list;
    private Context context;
    private LayoutInflater inflater;

    public MyExpressAdapter(List<ExpressBeen> list, Context context) {
        this.list = list;
        this.context = context;
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
        MyHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.express_item_layout,parent,false);
            holder = new MyHolder();
            holder.time = (TextView) convertView.findViewById(R.id.tv_time_item);
            holder.context = (TextView) convertView.findViewById(R.id.tv_context_item);
            convertView.setTag(holder);
        }else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.time.setText(list.get(position).getTime());
        holder.context.setText(list.get(position).getContext());
        return convertView;
    }
    class MyHolder {
        TextView time;
        TextView context;

    }
}
