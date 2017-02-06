
package me.kw.mall.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zcomponent.adapter.CommonAdapter;
import com.xiangying.fighting.R;

import java.util.List;

import me.kw.mall.activity.HelpDetailActivity;
import service.api.ArticleInfo;
import service.api.ArticleItem;

public class ArticlesAdapter extends CommonAdapter {

  public ArticlesAdapter(Context context, List<?> list) {
    super(context, list);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = layoutInflater.inflate(R.layout.adapter_articles, null);
    }
    TextView tvewReviewTitle = findViewById(convertView, R.id.tvew_title_show);
    LinearLayout llayoutContent = findViewById(convertView, R.id.llayout_content);
    final ArticleItem category = (ArticleItem) mList.get(position);
    getContentView(position, llayoutContent, category._child);
    tvewReviewTitle.setText(category.title);
    return convertView;
  }

  private void getContentView(int position, LinearLayout linearLayouts, ArticleInfo[]
      articleInfos) {
    linearLayouts.removeAllViews();

    for (int i = 0; i < articleInfos.length; i++) {
      View view = layoutInflater.inflate(R.layout.adapter_articles_child, null);
      TextView tvewReviewTitle = (TextView) view.findViewById(R.id.tvew_title_show);
      tvewReviewTitle.setText(articleInfos[i].title);
      view.setOnClickListener(new ContentClickListener(position, i));
      linearLayouts.addView(view);
    }
  }

  private class ContentClickListener implements View.OnClickListener {
    private int groupPosition;
    private int childPosition;

    public ContentClickListener(int groupPosition, int childPosition) {
      this.groupPosition = groupPosition;
      this.childPosition = childPosition;
    }

    @Override public void onClick(View v) {
      final ArticleItem category = (ArticleItem) mList.get(groupPosition);
      Bundle bundle = new Bundle();
      bundle.putString("id", category._child[childPosition].id);
      intentToActivity(bundle, HelpDetailActivity.class);
    }
  }
}