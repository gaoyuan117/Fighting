package com.xiangying.fighting.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.widget.FontTextView;


public class ViewHolder {

  private final SparseArray<View> mviews;
  private View mconvertView;
  private static Context context;
  //FinalBitmap _FinalBitmap;

  private ViewHolder(Context context, ViewGroup parent, int LayoutId, int position) {
    super();
    this.mviews = new SparseArray<View>();
    this.mconvertView = LayoutInflater.from(context).inflate(LayoutId, null);
    mconvertView.setTag(this);
    //字体设置
//        FontManager.changeFonts((ViewGroup) mconvertView, (Activity) context);
    this.context = context;


  }

  public static ViewHolder get(Context context, View convertView, ViewGroup parent, int LayoutId, int position) {

    if (convertView == null) {
      return new ViewHolder(context, parent, LayoutId, position);
    }

    return (ViewHolder) convertView.getTag();
  }

  public <T extends View> T getView(int id) {

    View view = mviews.get(id);

    if (view == null) {
      view = mconvertView.findViewById(id);
      mviews.put(id, view);
    }
    return (T) view;

  }


  /**
   * 设置item点击监听
   */
  public ViewHolder setItemClick(View.OnClickListener listener) {
    mconvertView.setOnClickListener(listener);
    return this;
  }


  /**
   * 为TextView设置text
   **/
  public ViewHolder setText(int id, String text) {
    View view = getView(id);
    if (view instanceof FontTextView) {
      ((FontTextView) view).setText(text);
    } else {
      ((TextView) view).setText(text);
    }
    return this;
  }

  /**
   * EditText设置编辑坚挺
   **/
  public ViewHolder setOnEditChangeListener(int id, TextWatcher textWatcher) {
    EditText view = getView(id);
    view.addTextChangedListener(textWatcher);
    return this;
  }


  /**
   * gettag
   */
  public String getViewTag(int id) {
    View view = getView(id);
    return (String) view.getTag();
  }


  /**
   * 为EditText设置text
   **/
  public ViewHolder setEditText(int id, String text) {
    EditText view = getView(id);
    view.setText(text);
    return this;
  }

  /**
   * 为ViewsetTag
   */
  public ViewHolder setViewTag(int id, String tag) {
    View view = getView(id);
    view.setTag(tag);
    return this;
  }

  /**
   * 设置背景
   */
  public ViewHolder setBackgroundResource(int resId, int id) {
    View view = getView(resId);
    view.setBackgroundResource(id);
    return this;
  }


  /**
   * 设置背景
   */
  public ViewHolder setCommonAdapter(int resId, BaseAdapter adapter) {
    GridView view = getView(resId);
    view.setAdapter(adapter);
    return this;
  }


  /**
   * 设置GridView的高度
   */
  public ViewHolder setGridViewHeight(int resId) {
    GridView view = getView(resId);
    Utils.setGridViewHeightBasedOnChildren(view);
    return this;
  }


  public ViewHolder setConvertViewBacground(int id) {
    mconvertView.setBackgroundResource(id);
    return this;
  }

  public ViewHolder setGone(int id) {
    View view = getView(id);
    view.setVisibility(View.GONE);
    return this;
  }

  public ViewHolder setVivisble(int id) {
    View view = getView(id);
    view.setVisibility(View.VISIBLE);
    return this;
  }

  public ViewHolder setItemVivisble() {
    mconvertView.setVisibility(View.VISIBLE);
    return this;
  }

  public ViewHolder setItemGone() {
    mconvertView.setVisibility(View.GONE);
    return this;
  }


  public ViewHolder setInVivisble(int id) {
    View view = getView(id);
    view.setVisibility(View.INVISIBLE);
    return this;
  }

  public ViewHolder setTextViewClick(int id, View.OnClickListener listener) {
    TextView view = getView(id);
    view.setOnClickListener(listener);
    return this;
  }

  public ViewHolder setFontTextViewClick(int id, View.OnClickListener listener) {
    FontTextView view = getView(id);
    view.setOnClickListener(listener);
    return this;
  }

  public ViewHolder setViewClick(int id, View.OnClickListener listener) {
    View view = getView(id);
    view.setOnClickListener(listener);
    return this;
  }

  public ViewHolder setLayoutClick(int id, View.OnClickListener listener) {
    ViewGroup view = getView(id);
    view.setOnClickListener(listener);
    return this;
  }

  public ViewHolder setLayoutCanNotClick(int id) {
    ViewGroup view = getView(id);
    view.setClickable(false);
    return this;
  }

  public ViewHolder setLayoutCanClick(int id) {
    ViewGroup view = getView(id);
    view.setClickable(true);
    return this;
  }

  public ViewHolder setViewGroupClick(int id, View.OnClickListener listener) {
    ViewGroup view = getView(id);
    view.setOnClickListener(listener);
    return this;
  }

  public ViewHolder setTextColor(int id, int colorResid) {
    FontTextView view = getView(id);
    int color = context.getResources().getColor(colorResid);
    view.setTextColor(color);
    return this;
  }

  public ViewHolder setIamgeViewClick(int id, View.OnClickListener listener) {
    ImageView view = getView(id);
    view.setOnClickListener(listener);
    return this;
  }

  /**
   * 为TextView设置带有中线的text
   **/
  public ViewHolder setTextWithLine(int id, String text) {
    TextView view = getView(id);
    view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    view.setText(text);
    return this;
  }

  public ViewHolder setTextShowOrHind(int id, int VISIBLE) {
    TextView view = getView(id);
    view.setVisibility(VISIBLE);
    return this;
  }

  public ViewHolder setCheckBox(int id, boolean checked) {
    CheckBox view = getView(id);
    view.setChecked(checked);
    return this;
  }

  /**
   * 为TextView设置text
   **/
  public ViewHolder setImageResource(int id, int resId) {
    ImageView view = getView(id);
    view.setImageResource(resId);
    return this;
  }

  public ViewHolder setImageBitmap(int id, Bitmap bm) {
    ImageView view = getView(id);
    view.setImageBitmap(bm);
    return this;
  }

  public void setSimpleDraweeViewByUrl(int id, String url) {
    final SimpleDraweeView view = getView(id);
    if (TextUtils.isEmpty(url)) {
      return;
    }

    if (!url.startsWith("http")) {
      url = Endpoint.HOST + url;
    }

    Uri uri = Uri.parse(url);
    view.setImageURI(uri);
  }



	
	
	/*public ViewHolder setImageLocal(int id, String dirPath){
        ImageView view = getView(id);
		view.setImageResource(R.drawable.friends_sends_pictures_no);
		ImageLoader.getInstance().loadImage(dirPath, view);
		System.out.println("this is viewholder 0000000000000");
		return this;
	}*/

  public View getConvertView() {
    return mconvertView;
  }

  /**
   * 设置背景颜色资源文件
   *
   * @param id
   * @param resId
   */
  public void setBackgroundRes(int id, int resId) {
    View view = getView(id);
    view.setBackgroundResource(resId);
  }
}
