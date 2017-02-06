
package me.kw.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.common.uiframe.BaseActivity;
import com.android.zcomponent.json.JsonSerializerFactory;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseApplication;

import java.util.List;

import me.kw.mall.activity.MyAddressEditActivity;
import me.kw.mall.activity.OrderEnsureActivity;
import me.kw.mall.constant.ResultActivity;
import service.api.Address;

public class MyAddressAdapter extends CommonAdapter {

  public MyAddressAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_my_address, null);
    }

    final Address.Data address = (Address.Data) mList.get(position);

    TextView tvewPersonal = findViewById(convertView, R.id.tvew_personal_show);
    TextView tvewPhone = findViewById(convertView, R.id.tvew_phone_show);

    //收货地址
    TextView tvewAddress = findViewById(convertView, R.id.tvew_address_show);

    tvewPersonal.setText(address.realname);
    tvewPhone.setText(address.mobile);

    String addressInfo = address.province + address.city + address.area + address.address;

    if ("1".equals(address.is_default)) {
      tvewAddress.setText("[默认] " + addressInfo);
    } else {
      tvewAddress.setText(addressInfo);
    }
    convertView.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        if (((BaseActivity) mContext).getIntentHandle().isIntentFrom(OrderEnsureActivity.class)) {
          Intent intent = new Intent();
          intent.putExtra("address", JsonSerializerFactory.Create().encode(address));
          BaseApplication.getInstance().setActivityResult(ResultActivity.CODE_ADDRESS_SELECT, intent);
          ((BaseActivity) mContext).finish();
        } else {
          Bundle bundle = new Bundle();
          bundle.putString("address", JsonSerializerFactory.Create().encode(address));
          intentToActivity(bundle, MyAddressEditActivity.class);
        }
      }
    });

    return convertView;
  }
}