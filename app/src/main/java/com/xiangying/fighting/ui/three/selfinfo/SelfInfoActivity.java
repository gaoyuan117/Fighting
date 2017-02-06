package com.xiangying.fighting.ui.three.selfinfo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.zcomponent.http.api.host.Endpoint;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jock.pickerview.RegionInfo;
import com.jock.pickerview.dao.RegionDAO;
import com.jock.pickerview.view.OptionsPickerView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.three.bean.UserBaseInfo;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import service.api.CommonReturn;

@RuntimePermissions
public class SelfInfoActivity extends BaseActivity {
    @Bind(R.id.title_bar_common_iv_operate_3)
    FontTextView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView titleBarCommonIvOperate2;
    @Bind(R.id.title_bar_common_rv_viewGroup)
    RelativeLayout titleBarCommonRvViewGroup;
    @Bind(R.id.activity_self_info_tv_head)
    FontTextView activitySelfInfoTvHead;
    @Bind(R.id.activity_self_info_sdv_head)
    SimpleDraweeView activitySelfInfoSdvHead;
    @Bind(R.id.activity_self_info_ll_head)
    LinearLayout activitySelfInfoLlHead;
    @Bind(R.id.activity_self_info_tv_nickname)
    FontTextView activitySelfInfoTvNickname;
    @Bind(R.id.activity_self_info_ll_nickname)
    LinearLayout activitySelfInfoLlNickname;
    @Bind(R.id.activity_self_info_tv_zhandouhao)
    FontTextView activitySelfInfoTvZhandouhao;
    @Bind(R.id.activity_self_info_ll_zhandouhao)
    LinearLayout activitySelfInfoLlZhandouhao;
    @Bind(R.id.activity_self_info_tv_sex)
    FontTextView activitySelfInfoTvSex;
    @Bind(R.id.activity_self_info_ll_sex)
    LinearLayout activitySelfInfoLlSex;
    @Bind(R.id.activity_self_info_tv_area)
    FontTextView activitySelfInfoTvArea;
    @Bind(R.id.activity_self_info_ll_area)
    LinearLayout activitySelfInfoLlArea;
    @Bind(R.id.activity_self_info_tv_sign)
    FontTextView activitySelfInfoTvSign;
    @Bind(R.id.activity_self_info_ll_sign)
    LinearLayout activitySelfInfoLlSign;

    public static int MODIFY_NICKNAME = 100;
    public static int MODIFY_SELFSIGN = 101;
    public int REQUEST_SIMGLE_IMAGE = 10000;

