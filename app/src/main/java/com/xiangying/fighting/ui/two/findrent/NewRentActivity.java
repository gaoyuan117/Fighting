package com.xiangying.fighting.ui.two.findrent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.zcomponent.http.api.host.Endpoint;
import com.android.zcomponent.util.CommonUtil;
import com.jock.pickerview.view.OptionsPickerView;
import com.orhanobut.logger.Logger;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.bean.MyRentBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.ui.three.zichan.RechargeActivity;
import com.xiangying.fighting.ui.two.findrent.bean.HasRent;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.widget.MyGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import service.api.CommonReturn;

@RuntimePermissions
public class NewRentActivity extends BaseActivity {
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
    @Bind(R.id.activity_new_rent_gv_images)
    MyGridView activityNewRentGvImages;
    @Bind(R.id.activity_new_rent_et_name)
    ClearEditText activityNewRentEtName;
    @Bind(R.id.activity_new_rent_et_price)
    ClearEditText activityNewRentEtPrice;
    @Bind(R.id.activity_new_rent_tv_tingshi)
    FontTextView activityNewRentTvTingshi;
    @Bind(R.id.activity_new_rent_ll_tingshi)
    LinearLayout activityNewRentLlTingshi;
    @Bind(R.id.activity_new_rent_et_area)
    ClearEditText activityNewRentEtArea;
    @Bind(R.id.activity_new_rent_et_floor)
    ClearEditText activityNewRentEtFloor;
    @Bind(R.id.activity_new_rent_et_total_floor)
    ClearEditText activityNewRentEtTotalFloor;
    @Bind(R.id.activity_new_rent_et_xiaoqu)
    ClearEditText activityNewRentEtXiaoqu;
    @Bind(R.id.activity_new_rent_et_address)
    ClearEditText activityNewRentEtAddress;
    @Bind(R.id.activity_new_rent_et_describ)
    EditText activityNewRentEtDescrib;
    @Bind(R.id.activity_new_rent_tv_publish)
    FontTextView activityNewRentTvPublish;
    @Bind(R.id.activity_new_rent_tv_delete)
    FontTextView activityNewRentTvDelete;

    public int REQUEST_IMAGE = 10001;

    ArrayList<String> selectImagePath = new ArrayList<>();
    ImageGridAdapter imageGridAdapter;
    OptionsPickerView pvOptionsTingshi;
    ArrayList<String> tingshis = new ArrayList<>();
    private XUtilsHelper myRentHelper;
    private XUtilsHelper mUploadImageHelper;
    private XUtilsHelper mIsFirstHelper;
    private MyRentBean mMyRentBean;
    private MyRentBean.Data data;

    private AlertDialog mDialog;
    private String type;
    private String url;
    private String id;

