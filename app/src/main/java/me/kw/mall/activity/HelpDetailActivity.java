package me.kw.mall.activity;

import android.widget.TextView;

import com.android.zcomponent.annotation.ZTitleMore;
import com.android.zcomponent.http.api.model.MessageData;
import com.xiangying.fighting.R;

import butterknife.Bind;
import me.kw.mall.model.CommonValidate;
import me.kw.mall.model.HelpBusiness;
import service.api.ArticleDetail;
import service.entity.HelpService;

@ZTitleMore(false)
public class HelpDetailActivity extends MallBaseActivity {
  @Bind(R.id.tvew_title_show)
  TextView mTvewTitle;

  @Bind(R.id.tvew_desc_show)
  TextView mTvewDesc;

  private String mstrArticleId;

  @Override protected int getLayoutId() {
    return R.layout.activity_help_detail;
  }

  @Override protected void initUI() {
    getTitleBar().setTitleText("问题详情");
    getDataEmptyView().showViewWaiting();
    mstrArticleId = getIntent().getStringExtra("id");
    HelpBusiness.queryArticlesDetail(getHttpDataLoader(), mstrArticleId);
  }

  @Override
  public void onDataEmptyClickRefresh() {
    HelpBusiness.queryArticlesDetail(getHttpDataLoader(), mstrArticleId);
  }

  @Override
  public void onRecvMsg(MessageData msg) throws Exception {
    if (msg.valiateReq(HelpService.ArticlesDetailRequest.class)) {
      ArticleDetail response =
          (ArticleDetail) msg.getRspObject();
      if (CommonValidate.validateQueryState(this, msg, response)) {
        mTvewTitle.setText(response.Data.title);
        mTvewDesc.setText(response.Data.description);

        getDataEmptyView().dismiss();
      } else {
        getDataEmptyView().showViewDataEmpty(true, false, msg, "");
      }
    }
  }
}
