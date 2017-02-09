package com.xiangying.fighting.ui.first.armygroup;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.HasGroupBean;
import com.xiangying.fighting.bean.ImageFileBean;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.first.talks.CreatGroupBean;
import com.xiangying.fighting.ui.three.zichan.RechargeActivity;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.Utils;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import service.api.CommonReturn;

@RuntimePermissions
public class CreatGroupActivity extends BaseActivity {
    public static final int REQUEST_IMAGE = 10001;
    @Bind(R.id.title_bar_common_tv_ok)
    FontTextView titleBarCommonTvOk;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.creatgroup_edt)
    EditText creatgroupEdt;
    @Bind(R.id.creatgroup_img_avatar)
    SimpleDraweeView creatgroupImg;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.creatgroup_fra)
    FrameLayout creatgroupFra;
    @Bind(R.id.btn_recharge_tx)
    Button mButton;
    @Bind(R.id.lv_creat_group)
    ListView mListView;
    @Bind(R.id.creatgroup_num)
    EditText mGroupNum;
    @Bind(R.id.tv_creat_group_ts)
    TextView tvCreatGroupTs;

    private String name = "", type = "group";
    ArrayList<String> selectImagePath = new ArrayList<>();

    //request params and helper
    private XUtilsHelper xHelper;
    private String[][] requestParams;
    private AlertDialog mDialog;
    private XUtilsHelper firstHelper;
    private ArrayAdapter adapter;
    private List<Integer> mList;
    private String mId;


    @Override
    protected void process() {
        requestParams = new String[5][5];
    }

    @Override
    protected void setAllClick() {
        titleBarCommonTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = creatgroupEdt.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    if (type.equals("group")) {
                        Utils.toast(CreatGroupActivity.this, "请输入军团名字");

                        if (selectImagePath.size() == 0) {
                            Utils.toast(CreatGroupActivity.this, "请选择军团头像");
                            return;
                        }

                    } else {
                        Utils.toast(CreatGroupActivity.this, "请输入分队名字");
                    }
                    return;
                }


                if (type.equals("group")) {
                    mId = mGroupNum.getText().toString().trim();
                    if (!TextUtils.isEmpty(mId) && mId.length() < 5) {
                        Toast.makeText(CreatGroupActivity.this, "战斗团号至少填写5位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (selectImagePath.size() == 0) {
                        Toast.makeText(CreatGroupActivity.this, "请选择一张图片作为你的战斗团头像", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    XUtilsHelper xUtilsHelper = new XUtilsHelper(CreatGroupActivity.this, NetworkTools.UPLOAD_Image_app, new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            try {
                                final ImageFileBean reslut = (ImageFileBean) msg.obj;
                                if (reslut.getCode() == 1) {
                                    if (!TextUtils.isEmpty(mId)) {
                                        showChargeDialog(reslut.getData());
                                    } else {
                                        isFirstCreat(reslut.getData());
                                    }
                                } else {
                                    Toast.makeText(CreatGroupActivity.this, "请选择一张图片作为军团头像", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                    }));
                    xUtilsHelper.setFileRequestParames2(selectImagePath);
                    xUtilsHelper.sendPostAuto(ImageFileBean.class);
                } else {
                    createTeam();
                }
            }
        });
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        creatgroupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImages();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mId = mList.get(position) + "";
                mGroupNum.setText(mList.get(position) + "");
            }
        });
    }

    /**
     * 自选号收费
     */
    private void showChargeDialog(final String data) {
        AlertDialog dialog = new AlertDialog.Builder(CreatGroupActivity.this)
                .setMessage("自选战斗团号将收费100元，是否继续？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isFirstCreat(data);
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

    /**
     * 创建团队
     *
     * @param data
     */
    private void createGroup(String data) {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(CreatGroupActivity.this, NetworkTools.HX_ADD_GROUP, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CreatGroupBean commonReturn = (CreatGroupBean) msg.obj;

                    if (commonReturn.getCode() == 1) {
                        Utils.toast(CreatGroupActivity.this, "创建成功");
                        finish();
                    } else if (commonReturn.getCode() == -2) {
                        showChongZhi();
                    } else {
                        Toast.makeText(CreatGroupActivity.this, commonReturn.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.toast(CreatGroupActivity.this, "创建失败");
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{
                {"groupname", name},
                {"desc", "军团"},
                {"public", "true"},
                {"maxusers", "100"},
                {"approval", "true"},
                {"cover_id", data},
                {"rand", mId}
        });
        xUtilsHelper.sendPostAuto(CreatGroupBean.class);
    }

    /**
     * 显示充值对话框
     */
    private void showChongZhi() {
        AlertDialog dialog = new AlertDialog.Builder(CreatGroupActivity.this)
                .setMessage("余额不足，是否前往充值")
                .setPositiveButton("前往充值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(CreatGroupActivity.this, RechargeActivity.class));
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

    /**
     * 判断是否是第一次创建团队
     *
     * @param
     */
    private void isFirstCreat(final String data) {
        firstHelper = new XUtilsHelper(CreatGroupActivity.this, NetworkTools.HX_ISFIRST, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    HasGroupBean commonReturn = (HasGroupBean) msg.obj;
                    if (commonReturn.getCode() == 1) {
                        if (commonReturn.getData().getIs_exist() == 0) {
                            createGroup(data);
                        } else {
                            showDialog(data);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }));
        firstHelper.setRequestParams(new String[][]{});
        firstHelper.sendPostAuto(HasGroupBean.class);

    }

    /**
     * 显示对话框
     *
     * @param data
     */
    private void showDialog(final String data) {
        mDialog = new AlertDialog.Builder(CreatGroupActivity.this)
                .setMessage("再次创建需要支付100元，是否继续？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createGroup(data);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                    }
                }).create();
        mDialog.show();
    }

    /**
     * 创建团队
     */
    private void createTeam() {
        XUtilsHelper xUtilsHelper = new XUtilsHelper(CreatGroupActivity.this, NetworkTools.HX_ADD_TEAM, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    CommonReturn commonReturn = (CommonReturn) msg.obj;
                    if (commonReturn.code == 1) {
                        Utils.toast(CreatGroupActivity.this, "创建成功");
                        finish();
                    } else {
                        Utils.toast(CreatGroupActivity.this, commonReturn.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.toast(CreatGroupActivity.this, "创建失败");
                }
                return false;
            }
        }));
        xUtilsHelper.setRequestParams(new String[][]{{"name", name}});
        xUtilsHelper.sendPostAuto(CommonReturn.class);
    }

    @Override
    protected void initViews() {
        if (getIntent().hasExtra("element")) {
            type = "element";
            creatgroupFra.setVisibility(View.GONE);
            titleBarCommonTvTitle.setText("添加分队");
            creatgroupEdt.setHint("给您的分队起一个响亮的名字");
        }

        if (type.equals("group")) {
            tvCreatGroupTs.setVisibility(View.VISIBLE);
            mGroupNum.setVisibility(View.VISIBLE);
        }
        mList = new ArrayList<>();
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_creat_group);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {

    }

    /**
     * 查看图库文件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void chooseImages() {
        boolean showCamera = true;
        int maxNum = 1;
        MultiImageSelector selector = MultiImageSelector.create(CreatGroupActivity.this);
        selector.showCamera(showCamera);
        selector.count(maxNum);

        selector.multi();
        ArrayList<String> tempSelectPath = new ArrayList<>();
        tempSelectPath.addAll(selectImagePath);

        selector.origin(tempSelectPath);
        selector.start(CreatGroupActivity.this, REQUEST_IMAGE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void onCamaraDenied() {
        Utils.toast(this, "您已经禁用了拍照以及浏览图库权限，可能导致相关功能无法正常使用");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        CreatGroupActivityPermissionsDispatcher.onRequestPermissionsResult(CreatGroupActivity.this, requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                selectImagePath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                //更新数据
                creatgroupImg.setImageURI(Uri.parse("file://" + selectImagePath.get(0)));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
