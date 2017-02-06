
package me.kw.mall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.android.zcomponent.util.TimeUtil;
import com.xiangying.fighting.R;

import java.util.List;

import service.api.CourseItem;

public class CourseDocAdapter extends CommonAdapter {

  public CourseDocAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_course_doc, null);
    }

    TextView tvewTitle = findViewById(convertView, R.id.tvew_title_show);
    TextView tvewTime = findViewById(convertView, R.id.tvew_time_show);

    final CourseItem courseItem = (CourseItem) mList.get(position);

    tvewTitle.setText(courseItem.title);
    tvewTime.setText(TimeUtil.transformLongTimeFormat(
        Long.parseLong(courseItem.create_time) * 1000,
        TimeUtil.STR_FORMAT_DATE_TIME2));
    convertView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("id", courseItem.id);
//        intentToActivity(bundle, CourseDocDetailActivity_.class);
      }
    });
    return convertView;
  }
}