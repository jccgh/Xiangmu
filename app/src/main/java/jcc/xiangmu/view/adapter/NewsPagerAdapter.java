package jcc.xiangmu.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment>list;
    private List<String>listitle;

    public NewsPagerAdapter(FragmentManager fm,List<Fragment>list,List<String>listitle) {
        super(fm);
        this.list = list;
        this.listitle = listitle;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listitle.get(position);
    }
}
