package com.xiangying.fighting.ui.three.selfinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.UserAllNoteAdapter;
import com.xiangying.fighting.bean.AllNoteBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.bottomlialog.BottomDialog;
import com.xiangying.fighting.widget.pulltorefreshview.PullToRefreshView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AllNoteActivity extends BaseActivity
        implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView titleBarCommonIvOperate2;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.common_listview_show)
    ListView commonListviewShow;
    @Bind(R.id.common_pull_refresh_view_show)
    PullToRefreshView commonPullRefreshViewShow;

    UserAllNoteAdapter adapter;
    ArrayList<AllNoteBean.DataBean> dataList = new ArrayList<>();
    private String uid;

    private ProgressDialog progressDialog;

    @Override
    protected void process() {
    }

    @Override
    protected void setAllClick() {
        commonPullRefreshViewShow.setOnFooterRefreshListener(this);
        commonPullRefreshViewShow.setOnHeaderRefreshListener(this);

        //发布新照片
        titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(AllNoteActivity.this, PublishNewNoteActivity.class));
                final BottomDialog dialog = BottomDialog.create(getSupportFragmentManager());
                dialog.setLayoutRes(R.layout.dialog_note_option)
                        .setCancelOutside(true)
                        .setViewListener(new BottomDialog.ViewListener() {
                            @Override
                            public void bindView(View rootView) {
                                rootView.findViewById(R.id.tv_upload_image).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(AllNoteActivity.this, PublishNewAlbumActivity.class));
                                        dialog.dismiss();
                                    }
                                });

                                rootView.findViewById(R.id.tv_upload_video).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(AllNoteActivity.this, PublishNewVedioActivity.class));
                                        dialog.dismiss();
                                    }
                                });

                                rootView.findViewById(R.id.tv_upload_note).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(AllNoteActivity.this, PublishNewNoteActivity.class));
                                        dialog.dismiss();
                                    }
                                });

                                rootView.findViewById(R.id.tv_upload_cancel).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("日记");
        titleBarCommonIvOperate2.setImageResource(R.drawable.fabu);
        titleBarCommonIvOperate3.setImageResource(R.drawable.icon_plus);
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(uid)) {
            titleBarCommonIvOperate2.setVisibility(View.GONE);
            titleBarCommonIvOperate3.setVisibility(View.GONE);
        } else {
            titleBarCommonIvOperate2.setVisibility(View.VISIBLE);
            titleBarCommonIvOperate3.setVisibility(View.VISIBLE);
        }

        titleBarCommonIvOperate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu pm = new PopupMenu(AllNoteActivity.this, titleBarCommonIvOperate2);
                getMenuInflater().inflate(R.menu.choose, pm.getMenu());

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ok:
                                setAuth("1");
                                break;
                            case R.id.no:
                                setAuth("0");
                                break;
                        }
                        return false;
                    }
                });
                pm.show();
            }
        });

        commonPullRefreshViewShow.setFooterRefreshComplete();
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        uid = getIntent().getStringExtra("uid");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中..");
        progressDialog.show();
    }

    @Override
    protected void loadNetData() {
        requestData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestData();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.ME_ALL_NOTE, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if (commonPullRefreshViewShow.isHeaderRefresh()) {
                    commonPullRefreshViewShow.onHeaderRefreshComplete();
                }
                try {
                    AllNoteBean userNoteBean = (AllNoteBean) msg.obj;
                    if (userNoteBean.getData() != null && userNoteBean.getData().size() > 0) {
                        dataList = (ArrayList<AllNoteBean.DataBean>) userNoteBean.getData();
                        adapter = new UserAllNoteAdapter(AllNoteActivity.this, dataList);
                        commonListviewShow.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


                return false;
            }
        }));

        if (TextUtils.isEmpty(uid)) {//获取好友数据
            helper.setRequestParams(new String[][]{{"uid", GetLocalKey.getUid()}});
        } else {//获取个人数据

            helper.setRequestParams(new String[][]{{"uid", uid}});
        }
        helper.sendPostAuto(AllNoteBean.class);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        requestData();
    }


    /**
     * 设置权限
     *
     * @param type
     */
    private void setAuth(String type) {
        XUtilsHelper helper = new XUtilsHelper<>(getApplication(), NetworkTools.AUTH, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean renZhengBean = (RenZhengBean) msg.obj;
                Toast.makeText(AllNoteActivity.this, renZhengBean.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"type", type}});
        helper.sendPostAuto(RenZhengBean.class);

    }
}

