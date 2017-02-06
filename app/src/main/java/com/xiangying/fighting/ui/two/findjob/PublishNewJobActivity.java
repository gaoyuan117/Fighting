package com.xiangying.fighting.ui.two.findjob;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jock.pickerview.RegionInfo;
import com.jock.pickerview.dao.RegionDAO;
import com.jock.pickerview.view.OptionsPickerView;
import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseActivity;
import com.xiangying.fighting.ui.three.zichan.RechargeActivity;
import com.xiangying.fighting.ui.two.findjob.bean.JobDetailBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.ClearEditText;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import service.api.CommonReturn;

public class PublishNewJobActivity extends BaseActivity {
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
    @Bind(R.id.activity_publish_NJ_et_zhiwei)
    ClearEditText activityPublishNJEtZhiwei;
    @Bind(R.id.activity_publish_NJ_tv_xingzi)
    FontTextView activityPublishNJTvXingzi;
    @Bind(R.id.activity_publish_NJ_ll_xingzi)
    LinearLayout activityPublishNJLlXingzi;
    @Bind(R.id.activity_publish_NJ_ll_fenlei)
    LinearLayout activityPublishNJLlFenlei;
    @Bind(R.id.activity_publish_NJ_tv_fenlei)
    FontTextView activityPublishNJTvFeilei;
    @Bind(R.id.activity_publish_NJ_tv_area)
    FontTextView activityPublishNJTvArea;
    @Bind(R.id.activity_publish_NJ_ll_area)
    LinearLayout activityPublishNJLlArea;
    @Bind(R.id.activity_publish_NJ_et_address)
    ClearEditText activityPublishNJEtAddress;
    @Bind(R.id.activity_publish_NJ_et_company)
    ClearEditText activityPublishNJEtCompany;
    @Bind(R.id.activity_publish_NJ_et_phone)
    ClearEditText activityPublishNJEtPhone;
    @Bind(R.id.activity_publish_NJ_et_zhize)
    EditText activityPublishNJEtZhize;
    @Bind(R.id.activity_publish_NJ_et_tiaojian)
    EditText activityPublishNJEtTiaojian;
    @Bind(R.id.activity_publish_NJ_tv_publish)
    FontTextView activityPublishNJTvPublish;

    OptionsPickerView pvOptionXingzi;
    ArrayList<String> xingziList = new ArrayList<>();

    OptionsPickerView pvOptionArea;

    private String cid;
    private String id;


    static ArrayList<RegionInfo> item1;
    static ArrayList<ArrayList<RegionInfo>> item2 = new ArrayList<ArrayList<RegionInfo>>();
    static ArrayList<ArrayList<ArrayList<RegionInfo>>> item3 = new ArrayList<ArrayList<ArrayList<RegionInfo>>>();
    private XUtilsHelper mHelper;
    private AlertDialog mDialog;

    public static void start(Context context, String cid) {
        Intent starter = new Intent(context, PublishNewJobActivity.class);
        starter.putExtra("cid", cid);
        context.startActivity(starter);
    }

