package jcc.xiangmu.view.fragment;

import java.util.List;

import jcc.xiangmu.module.been.NewsBeen;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface INewsFragment {
    void updateView(List<NewsBeen>list);
    void toastFailed();
}
