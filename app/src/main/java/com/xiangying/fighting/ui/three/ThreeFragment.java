package com.xiangying.fighting.ui.three;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.chat.help.PreferenceManager;
import com.xiangying.fighting.ui.login.LoginActivity;
import com.xiangying.fighting.ui.three.bean.UserBaseInfo;
import com.xiangying.fighting.ui.three.company.CompanyActivity;
import com.xiangying.fighting.ui.three.selfinfo.AllNoteActivity;
import com.xiangying.fighting.ui.three.selfinfo.SelfInfoActivity;
import com.xiangying.fighting.ui.three.selfinfo.UserAlbumActivity;
import com.xiangying.fighting.ui.three.selfinfo.UserVedioActivity;
import com.xiangying.fighting.ui.three.setting.SettingActivity;
import com.xiangying.fighting.ui.three.zichan.ZichanActivity;
import com.xiangying.fighting.ui.two.jiehun.WanshanActivity;
import com.xiangying.fighting.utils.GetLocalKey;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.kw.mall.activity.BuyerActivity;
import me.kw.mall.activity.MyShopActivity;


/**
 * Created by xiaoniao on 2016/3/18.
 */
public class ThreeFragment extends BaseFragment {
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
    @Bind(R.id.fragment_three_sdv_head)
    SimpleDraweeView fragmentThreeSdvHead;
    @Bind(R.id.fragment_three_tv_nickname)
    FontTextView fragmentThreeTvNickname;
    @Bind(R.id.fragment_three_tv_zhandouhao)
    FontTextView fragmentThreeTvZhandouhao;
    @Bind(R.id.fragment_three_tv_zichan)
    TextView fragmentThreeTvZichan;
    @Bind(R.id.fragment_three_ll_zichan)
    LinearLayout fragmentThreeLlZichan;
    @Bind(R.id.fragment_three_tv_shiping)
    TextView fragmentThreeTvShiping;
    @Bind(R.id.fragment_three_ll_shiping)
    LinearLayout fragmentThreeLlShiping;
    @Bind(R.id.fragment_three_tv_xiangce)
    TextView fragmentThreeTvXiangce;
    @Bind(R.id.fragment_three_ll_xiangce)
    LinearLayout fragmentThreeLlXiangce;
    @Bind(R.id.fragment_three_tv_rizhi)
    TextView fragmentThreeTvRizhi;
    @Bind(R.id.fragment_three_ll_rizhi)
    LinearLayout fragmentThreeLlRizhi;
    @Bind(R.id.fragment_three_tv_shezhi)
    TextView fragmentThreeTvShezhi;
    @Bind(R.id.fragment_three_ll_shezhi)
    LinearLayout fragmentThreeLlShezhi;
    @Bind(R.id.fragment_three_ll_selfinfo)
    RelativeLayout fragment_three_ll_selfinfo;
    @Bind(R.id.btn_three_exit)
    Button mExit;

    //我是卖家
    @OnClick(R.id.fragment_three_ll_seller)
    void onClickSellerLl() {
        if (!BaseApplication.isLogin()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getActivity(), MyShopActivity.class));
    }

    //我是买家
    @OnClick(R.id.fragment_three_ll_buyer)
    void onClickBuyerLl() {
        if (!BaseApplication.isLogin()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        startActivity(new Intent(getActivity(), BuyerActivity.class));
    }

    @OnClick({R.id.fragment_three_ll_company, R.id.fragment_three_ll_marry, R.id.fragment_three_ll_rent, R.id.btn_three_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_three_ll_company:
                //我的企业，找工作管理
                startActivity(new Intent(getActivity(), CompanyActivity.class));
                break;
            case R.id.fragment_three_ll_marry:
                //结婚吧资料管理
                startActivity(new Intent(getActivity(), WanshanActivity.class));
                break;
            case R.id.fragment_three_ll_rent:
                //出租房管理
                break;

            case R.id.btn_three_exit:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                GetLocalKey.setToken("");
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    public static Fragment newInstance() {
        Fragment fragment = new ThreeFragment();
        return fragment;
    }


    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        //资产
        fragmentThreeLlZichan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), ZichanActivity.class));
            }
        });
        //个人信息
        fragment_three_ll_selfinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), SelfInfoActivity.class));
            }
        });
        //相册
        fragmentThreeLlXiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), UserAlbumActivity.class));
            }
        });

        fragmentThreeLlShiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, UserVedioActivity.class));
            }
        });

        fragmentThreeLlRizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(mActivity, UserNoteActivity.class));
                startActivity(new Intent(mActivity, AllNoteActivity.class));
            }
        });

        fragmentThreeTvShezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SettingActivity.class));
            }
        });
    }

    @Override
    protected void initViews(View view) {
        titleBarCommonTvTitle.setText("我");
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
        titleBarCommonIvOperate1.setVisibility(View.INVISIBLE);

    }

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_three, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadNetData() {
//    loadUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 加载数据
     */
    public void loadUserInfo() {
        XUtilsHelper<UserBaseInfo> helper = new XUtilsHelper<>(getApplication(), NetworkTools.GET_USER_INFO_SELF, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    UserBaseInfo userBaseInfo = (UserBaseInfo) msg.obj;

                    //设置信息
                    fragmentThreeTvNickname.setText(userBaseInfo.getData().getNickname());
                    fragmentThreeTvZhandouhao.setText("战斗号：" + userBaseInfo.getData().getUsername());
                    fragmentThreeTvZichan.setText(TextUtils.isEmpty(userBaseInfo.getData().getAmount()) ? "￥ 0.00" : "￥ " + userBaseInfo.getData().getAmount());
                    GetLocalKey.setNickName(userBaseInfo.getData().getNickname());
                    GetLocalKey.setUid(userBaseInfo.getData().getUid());
                    //头像
                    if (!"".equals(userBaseInfo.getData().getAvatar_path())) {
                        fragmentThreeSdvHead.setImageURI(Uri.parse(Endpoint.HOST + userBaseInfo.getData().getAvatar_path()));
                    }
                    savaMyInfo(userBaseInfo);
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(UserBaseInfo.class);
    }

    private void savaMyInfo(UserBaseInfo userBaseInfo) {
        PreferenceManager.getInstance().setCurrentUserNick(userBaseInfo.getData().getNickname());
        PreferenceManager.getInstance().setCurrentUserAvatar(Endpoint.HOST + userBaseInfo.getData().getAvatar_path());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