    ArrayList<String> selectImageSingle = new ArrayList<>();
    String[] sexItemsValue = new String[]{"男", "女"};
    OptionsPickerView pickerViewArea;
    /**
     * 城市数据
     */
    static ArrayList<RegionInfo> item1;
    static ArrayList<ArrayList<RegionInfo>> item2 = new ArrayList<ArrayList<RegionInfo>>();
    static ArrayList<ArrayList<ArrayList<RegionInfo>>> item3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            System.out.println(System.currentTimeMillis());
            // 三级联动效果
            pickerViewArea.setPicker(item1, item2, null, true);
            pickerViewArea.setCyclic(true, true, true);
            pickerViewArea.setSelectOptions(0, 0, 0);
        };
    };

    private UserBaseInfo mUserBaseInfo;


    @Override
    protected void process() {

        pickerViewArea = new OptionsPickerView(this);
        /**
         * 设置pickerView的回调
         */
        pickerViewArea.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(Integer options1, Integer options2,
                                        Integer options3) {
                String tx = item1.get(options1).getPickerViewText()
                        + (options2 != null ? item2.get(options1).get(options2).getPickerViewText() : "")
                        + (options3 != null ? item3.get(options1).get(options2).get(options3).getPickerViewText() : "");
                activitySelfInfoTvArea.setText(tx);
            }
        });
        new AreaDataInitThread().start();

    }

    @Override
    protected void setAllClick() {
        //性别单选对话框
        activitySelfInfoLlSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出单选对话框
                final AlertDialog.Builder builder = new AlertDialog.Builder(SelfInfoActivity.this);
                builder.setSingleChoiceItems(sexItemsValue, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String context = sexItemsValue[which];
                        activitySelfInfoTvSex.setText(context);

                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        //选择城市
        activitySelfInfoLlArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerViewArea.show();
            }
        });
        //修改昵称
        activitySelfInfoLlNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfInfoActivity.this, MNicknameActivity.class);
                intent.putExtra("lastNickname", activitySelfInfoTvNickname.getText().toString().trim());
                startActivityForResult(intent, MODIFY_NICKNAME);
            }
        });
        //修改签名
        activitySelfInfoLlSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfInfoActivity.this, MSignActivity.class);
                intent.putExtra("lastSign", activitySelfInfoTvSign.getText().toString().trim());
                startActivityForResult(intent, MODIFY_SELFSIGN);
            }
        });
        //选择头像
        activitySelfInfoLlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelfInfoActivityPermissionsDispatcher.chooseImageSingleWithCheck(SelfInfoActivity.this);
            }
        });
    }

    @Override
    protected void initViews() {
        titleBarCommonTvTitle.setText("个人信息");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonIvOperate3.setVisibility(View.VISIBLE);
        titleBarCommonIvOperate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectImageSingle.size() == 0) {
                    upUserInfo(mUserBaseInfo.getData().getAvatar());
                } else {
                    updateHead();
                }
            }
        });
    }

    private void upUserInfo(String cover_id) {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(SelfInfoActivity.this, NetworkTools.Edit_user_info, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn result = (CommonReturn) msg.obj;
                    if (result.code == 1) {
                        Toast.makeText(SelfInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SelfInfoActivity.this, result.message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SelfInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        String sex = "";
        if(activitySelfInfoTvSex.getText().toString().trim().equals("男")){
            sex = "1";
        }else {
            sex = "2";
        }

        xUtilsHelper.setRequestParams(new String[][]{
                {"cover_id", cover_id},
                {"nickname", activitySelfInfoTvNickname.getText().toString().trim()},
                {"sex", sex},
                {"district", activitySelfInfoTvArea.getText().toString().trim()},
                {"sign", activitySelfInfoTvSign.getText().toString().trim()}
        });
        xUtilsHelper.sendPostAuto(CommonReturn.class);
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_self_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        loadUserInfo();
    }

    /**
     * 处理城市数据线程
     */
    class AreaDataInitThread extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
            if (item1 != null && item2 != null && item3 != null) {
                handler.sendEmptyMessage(0x123);
                return;
            }
            item1 = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCity(1);
            for (RegionInfo regionInfo : item1) {
                item2.add((ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId()));

            }

            for (ArrayList<RegionInfo> arrayList : item2) {
                ArrayList<ArrayList<RegionInfo>> list2 = new ArrayList<ArrayList<RegionInfo>>();
                for (RegionInfo regionInfo : arrayList) {
                    ArrayList<RegionInfo> q = (ArrayList<RegionInfo>) RegionDAO.getProvencesOrCityOnParent(regionInfo.getId());
                    list2.add(q);
                }
                item3.add(list2);
            }
            handler.sendEmptyMessage(0x123);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 单选图片文件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void chooseImageSingle() {
        boolean showCamera = true;
        int maxNum = 1;
        MultiImageSelector selector = MultiImageSelector.create(SelfInfoActivity.this);
        selector.showCamera(showCamera);
        selector.count(maxNum);
        selector.single();
        selector.origin(selectImageSingle);
        selector.start(SelfInfoActivity.this, REQUEST_SIMGLE_IMAGE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onCamaraDenied() {
        Utils.toast(this, "您已经禁用了拍照以及浏览图库权限，可能导致相关功能无法正常使用");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        SelfInfoActivityPermissionsDispatcher.onRequestPermissionsResult(SelfInfoActivity.this, requestCode, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_NICKNAME && resultCode == RESULT_OK) {
            String newNickname = data.getStringExtra("newNickname");
            activitySelfInfoTvNickname.setText(newNickname);
        }
        if (requestCode == MODIFY_SELFSIGN && resultCode == RESULT_OK) {
            String newSign = data.getStringExtra("newSign");
            activitySelfInfoTvSign.setText(newSign);
        }
        if (requestCode == REQUEST_SIMGLE_IMAGE && resultCode == RESULT_OK) {
            selectImageSingle = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            activitySelfInfoSdvHead.setImageURI(Uri.parse("file://" + selectImageSingle.get(0)));
        }
    }

    public void loadUserInfo() {
        XUtilsHelper<UserBaseInfo> helper = new XUtilsHelper<UserBaseInfo>(getApplication(), NetworkTools.GET_USER_INFO_SELF, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    mUserBaseInfo = (UserBaseInfo) msg.obj;
                    //设置信息
                    activitySelfInfoTvNickname.setText(mUserBaseInfo.getData().getNickname());
                    activitySelfInfoTvZhandouhao.setText("战斗号：" + mUserBaseInfo.getData().getUsername());

                    activitySelfInfoTvSex.setText(mUserBaseInfo.getData().getSex().equals("1") ? "男" : "女");
                    activitySelfInfoTvArea.setText(mUserBaseInfo.getData().getDistrict());
                    activitySelfInfoTvSign.setText(mUserBaseInfo.getData().getSign());//签名
                    //头像
                    if (!"".equals(mUserBaseInfo.getData().getAvatar_path())) {
                        activitySelfInfoSdvHead.setImageURI(Uri.parse(Endpoint.HOST + mUserBaseInfo.getData().getAvatar_path()));
                    }
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(UserBaseInfo.class);
    }

    /**
     * 更新头像
     */
    public void updateHead() {
        XUtilsHelper helper = null;
        helper = new XUtilsHelper(this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == XUtilsHelper.TAG_SUCCESS) {
                    ImageFileBean fileUploadBean = (ImageFileBean) msg.obj;
                    upUserInfo(fileUploadBean.getData());
                }
                return true;
            }
        }));
        helper.setFileRequestParames2(selectImageSingle);
        helper.sendPostAuto(ImageFileBean.class);
    }

    /**
     * 跟新头像
     */
    public void uploadHeadId(String imageId) {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.UPDATE_USER_HEAD, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"cover_id", imageId}});
        helper.sendPost();
    }

}
