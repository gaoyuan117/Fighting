
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
public class ShopNameActivity extends MallBaseActivity {
  @Bind(R.id.edittxt_shop_name_show) EditText mEdittxtShopName;

  @Override protected int getLayoutId() {
    return R.layout.activity_shop_name;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("店铺名称");
    Intent intent = getIntent();
    String name = intent.getStringExtra("name");
    if (!TextUtils.isEmpty(name)) {
      mEdittxtShopName.setText(name);
      CommonUtil.setEditViewSelectionEnd(mEdittxtShopName);
    }
  }

  @OnClick(R.id.btn_shop_name_save_click)
  void onClickBtnShopNameSave() {
    Intent intent = new Intent();
    intent.putExtra("name", mEdittxtShopName.getText().toString());
    FramewrokApplication.getInstance().setActivityResult(ProviderResultActivity.CODE_EDIT_SHOP_NAME, intent);
    finish();
  }
}