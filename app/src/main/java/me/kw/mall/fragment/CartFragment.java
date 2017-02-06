
package me.kw.mall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.android.zcomponent.http.api.model.MessageData;
import com.android.zcomponent.http.constant.ErrorCode;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.android.zcomponent.util.CommonUtil;
import com.android.zcomponent.util.ShowMsg;
import com.android.zcomponent.util.StringUtil;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.activity.CartActivity;
import me.kw.mall.activity.OrderEnsureActivity;
import me.kw.mall.adapter.CartAdapter;
import me.kw.mall.constant.ResultActivity;
import me.kw.mall.model.CartBusiness;
import me.kw.mall.model.CommonValidate;
import service.api.CartItem;
import service.api.CartPageResult;
import service.api.CartTradeResult;
import service.api.CommonReturn;
import service.entity.CartService;

/**
 * <p>
 * Description: 购物车
 * </p>
 */
public class CartFragment extends MallBaseFragment implements
    CartAdapter.EditProductClickListener {
//  @Bind(R.id.imgvew_select_show) TextView mImgvewSelect;
//  @Bind(R.id.tvew_settle_show) TextView mTvewSettle;
  @Bind(R.id.tvew_total_money_show) TextView mTvewTotalMoney;
  @Bind(R.id.tvew_select_show_click) TextView mtvewSelectAll;
  @Bind(R.id.common_listview_show) ExpandableListView mListView;

  private List<CartItem> mlistCartItems = null;
  private CartAdapter mCartAdapter;
  private int groupPosition;
  private int childPosition;
  private boolean isAdd;
  private boolean isRemove;

  @Override protected int getLayoutId() {
    return R.layout.fragment_cart;
  }

  protected void initUI() {
    mListView.setGroupIndicator(null);
    mListView.setOnGroupClickListener(new OnGroupClickListener() {
      @Override
      public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        mListView.expandGroup(groupPosition);
        return true;
      }
    });
    getDataEmptyView().setBackgroundResource(R.drawable.transparent);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (BaseApplication.isLogin()) {
      CartBusiness.queryCarts(getHttpDataLoader(), 1);
      getDataEmptyView().showViewWaiting();
    } else {
      getDataEmptyView().showViewDataEmpty(false, false, true, 1, "还未登录", "去登录");
    }
  }

  /**
   * Fragment显示切换的时候调用该方法。
   */
  @Override public void onHiddenChanged(boolean hidden) {
    if (!hidden) {
      if (BaseApplication.isLogin()) {
        CartBusiness.queryCarts(getHttpDataLoader(), 1);
      }
    }
  }

  @Override public void onDataEmptyClickChange() {
    BaseApplication.intentToLoginActivity(getActivity());
  }

  @Override public void onLoginSuccess() {
    CartBusiness.queryCarts(getHttpDataLoader(), 1);
    getDataEmptyView().showViewWaiting();
  }

  @Override
  public void onActivityResultCallBack(int resultCode, Intent intent) {
    if (resultCode == ResultActivity.CODE_ADD_ORDER) {
      CartBusiness.queryCarts(getHttpDataLoader(), 1);
      getDataEmptyView().showViewWaiting();
    }
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(CartService.QueryCartsRequest.class)) {
      CartPageResult response = msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        if (null != response.Data && response.Data.Result.length > 0) {
          mlistCartItems = new ArrayList<>(Arrays.asList(response.Data.Result));
          getDataEmptyView().setVisibility(View.GONE);
        } else {
          if (null != mlistCartItems) {
            mlistCartItems.clear();
          }
          getDataEmptyView().showViewDataEmpty(false, false, msg, "购物车空空如也");
        }

        if (null == mCartAdapter) {
          mCartAdapter = new CartAdapter(getActivity(), mlistCartItems);
          mCartAdapter.setEditProductClickListener(this);
          mListView.setAdapter(mCartAdapter);
          int groupCount = mListView.getCount();
          for (int i = 0; i < groupCount; i++) {
            mListView.expandGroup(i);
          }
        } else {
          mCartAdapter.refreshAdapterData(mlistCartItems);
//                    mCartAdapter.notifyDataSetChanged();
          for (int i = 0; i < mCartAdapter.getGroupCount(); i++) {
            mListView.expandGroup(i);
          }
        }

        showTotalMoney();
      } else {
        getDataEmptyView().showViewDataEmpty(false, false, msg, "购物车空空如也");
      }
    } else if (msg.valiateReq(CartService.DelCartByIdRequest.class)) {
      CommonReturn response = (CommonReturn) msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          CartItem.Data[] products = CartBusiness.removeProduct(
              mlistCartItems.get(groupPosition).product,
              childPosition);
          if (null != products) {
            mlistCartItems.get(groupPosition).product = products;
          } else {
            mlistCartItems.remove(groupPosition);
          }

          if (mlistCartItems.size() == 0) {
            getDataEmptyView().showViewDataEmpty(false, false, msg,
                "购物车空空如也");
          }

          mCartAdapter.notifyDataSetChanged();

          showTotalMoney();
        } else {
          ShowMsg.showToast(getActivity(), msg, response.message);
        }
      } else {
        ShowMsg.showToast(getActivity(), msg, "删除失败");
      }
    } else if (msg.valiateReq(CartService.EditCartRequest.class)) {
      CommonReturn response = msg.getRspObject();
      if (null != response) {
        if (response.code == ErrorCode.INT_QUERY_DATA_SUCCESS) {
          CartItem.Data product =
              mlistCartItems.get(groupPosition).product[childPosition];
          product.quantity =
              CartBusiness.editQuantity(product, isAdd, isRemove)
                  + "";
          product.total_price = product.price.multiply(new BigDecimal(product.quantity));
          CartItem cartItem = mlistCartItems.get(groupPosition);
          cartItem.product[childPosition] = product;
          mlistCartItems.set(groupPosition, cartItem);
          mCartAdapter.refreshAdapterData(mlistCartItems);
          mCartAdapter.notifyDataSetChanged();

          showTotalMoney();
        } else {
          ShowMsg.showToast(getActivity(), msg, response.message);
        }
      } else {
        ShowMsg.showToast(getActivity(), msg, "编辑失败");
      }
    } else if (msg.valiateReq(CartService.ToTradeRequest.class)) {
      //在购物车中先加入订单 ->
      CartTradeResult response = (CartTradeResult) msg.getRspObject();
      if (CommonValidate.validateQueryState(getActivity(), msg, response)) {
        Bundle bundle = new Bundle();
        bundle.putString("product", JsonSerializerFactory.Create().encode(response.Data));
        getIntentHandle().intentToActivity(bundle, OrderEnsureActivity.class);

        if (getActivity() instanceof CartActivity) {
          getActivity().finish();
        }
      } else {
        ShowMsg.showToast(getActivity(), msg, "结算失败");
      }
    }
  }

  private void showTotalMoney() {
    BigDecimal totalPrice = CartBusiness.getTotalMoney(mlistCartItems);
    mTvewTotalMoney.setText("合计：¥" + StringUtil.formatProgress(totalPrice));
    if (null != mCartAdapter) {
      mCartAdapter.refreshAdapterData(mlistCartItems);
    }
    if (null == mlistCartItems || mlistCartItems.size() == 0) {
      showAllSelectBtnState(false);
    }
  }

  private void showAllSelectBtnState(boolean isSelectAll) {
    if (isSelectAll) {
      CommonUtil.setDrawableLeft(getActivity(), mtvewSelectAll,
          R.drawable.cart_option_on);
    } else {
      CommonUtil.setDrawableLeft(getActivity(), mtvewSelectAll,
          R.drawable.cart_option);
    }
  }

  @OnClick(R.id.tvew_select_show_click)
  void onClickSelectAll() {
    boolean isSelectAll = CartBusiness.selectAllCartProduct(mlistCartItems);
    showAllSelectBtnState(isSelectAll);
    showTotalMoney();
  }

  @OnClick(R.id.tvew_settle_show_click)
  void onClickTvewSettel() {
    Integer[] cartIds = CartBusiness.getAllSelectCartId(mlistCartItems);
    if (null != cartIds) {
      CartBusiness.toTrade(getHttpDataLoader(), cartIds);
      showWaitDialog(1, false, R.string.common_submit_data);
    } else {
      ShowMsg.showToast(getActivity(), "请选择需购买的商品");
    }
  }

  @Override
  public void onClickEditProduct(int groupPosition, int childPosition,
                                 boolean isAdd, boolean isRemove) {
    this.groupPosition = groupPosition;
    this.childPosition = childPosition;
    this.isAdd = isAdd;
    this.isRemove = isRemove;

    CartItem.Data product =
        mlistCartItems.get(groupPosition).product[childPosition];
    if (isRemove) {
      int cartId = Integer.parseInt(product.cart_id);
      CartBusiness.delToCart(getHttpDataLoader(), cartId);
      showWaitDialog(1, false, R.string.common_submit_data);
    } else {
      int quantity = CartBusiness.editQuantity(product, isAdd, isRemove);

      if (quantity > 0) {
        CartBusiness.editCart(getHttpDataLoader(), product.price,
            Integer.parseInt(product.cart_id), quantity,
            product.option_ids);
        showWaitDialog(1, false, R.string.common_submit_data);
      } else {
        ShowMsg.showToast(getActivity(), "再减就没了哦");
      }
    }
  }

  @Override
  public void onClickSelectProduct(int groupPosition, int childPosition,
                                   boolean isSelectAll) {
    CartBusiness.selectCartProduct(mlistCartItems, groupPosition, childPosition, isSelectAll);
    showAllSelectBtnState(CartBusiness.isAllCartProductSelected(mlistCartItems));
    showTotalMoney();
  }
}