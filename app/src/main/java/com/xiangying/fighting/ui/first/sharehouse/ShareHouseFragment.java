package com.xiangying.fighting.ui.first.sharehouse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ShareHouseAdapter;
import com.xiangying.fighting.bean.ShareHouseBean;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.utils.DateUtils;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.RefreshHandler;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.bottomlialog.BottomDialog;
import com.xiangying.fighting.widget.pullableview.PullToRefreshLayout;
import com.xiangying.fighting.widget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class ShareHouseFragment extends BaseFragment
        implements PullToRefreshLayout.OnRefreshListener {
    @Bind(R.id.fragmenttalk_sharehouse)
    PullableListView fragmenttalkSharehouse;
    @Bind(R.id.common_pull_refresh_view_show)
    PullToRefreshLayout commonPullRefreshViewShow;

    ArrayList<ShareHouseBean.Data> listData = new ArrayList<>();
    ShareHouseAdapter adapter;

    private String type = "1";
    private int pageIndex = 1, pageSize = 10;
    private ProgressDialog dialog;

    public static Fragment newInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        Fragment fragment = new ShareHouseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void process() {
        mImlRefreshHandler = new ShareHouseFragment.ImlRefreshHandler(commonPullRefreshViewShow);
    }

    @Override
    protected void setAllClick() {
        commonPullRefreshViewShow.setOnRefreshListener(this);
        adapter.setItemListener(new ShareHouseAdapter.ItemListener() {
            @Override
            public void onClickZan(ShareHouseBean.Data data, int position) {
                itemZan(data, position);
            }

            @Override
            public void onClickComment(ShareHouseBean.Data data, int position) {
                itemComment(data, position);
            }

            @Override
            public void onClickMoreComment(ShareHouseBean.Data data) {
                ShareHouseDetailActivity.start(mActivity, Integer.parseInt(data.getId()));
            }
        });
    }

    private void itemComment(final ShareHouseBean.Data data, final int position) {
        final BottomDialog dialog = BottomDialog.create(getChildFragmentManager());
        dialog.setLayoutRes(R.layout.dialog_edit_text)
                .setCancelOutside(true)
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View rootView) {
                        final EditText editText = (EditText) rootView.findViewById(R.id.edit_text);
                        TextView textView = (TextView) rootView.findViewById(R.id.commit_tv);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                                    Utils.toast(mActivity, "请输入评论内容");
                                    return;
                                }
                                XUtilsHelper xUtilsHelper = new XUtilsHelper(mActivity, NetworkTools.SHARE_COMMENT, new Handler(new Handler.Callback() {
                                    @Override
                                    public boolean handleMessage(Message msg) {
                                        if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {

                                            if (data.getComment() == null) {
                                                ArrayList<ShareHouseBean.Comment> comments = new ArrayList<ShareHouseBean.Comment>();
                                                data.setComment(comments);
                                            }
                                            ShareHouseBean.Comment comment = new ShareHouseBean.Comment();
                                            comment.setNickname(GetLocalKey.getNickName());
                                            comment.setText(editText.getText().toString().trim());
                                            data.getComment().add(comment);
                                            adapter.setData(position, data);
                                            dialog.dismiss();
                                        }
                                        return false;
                                    }
                                }));
                                xUtilsHelper.setRequestParams(new String[][]{{"e_id", data.getId()}, {"text", editText.getText().toString().trim()}});
                                xUtilsHelper.sendPostAuto(CommonReturn.class);
                            }
                        });
                    }
                });
        dialog.show();
    }

    private void itemZan(final ShareHouseBean.Data data, final int position) {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(mActivity, NetworkTools.SHARE_ZAN, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (Utils.valiueCommonReture((CommonReturn) msg.obj)) {
                    if (!TextUtils.isEmpty(data.getPraise())) {
                        data.setPraise(data.getPraise() + "," + GetLocalKey.getNickName());
                    } else {
                        data.setPraise(GetLocalKey.getNickName());
                    }
                    adapter.setData(position, data);
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{{"e_id", data.getId()}});
        xUtilsHelper.sendPostAuto(CommonReturn.class);
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getString("type");
        adapter = new ShareHouseAdapter(context, listData);
        commonPullRefreshViewShow.setCanPullDown(true);
        commonPullRefreshViewShow.setCanPullUp(true);
        fragmenttalkSharehouse.setAdapter(adapter);
    }

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragmentcommunity, null);
        ButterKnife.bind(this, view);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载..");
        return view;
    }

    @Override
    protected void loadNetData() {

    }

    private void loadData(String page) {
        XUtilsHelper helper = new XUtilsHelper(context, NetworkTools.SHARE_LIST, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    ShareHouseBean shareHouseBean = (ShareHouseBean) msg.obj;
                    if (shareHouseBean.getCode() == 1 && shareHouseBean.getData() != null && shareHouseBean.getData().size() > 0) {
                        adapter.addDatas(shareHouseBean.getData());
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 1000);
                        if (shareHouseBean.getData() != null && shareHouseBean.getData().size() < 10) {
                            isEnd = true;
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }
                commonPullRefreshViewShow.refreshFinish(DateUtils.getTimestampString(new Date(System.currentTimeMillis())));
                commonPullRefreshViewShow.loadmoreFinish(1);
                return false;
            }
        }));

        helper.setRequestParams(new String[][]{
                {"is_energy", type},
                {"page", page + ""},
                {"pageSize", pageSize + ""}
        });
        helper.sendPostAuto(ShareHouseBean.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        mImlRefreshHandler.onPullDownToRefresh();
    }

    @Override
    public void onLoadMore() {
        mImlRefreshHandler.onPullUpToRefresh(isEnd);
    }

    private ShareHouseFragment.ImlRefreshHandler mImlRefreshHandler;
    private boolean isEnd = false;

    private class ImlRefreshHandler extends RefreshHandler {

        public ImlRefreshHandler(PullToRefreshLayout pullToRefreshLayout) {
            super(pullToRefreshLayout);
        }

        @Override
        public void loadNextData(int page) {
            if (page == 1) {
                adapter.removeAll();
                loadData(1 + "");
            } else {
                loadData(page + "");
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            dialog.show();
            loadData(1 + "");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImlRefreshHandler.onActivityDestory();
    }
}
