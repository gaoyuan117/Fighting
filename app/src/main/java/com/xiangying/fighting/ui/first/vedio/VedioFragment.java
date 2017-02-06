package com.xiangying.fighting.ui.first.vedio;

import android.view.LayoutInflater;
import android.view.View;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/18.
 */
public class VedioFragment extends BaseFragment {
    @Bind(R.id.fragmenttalk_sharehouse)
    PullableListView fragmenttalkSharehouse;
    @Bind(R.id.common_pull_refresh_view_show)
    PullToRefreshLayout commonPullRefreshViewShow;


    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragmentcommunity, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadNetData() {

    }
}
