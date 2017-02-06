package com.xiangying.fighting.ui.first.armygroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.xiangying.fighting.R;
import com.xiangying.fighting.common.BaseFragment;
import com.xiangying.fighting.widget.FontTextView;
import com.xiangying.fighting.ui.first.armyuser.SearchFriendResultActivity;
import com.xiangying.fighting.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/27.
 */
public class FragmentSearchFriend extends BaseFragment {
    @Bind(R.id.fragmentfind_edt)
    EditText fragmentfindEdt;
    @Bind(R.id.fragmenfind_tv_search)
    FontTextView fragmenfindTvSearch;

    private String type = "friend",keyWord="";

    public static Fragment newInstance(String tag) {
        Fragment fragment = new FragmentSearchFriend();
        Bundle bundle = new Bundle();
        bundle.putString("type", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void process() {

    }

    @Override
    protected void setAllClick() {
        fragmenfindTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord=fragmentfindEdt.getText().toString();
                if(TextUtils.isEmpty(keyWord)){
                    Utils.toast(getContext(), "请输入关键字进行搜索");
                    return;
                }
                Intent intent=new Intent(getContext(),SearchFriendResultActivity.class);
                intent.putExtra("key",keyWord);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getString("type");
        if (type.equals("friend")) {

        } else {

        }
    }

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_findresult, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadNetData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
