
package me.kw.mall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.TimeUtil;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.MessageItem;

public class MessageAdapter extends CommonAdapter {

  public MessageAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_message, null);
    }

    LinearLayout llayoutContent = findViewById(convertView, R.id.llayout_message_content_show);
    ImageView imgvewHeadPicture = findViewById(convertView, R.id.imgvew_head_picture_show);
    TextView tvewReviewTime = findViewById(convertView, R.id.tvew_review_time_show);
    TextView tvewReviewTitle = findViewById(convertView, R.id.tvew_review_title_show);
    TextView tvewReviewName = findViewById(convertView, R.id.tvew_review_name_show);

    final MessageItem message = (MessageItem) mList.get(position);
    tvewReviewTime.setText(TimeUtil.transformLongTimeFormat(Long.parseLong(message.time) * 1000,
        TimeUtil.STR_FORMAT_DATE_TIME));
    tvewReviewTitle.setText(message.title);
    tvewReviewName.setText(message.message);

    mImageLoader.displayImage(Endpoint.HOST + message.image_path, imgvewHeadPicture, options);

    llayoutContent.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("id", message.id);
//        intentToActivity(bundle, MyMessageDetailActivity_.class);
      }
    });
    return convertView;
  }
}