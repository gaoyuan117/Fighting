package com.xiangying.fighting.ui.three.zichan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangying.fighting.R;
import com.xiangying.fighting.bean.BankListBean;
import com.xiangying.fighting.ui.three.bean.RenZhengBean;
import com.xiangying.fighting.utils.NetworkTools;
import com.xiangying.fighting.utils.XUtilsHelper;
import com.xiangying.fighting.widget.FontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TiXianActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @Bind(R.id.title_bar_common_iv_operate_3)
    ImageView titleBarCommonIvOperate3;
    @Bind(R.id.title_bar_common_tv_title)
    FontTextView titleBarCommonTvTitle;
    @Bind(R.id.title_bar_common_iv_operate_1)
    ImageView titleBarCommonIvOperate1;
    @Bind(R.id.title_bar_common_iv_operate_2)
    ImageView titleBarCommonIvOperate2;
    @Bind(R.id.sp_tx_choose)
    Spinner mChoose;
    @Bind(R.id.et_tx_card)
    TextView mCard;
    @Bind(R.id.tv_tx_change)
    TextView mChange;
    @Bind(R.id.et_tx_money)
    EditText mMoney;
    @Bind(R.id.bt_tx_sure)
    Button mSure;

    private String type, money, path, card;
    private List<BankListBean.DataBean.ResultBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_xian);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        cardList();
        initTitle();
        setListener();
    }

    /**
     * 设置点击事件
     */
    private void setListener() {
        mChoose.setOnItemSelectedListener(this);
        mSure.setOnClickListener(this);
        mChange.setOnClickListener(this);
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        titleBarCommonIvOperate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarCommonTvTitle.setText("提现");
        titleBarCommonIvOperate3.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_tx_sure://提现
                money = mMoney.getText().toString().trim();
                if (TextUtils.isEmpty(type) || TextUtils.isEmpty(card) || TextUtils.isEmpty(money)) {
                    Toast.makeText(this, "请检查输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断充值类型
                if (type.equals("个人账户")) {
                    path = "";
                } else if (type.equals("商家账户")) {
                    path = "";
                }
                tixian();
                break;

            case R.id.tv_tx_change://选择银行卡
                Intent intent = new Intent(this, BankActivity.class);
                intent.putExtra("t", "card");
                startActivityForResult(intent, 110);
                break;
        }
    }

    /**
     * 获取银行卡列表
     */
    private void cardList() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.SELE_BANK_LIST, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    BankListBean bankListBean = (BankListBean) msg.obj;
                    if (bankListBean.getCode() == 1) {
                        mList.addAll(bankListBean.getData().getResult());
                        setText(0);
                    }
                } catch (Exception e) {
                    Toast.makeText(TiXianActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{});
        helper.sendPostAuto(BankListBean.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 123 && data != null) {
            int position = data.getIntExtra("position", 0);
            setText(position);
        }
    }

    /**
     * 设置信息
     *
     * @param position
     */
    private void setText(int position) {
        if (mList == null || mList.size() == 0) {
            return;
        }

        String cardNo = mList.get(position).getCard_no().substring(15);
        String title = mList.get(position).getBank_title();
        if (!TextUtils.isEmpty(title)) {
            String[] types = title.split("-");
            mCard.setText("尾号" + cardNo + "（" + types[0] + "卡" + "）");
        } else {
            mCard.setText("尾号" + cardNo + "（" + "未知" + "卡" + "）");
        }
        card = mList.get(position).getCard_no();
    }


    /**
     * 提现
     */
    private void tixian() {
        XUtilsHelper helper = new XUtilsHelper(this, NetworkTools.SHOP_TX, new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                RenZhengBean renZhengBean = (RenZhengBean) msg.obj;
                Toast.makeText(TiXianActivity.this, renZhengBean.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }));
        helper.setRequestParams(new String[][]{{"price", money}, {"bankname", card}});
        helper.sendPostAuto(RenZhengBean.class);
    }
}
