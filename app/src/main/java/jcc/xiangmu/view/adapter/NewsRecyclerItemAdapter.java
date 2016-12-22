package jcc.xiangmu.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jcc.xiangmu.R;

/**
 * Created by Administrator on 2016/10/22.
 */

public class NewsRecyclerItemAdapter extends RecyclerView.Adapter{
    private List<String>listurl;
    private Context context;
    private LayoutInflater inflater;
    public NewsRecyclerItemAdapter(Context context,List<String> listurl) {
        this.listurl = listurl;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_item_itemrecyclerview_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Holder holder1 = (Holder) holder;
        String url = listurl.get(position);
        Picasso.with(context).load(url).into(holder1.imageView);
    }

    @Override
    public int getItemCount() {
        return listurl.size();
    }
    class Holder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgview_recycler);
        }
    }
}
