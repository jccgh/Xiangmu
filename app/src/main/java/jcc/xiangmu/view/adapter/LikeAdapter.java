package jcc.xiangmu.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jcc.xiangmu.R;
import jcc.xiangmu.module.been.LikeShareBeen;
import jcc.xiangmu.view.activity.minterface.IsetOnLongClick;
import jcc.xiangmu.view.activity.minterface.LikeShareItemActivity;

/**
 * Created by Administrator on 2016/11/1.
 */

public class LikeAdapter extends RecyclerView.Adapter {
    private List<LikeShareBeen> list;
    private LayoutInflater inflater;
    private Context context;
    private IsetOnLongClick isetOnLongClick;
    public LikeAdapter(Context context, List<LikeShareBeen> list, IsetOnLongClick isetOnLongClick) {
        this.list = list;
        this.context=context;
        this.isetOnLongClick = isetOnLongClick;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.like_cardview_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder mholder = (Holder) holder;
        mholder.tv.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class Holder extends RecyclerView.ViewHolder{
        private TextView tv;
        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.like_tv);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("TAG", "adapter*onLongClick: ****************"+getPosition());
                    isetOnLongClick.onLongclick(getPosition());
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LikeShareItemActivity.class);
                    intent.putExtra("content",list.get(getPosition()).getContent());
                    context.startActivity(intent);

                }
            });
        }
    }
}

