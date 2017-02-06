
package me.kw.mall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.common.uiframe.FramewrokApplication;
import com.android.zcomponent.util.CommonUtil;
import com.xiangying.fighting.R;

import butterknife.Bind;
import butterknife.OnClick;
import me.kw.mall.constant.ProviderResultActivity;

@ZTitleMore(false)
public class ShopIntroduceActivity extends MallBaseActivity {
  @Bind(R.id.edittxt_shop_introduce_show) EditText mEdittxtShopIntroduce;

  @Override protected int getLayoutId() {
    return R.layout.activity_shop_introduce;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("店铺介绍");
    Intent intent = getIntent();
    String desc = intent.getStringExtra("desc");
    if (!TextUtils.isEmpty(desc)) {
      mEdittxtShopIntroduce.setText(desc);
      CommonUtil.setEditViewSelectionEnd(mEdittxtShopIntroduce);
    }
  }

  @OnClick(R.id.btn_shop_introduce_save_click)
  void onClickBtnShopIntroduceSave() {
    Intent intent = new Intent();
    intent.putExtra("desc", mEdittxtShopIntroduce.getText().toString());
    FramewrokApplication.getInstance().setActivityResult(
        ProviderResultActivity.CODE_EDIT_SHOP_DESC, intent);
    finish();
  }
}