    public static void start(Context context, String id, String cid) {
        Intent starter = new Intent(context, PublishNewJobActivity.class);
        starter.putExtra("id", id);
        starter.putExtra("cid", cid);
        context.startActivity(starter);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            pvOptionArea.setPicker(item1, item2, null, true);
            pvOptionArea.setCyclic(true, true, true);
            pvOptionArea.setSelectOptions(0, 0, 0);
        }
    };

    @Override
    protected void process() {
        xingziList.add("2000元以下");
        xingziList.add("2000-3000元");
        xingziList.add("3000-5000元");
        xingziList.add("5000-6000元");
        xingziList.add("6000-8000元");
        xingziList.add("8000元以上");

        pvOptionXingzi = new OptionsPickerView(this);

        pvOptionXingzi.setPicker(xingziList);
        pvOptionXingzi.setCyclic(false);
        pvOptionXingzi.setSelectOptions(0);

        //设置监听
        pvOptionXingzi.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
                activityPublishNJTvXingzi.setText(xingziList.get(options1));
            }
        });

        //开启处理城市数据线程
        new AreaDataInitThread().start();

        pvOptionArea = new OptionsPickerView(this);

        //设置监听
        pvOptionArea.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(Integer options1, Integer options2, Integer options3) {
                String tx = item1.get(options1).getPickerViewText()
                        + (options2 != null ? item2.get(options1).get(options2).getPickerViewText() : "")
                        + (options3 != null ? item3.get(options1).get(options2).get(options3).getPickerViewText() : "");
                activityPublishNJTvArea.setText(tx);
            }
        });

        activityPublishNJLlFenlei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishNewJobActivity.this, JobCategoryActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void setAllClick() {
        titleBarCommonTvTitle.setText("发布职位");
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        activityPublishNJLlXingzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptionXingzi.show();
            }
        });
        activityPublishNJLlArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptionArea.show();
            }
        });

        //TODO 提示发布一条招聘信息需要2元钱
        activityPublishNJTvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PublishNewJobActivity.this)
                        .setMessage("每发布一条招聘信息需要2元钱，是否继续？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                commit();
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
            }
        });

    }

    @Override
    protected void initViews() {
        titleBarCommonIvOperate3.setVisibility(View.INVISIBLE);
        cid = getIntent().getStringExtra("cid");
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void loadLayout() {
        setContentView(R.layout.activity_publish_nj);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadNetData() {
        if (!TextUtils.isEmpty(id)) {
            XUtilsHelper xUtilsHelper = new XUtilsHelper(this, NetworkTools.JOB_DETAIL, new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    try {
                        JobDetailBean bean = (JobDetailBean) msg.obj;
                        if (bean.getCode() == 1) {
                            showJobInfo(bean.getData());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }));
            xUtilsHelper.setRequestParams(new String[][]{{"id", id}});
            xUtilsHelper.sendPostAuto(JobDetailBean.class);
        }
    }

    private void showJobInfo(JobDetailBean.Data data) {
        activityPublishNJEtZhiwei.setText(data.getWork());
        activityPublishNJTvFeilei.setText(data.getWorktype());
        activityPublishNJTvXingzi.setText(data.getTreatment());

    }

    public void commit() {

        String url = NetworkTools.ADD_NEW_JOB;
        if (!TextUtils.isEmpty(id)) {
            url = NetworkTools.EDIT_JOB;
        }

        String word = activityPublishNJEtZhiwei.getText().toString().trim();
        String workType = activityPublishNJTvFeilei.getText().toString().trim();
        String treatment = activityPublishNJTvXingzi.getText().toString().trim();

        String workplace = activityPublishNJTvArea.getText().toString().trim();
        String address = activityPublishNJEtAddress.getText().toString().trim();
        String companyName = activityPublishNJEtCompany.getText().toString().trim();
        String tel = activityPublishNJEtPhone.getText().toString().trim();

        String obligation = activityPublishNJEtZhize.getText().toString().trim();
        String criteria = activityPublishNJEtTiaojian.getText().toString().trim();

        mHelper = new XUtilsHelper(this, url, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                mHelper.hideProgress();
                CommonReturn commonReturn = (CommonReturn) msg.obj;
                if (commonReturn.code == 1) {
                    Toast.makeText(PublishNewJobActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(PublishNewJobActivity.this)
                            .setMessage("余额不足，是否前往充值")
                            .setPositiveButton("前往充值", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(PublishNewJobActivity.this, RechargeActivity.class));
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
                return false;
            }
        }));
        mHelper.setRequestParams(new String[][]{
                {"id", TextUtils.isEmpty(id) ? "" : id},
                {"cid", cid},
                {"work", word},   //职位名称
                {"worktype", workType}, //职位分类
                {"workplace", workplace}, //工作地区
                {"tel", tel}, //职位分类
                {"address", address}, //详细地址
                {"treatment", treatment}, //待遇
                {"obligation", obligation}, //岗位职责
                {"criteria", criteria}   //条件
        });
        mHelper.showProgress("正在上传...");
        mHelper.sendPostAuto(CommonReturn.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            activityPublishNJTvFeilei.setText(data.getStringExtra("data"));
        }
    }
}