    @Override
    protected void process() {
        selectImagePath.add("");
        imageGridAdapter = new ImageGridAdapter(selectImagePath, this);
        activityNewRentGvImages.setAdapter(imageGridAdapter);
        //已有的发布信息
        data = (MyRentBean.Data) getIntent().getSerializableExtra("data");
        type = getIntent().getStringExtra("type");
        if (data != null) {
            showRendInfo(data);
            id = data.getId();
        }


        activityNewRentGvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (selectImagePath.size() - 1)) {
                    NewRentActivityPermissionsDispatcher.chooseImagesWithCheck(NewRentActivity.this);
                }
            }
        });

        tingshis.add("一室");
        tingshis.add("两室");
        tingshis.add("三室");
        tingshis.add("四室");
        tingshis.add("四室以上");

        pvOptionsTingshi = new OptionsPickerView(this);
        pvOptionsTingshi.setPicker(tingshis);
        pvOptionsTingshi.setCyclic(false);
        pvOptionsTingshi.setSelectOptions(0);

        //设置监听
        pvOptionsTingshi.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
                activityNewRentTvTingshi.setText(tingshis.get(options1));
            }
        });
    }

    @Override
    protected void setAllClick() {
        titleBarCommonTvTitle.setText("发布出租");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityNewRentLlTingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptionsTingshi.show();
            }
        });
        activityNewRentTvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtil.isLeastSingleClick()) {
                    return;
                }

                if (mMyRentBean == null && selectImagePath.size() == 1) {
                    Utils.toast(NewRentActivity.this, "请选择图片");
                    return;
                }
                addRent();
            }
        });

        //删除发布
        activityNewRentTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data == null) {
                    return;
                }
                delete();
            }
        });
    }

    /**
     *  增加发布出租
     */
    private void addRent() {
        mUploadImageHelper = new XUtilsHelper(this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    ImageFileBean imageFileBean = (ImageFileBean) msg.obj;
                    if (imageFileBean.getCode() == 1) {
                        if (!TextUtils.isEmpty(type) && type.equals("my")) {
                            url = NetworkTools.RENT_EDIT;
                            commit(imageFileBean.getData());
                        } else {
                            url = NetworkTools.RENT_NEW;
                            isFirst(imageFileBean.getData());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        mUploadImageHelper.setFileRequestParames2(selectImagePath);
        mUploadImageHelper.sendPostAuto(ImageFileBean.class);
    }

    @Override
    protected void initViews() {
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_new_rent);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    /**
     * 显示信息
     *
     * @param myRentBean
     */
    private void showRendInfo(MyRentBean.Data myRentBean) {
        activityNewRentTvDelete.setVisibility(View.VISIBLE);

        if (myRentBean.getImgPath().size() > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (String img : myRentBean.getImgPath()) {
                list.add(Endpoint.HOST + img);
            }
            list.add("");
            imageGridAdapter.setImageList(list);
        }
        activityNewRentEtName.setText(myRentBean.getTitle());
        activityNewRentEtPrice.setText(myRentBean.getPrice());
        activityNewRentTvTingshi.setText(myRentBean.getHall_room());
        activityNewRentEtArea.setText(myRentBean.getArea() + "平米");
        activityNewRentEtFloor.setText(myRentBean.getFloor());
        activityNewRentEtTotalFloor.setText(myRentBean.getHeight());
        activityNewRentEtXiaoqu.setText(myRentBean.getQu_name());
        activityNewRentEtAddress.setText(myRentBean.getQu_site());
        activityNewRentEtDescrib.setText(myRentBean.getDescription());
    }

    /**
     * 查看图库文件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void chooseImages() {
        boolean showCamera = true;
        int maxNum = 9;

        MultiImageSelector selector = MultiImageSelector.create(NewRentActivity.this);
        selector.showCamera(showCamera);
        selector.count(maxNum);

        selector.multi();
        ArrayList<String> tempSelectPath = new ArrayList<>();
        tempSelectPath.addAll(selectImagePath);

        tempSelectPath.remove(tempSelectPath.size() - 1);

        selector.origin(tempSelectPath);
        selector.start(NewRentActivity.this, REQUEST_IMAGE);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onCamaraDenied() {
        Utils.toast(this, "您已经禁用了拍照以及浏览图库权限，可能导致相关功能无法正常使用");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        NewRentActivityPermissionsDispatcher.onRequestPermissionsResult(NewRentActivity.this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                selectImagePath.add("");
                imageGridAdapter.setImageList(selectImagePath);
                selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            }
        }
    }

    /**
     * 发布
     *
     * @param data
     */
    public void commit(String data) {
        String name = activityNewRentEtName.getText().toString().trim();
        String price = activityNewRentEtPrice.getText().toString().trim();
        String tingshi = activityNewRentTvTingshi.getText().toString().trim();
        String area = activityNewRentEtArea.getText().toString().trim();
        String floor = activityNewRentEtFloor.getText().toString().trim();
        String totalFloor = activityNewRentEtTotalFloor.getText().toString().trim();
        String xiaoqu = activityNewRentEtXiaoqu.getText().toString().trim();
        String address = activityNewRentEtAddress.getText().toString().trim();
        String describ = activityNewRentEtDescrib.getText().toString().trim();

        XUtilsHelper helper = new XUtilsHelper(this, url, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    CommonReturn commonReturn = (CommonReturn) msg.obj;
                    if (commonReturn.code == 1) {
                        startActivity(new Intent(NewRentActivity.this, SuccessActivity.class));
                        finish();
                    } else {
                        AlertDialog dialog = new AlertDialog.Builder(NewRentActivity.this)
                                .setMessage("余额不足，是否前往充值")
                                .setPositiveButton("前往充值", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(NewRentActivity.this, RechargeActivity.class));
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                    }
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{
                {"title", name},
                {"price", price},
                {"hall_room", tingshi},
                {"area", area},
                {"floor", floor},
                {"height", totalFloor},
                {"qu_name", xiaoqu},
                {"qu_site", address},
                {"qu", address},
                {"description", describ},
                {"litpic", data},
                {"id", id}
        });
        if (name.equals("") || price.equals("") || tingshi.equals("") || area.equals("") || floor.equals("") || totalFloor.equals("") || xiaoqu.equals("") ||
                address.equals("") || describ.equals("")) {
            Utils.toast(NewRentActivity.this, "请检查输入");
        } else {
            helper.sendPostAuto(CommonReturn.class);
        }
    }

    /**
     * 判断今天是否是第一次发布
     */
    private void isFirst(final String data) {
        mIsFirstHelper = new XUtilsHelper(this, NetworkTools.HAS_RENT, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    HasRent hasRent = (HasRent) msg.obj;
                    if (hasRent.getCode() == 1) {
                        Logger.d("我的发布状态 = " + hasRent.getData().getHasRent());
                        if (hasRent.getData().getHasRent() == 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewRentActivity.this)
                                    .setMessage("今天已经发布过了，\n再次发布需要扣除两元手续费，\n是否继续？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            commit(data);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mDialog.dismiss();
                                        }
                                    });
                            mDialog = builder.create();
                            mDialog.show();
                        } else {
                            commit(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));

        mIsFirstHelper.setRequestParams(new String[][]{});
        mIsFirstHelper.sendPostAuto(HasRent.class);
    }

    /**
     * 删除发布
     */
    private void delete() {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(NewRentActivity.this, NetworkTools.RENT_MY_RENT_DELETE, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    RenZhengBean commonReturn = (RenZhengBean) msg.obj;
                    if (commonReturn.getCode() == 1) {
                        Utils.toast(NewRentActivity.this, commonReturn.getMessage());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{{"id",id}});
        xUtilsHelper.sendPostAuto(RenZhengBean.class);
    }
}
