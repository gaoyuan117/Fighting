package com.xiangying.fighting.ui.two;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easemob.redpacketui.utils.RedPacketUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.adapter.CommonAdapter;
import com.xiangying.fighting.adapter.ViewHolder;
import com.xiangying.fighting.common.BaseApplication;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.ui.two.findjob.FindJobActivity;
import com.xiangying.fighting.ui.two.findrent.FindRentActivity;
import com.xiangying.fighting.ui.two.gongyi.GongyiListActivity;
import com.xiangying.fighting.ui.two.jianyi.JianyiActivity;
import com.xiangying.fighting.ui.two.jiehun.MarryActivity;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.MyGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.kw.mall.activity.TabHostActivity;


/**
 * Created by xiaoniao on 2016/3/18.
 */
public class TwoFragment extends BaseFragment {
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
  @Bind(R.id.fragment_first_mgv)
  MyGridView fragmentFirstMgv;
  private BaseApplication mContext;

  /**
   * menu功能模块
   */
  ArrayList<FunctionBean> functionBeans = FunctionBean.getDatas();

  private CommonAdapter<FunctionBean> adapter;


  /**
   * 获取fragment新实例
   *
   * @return
   */

  public static Fragment newInstance() {
    Fragment fragment = new TwoFragment();
    return fragment;
  }

  @Override
  protected void process() {
    //不让gridview自动滚动
    fragmentFirstMgv.setFocusable(false);

    adapter = new CommonAdapter<FunctionBean>(getContext(), functionBeans, R.layout.item_mode) {
      @Override
      public void convert(final ViewHolder helper, final FunctionBean item, int position) {
        //设置信息
        helper.setText(R.id.item_mode_tv_mode, item.getFunctionName());

        if (item.getFunctionName().equals("")) {
          helper.setLayoutCanNotClick(R.id.item_mode_ll);
        } else {
          helper.setLayoutCanClick(R.id.item_mode_ll);
        }


        //设置点击事件
        helper.setLayoutClick(R.id.item_mode_ll, new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //选中的选项来设置跳转
            switch (item.getFunctionName()) {
              case "团店"://团店
                startActivity(new Intent(getActivity(), TabHostActivity.class));
                break;
              case "公益"://公益
                startActivity(new Intent(getActivity(), GongyiListActivity.class));
                break;
              case "红包"://红包
                RedPacketUtil.startChangeActivity(getActivity());
                break;
              case "建议和投诉"://建议和投诉
                startActivity(new Intent(getActivity(), JianyiActivity.class));
                break;
              case "结婚吧"://结婚吧
                startActivity(new Intent(getActivity(), MarryActivity.class));
                break;
              case "好租房"://找出租
                startActivity(new Intent(getActivity(), FindRentActivity.class));
                break;
              case "好工作"://好工作
                startActivity(new Intent(getActivity(), FindJobActivity.class));
                break;
              case "系统通知":
                startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));

            }
          }
        });


        //功能头像
        GenericDraweeHierarchy hierarchy = ((SimpleDraweeView) helper.getView(R.id
            .item_mode_sdv_pic)).getHierarchy();
        //选中的选项来设置跳转
        switch (item.getFunctionName()) {
          case "公益"://创店
            hierarchy.setPlaceholderImage(R.drawable.two_gongyi);
            break;
          case "团店"://团店
            hierarchy.setPlaceholderImage(R.drawable.chuangdian);
            break;
          case "红包"://红包
            hierarchy.setPlaceholderImage(R.drawable.two_rp);
            break;
          case "建议和投诉"://建议和投诉
            hierarchy.setPlaceholderImage(R.drawable.two_discuss);
            break;
          case "结婚吧"://结婚吧
            hierarchy.setPlaceholderImage(R.drawable.two_marry);
            break;
          case "好租房"://找出租
            hierarchy.setPlaceholderImage(R.drawable.two_rent);
            break;
          case "好工作"://好工作
            hierarchy.setPlaceholderImage(R.drawable.two_work);
            break;
          case "系统通知":
            hierarchy.setPlaceholderImage(R.drawable.ic_haode_system);
            break;

        }
      }
    };
    fragmentFirstMgv.setAdapter(adapter);

  }

  @Override
  protected void setAllClick() {


  }

  @Override
  protected void initViews(View view) {
    titleBarCommonTvTitle.setText("好的");
    titleBarCommonIvOperate1.setVisibility(View.INVISIBLE);
    titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
  }

  @Override
  protected View loadLayout(LayoutInflater inflater) {
    View view = inflater.inflate(R.layout.fragment_two, null);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  protected void loadNetData() {

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

}
