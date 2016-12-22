package jcc.xiangmu.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.List;

import jcc.xiangmu.R;
import jcc.xiangmu.module.been.NewsBeen;
import jcc.xiangmu.module.been.NewsItemBeen;
import jcc.xiangmu.view.activity.MainActivity;
import jcc.xiangmu.view.activity.NewsItemActivity;

/**
 * Created by Administrator on 2016/10/21.
 */
public class NewsFragmentAdapter extends RecyclerView.Adapter {
    private List<NewsBeen> list;
    private Context context;
    private LayoutInflater inflater;
    private int TYPEFIRST = 1;
    private int TYPETWO = 2;

    public NewsFragmentAdapter(Context context, List<NewsBeen> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == 1) {

            view = inflater.inflate(R.layout.news_item_item1, parent, false);
            return new Holderfirst(view);

        } else if (viewType == 2) {
            view = inflater.inflate(R.layout.news_item_item2, parent, false);
            return new Holdertwo(view);
        } else {
            view = inflater.inflate(R.layout.news_item_item3, parent, false);
            return new HolderThree(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (TYPEFIRST == getItemViewType(position)) {
            Holderfirst holder1 = (Holderfirst) holder;
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(Uri.parse("asset://jcc.xiangmu/haha.gif"))
                    .build();
            holder1.sdv.setController(draweeController);

            holder1.tvtitle.setText(list.get(position).getTitle());
            holder1.tvcontent.setText(list.get(position).getDesc());
            holder1.tvtime.setText(list.get(position).getPubDate());
        } else if (TYPETWO == getItemViewType(position)) {
            Holdertwo holdertwo = (Holdertwo) holder;
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(Uri.parse("asset://jcc.xiangmu/haha.gif"))
                    .build();
            holdertwo.sdv2.setController(draweeController);
            holdertwo.tvtime.setText(list.get(position).getPubDate());
            holdertwo.tvtitle.setText(list.get(position).getTitle());
            GridLayoutManager gridmanager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
            holdertwo.recyclerView.setLayoutManager(gridmanager);
            NewsRecyclerItemAdapter adapter = new NewsRecyclerItemAdapter(context, list.get(position).getImgurl());
            holdertwo.recyclerView.setAdapter(adapter);

        } else {
            HolderThree holderThree = (HolderThree) holder;
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(Uri.parse("asset://jcc.xiangmu/haha.gif"))
                    .build();
            holderThree.sdv3.setController(draweeController);
            holderThree.tvtime.setText(list.get(position).getPubDate());
            holderThree.tvtitle.setText(list.get(position).getTitle());
            holderThree.tvcontent.setText(list.get(position).getDesc());
            Picasso.with(context).load(list.get(position).getImgurl().get(0)).into(holderThree.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = 1;
        if (list.get(position).getImgurl().size() > 1) {
            type = 2;
        } else if (list.get(position).getImgurl().size() == 1) {
            type = 3;
        }
        return type;
    }

    class Holderfirst extends RecyclerView.ViewHolder {
        private SimpleDraweeView sdv;
        private TextView tvtime;
        private TextView tvtitle;
        private TextView tvcontent;

        public Holderfirst(View itemView) {
            super(itemView);
            sdv = (SimpleDraweeView) itemView.findViewById(R.id.simpledraweeview);
            tvtime = (TextView) itemView.findViewById(R.id.tv_newstime_item);
            tvtitle = (TextView) itemView.findViewById(R.id.tv_newstitle_item);
            tvcontent = (TextView) itemView.findViewById(R.id.tv_newscontent_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = list.get(getPosition()).getTitle();
                    String content = list.get(getPosition()).getContent();
                    String source = list.get(getPosition()).getSource();
                    String link = list.get(getPosition()).getLink();
                    Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra("content", new NewsItemBeen(title,content, source, link));
                    ((MainActivity) context).startActivityForResult(intent, 3);


                }
            });
        }


    }

    class Holdertwo extends RecyclerView.ViewHolder {
        private TextView tvtime;
        private TextView tvtitle;
        private RecyclerView recyclerView;
        private SimpleDraweeView sdv2;
        public Holdertwo(View itemView) {
            super(itemView);
            sdv2 = (SimpleDraweeView) itemView.findViewById(R.id.simpledraweeview2);
            tvtime = (TextView) itemView.findViewById(R.id.tv_newstime_item2);
            tvtitle = (TextView) itemView.findViewById(R.id.tv_newstitle_item2);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.pager_newsitem_item2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = list.get(getPosition()).getTitle();
                    String content = list.get(getPosition()).getContent();
                    String source = list.get(getPosition()).getSource();
                    String link = list.get(getPosition()).getLink();
                    Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra("content", new NewsItemBeen(title,content, source, link));
                    ((MainActivity) context).startActivityForResult(intent, 3);


                }
            });

        }
    }

    class HolderThree extends RecyclerView.ViewHolder {

        private SimpleDraweeView sdv3;
        private TextView tvtime;
        private TextView tvtitle;
        private ImageView imageView;
        private TextView tvcontent;

        public HolderThree(View itemView) {
            super(itemView);
            sdv3 = (SimpleDraweeView) itemView.findViewById(R.id.simpledraweeview3);
            tvtime = (TextView) itemView.findViewById(R.id.tv_newstime_item3);
            tvtitle = (TextView) itemView.findViewById(R.id.tv_newstitle_item3);
            imageView = (ImageView) itemView.findViewById(R.id.img_newsimg_item3);
            tvcontent = (TextView) itemView.findViewById(R.id.tv_newscontent_item3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = list.get(getPosition()).getTitle();
                    String content = list.get(getPosition()).getContent();
                    String source = list.get(getPosition()).getSource();
                    String link = list.get(getPosition()).getLink();
                    Intent intent = new Intent(context, NewsItemActivity.class);
                    intent.putExtra("content", new NewsItemBeen(title,content, source, link));
                    ((MainActivity) context).startActivityForResult(intent, 3);


                }
            });
        }
    }
}
