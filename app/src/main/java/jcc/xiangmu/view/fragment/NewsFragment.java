package jcc.xiangmu.view.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.common.logging.LoggingDelegate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jcc.xiangmu.R;
import jcc.xiangmu.module.been.NewsBeen;
import jcc.xiangmu.progress.LocalNewsFragmentProgress;
import jcc.xiangmu.progress.NewsFragmentProgress;
import jcc.xiangmu.view.BaseFragment;
import jcc.xiangmu.view.adapter.NewsFragmentAdapter;


/**
 * Created by Administrator on 2016/10/20.
 */
public class NewsFragment extends BaseFragment implements INewsFragment,ILocalNewsFragement{
    private RecyclerView mrecycleview;
    private String channelId;
    private NewsFragmentProgress fragmentProgress;
    private Handler mhandler = new Handler();
    private SwipeRefreshLayout mrefresh;
    private NewsFragmentAdapter adapter;
    private ProgressBar mprogresssbar;
    private LinearLayoutManager manager;
    private List<NewsBeen> mlist = new ArrayList<>();
    private boolean isload = false;
    private int currentPage = 1;
    private int firstvisibleitem;
    private int firstvisibleitemtop;
    private LocalNewsFragmentProgress localNewsFragmentProgress;

    public NewsFragment(){}
    public void setchannelid(String channelId) {
        this.channelId = channelId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProgress = new NewsFragmentProgress(getActivity(),channelId, this);
        localNewsFragmentProgress = new LocalNewsFragmentProgress(getActivity(),channelId,this);
//        fragmentProgress.start(1);
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        mrecycleview = (RecyclerView) view.findViewById(R.id.news_recyclerview);
        mrefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        mprogresssbar = (ProgressBar) view.findViewById(R.id.news_progresssbar);
        setrefresh();
        initevent();
        loadlocal();
        return view;
    }


    private void loadlocal() {
        String path = getActivity().getExternalFilesDir(null)+File.separator+channelId+".txt";
        File file = new File(path);
        if(file.exists()){
            try {
                localNewsFragmentProgress.localstart();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TAG", "loadlocal: "+"文件存在");
        }else {
            fragmentProgress.start(1);
        }
    }

    private void initevent() {

    }

    private void initdata() {


        Log.d("TAG", "initdata: " + mlist.size());
        adapter = new NewsFragmentAdapter(getActivity(), mlist);
        manager = new LinearLayoutManager(getActivity());
        mrecycleview.setLayoutManager(manager);
        mrecycleview.setAdapter(adapter);
        if(isload) {
            ((LinearLayoutManager) mrecycleview.getLayoutManager()).scrollToPositionWithOffset(firstvisibleitem + 1, firstvisibleitemtop);
            firstvisibleitem = 0;
            firstvisibleitemtop = 0;
            isload=false;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setrefresh() {
        //下拉刷新
        mrefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        mrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentProgress.start(1);
                currentPage=1;
            }
        });
        //上拉加载
        mrecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int countitem = layoutManager.getItemCount();
                int lastvisibleitem = layoutManager.findLastVisibleItemPosition();
                if ((lastvisibleitem + 1) == countitem) {

                    if (!isload) {

                        firstvisibleitem = layoutManager.findFirstVisibleItemPosition();
                        firstvisibleitemtop = layoutManager.getChildAt(0).getTop();
                        mprogresssbar.setVisibility(View.VISIBLE);
                        isload = true;
                        fragmentProgress.start(++currentPage);
                    }
                } else {

                    mprogresssbar.setVisibility(View.GONE);
                }


            }
        });

    }

    @Override
    public void updateView(final List<NewsBeen> list) {

        mhandler.post(new Runnable() {
            @Override
            public void run() {
                if (isload) {
                    mlist.addAll(list);
                    Log.d("TAG", "run: mlist.size" + mlist.size());
                    mprogresssbar.setVisibility(View.GONE);

                } else {
                    mlist = list;
                }
                initdata();
                if (mrefresh.isRefreshing()) {
                    mrefresh.setRefreshing(false);
                }

            }
        });
    }

    @Override
    public void toastFailed() {
        mhandler.post(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_SHORT).show();
                if(mrefresh.isRefreshing()){
                    mrefresh.setRefreshing(false);
                }
                if(mprogresssbar.getVisibility()==View.VISIBLE){
                    mprogresssbar.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void localUpdateView(List<NewsBeen> locallist) {

        mlist=locallist;
        initdata();
    }
}
