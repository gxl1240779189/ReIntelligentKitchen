package com.gxl.intelligentkitchen.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.model.UserModel;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.presenter.DynamicFragmentPresenter;
import com.gxl.intelligentkitchen.ui.activity.DynamicDetailActivity;
import com.gxl.intelligentkitchen.ui.activity.LoginActivity;
import com.gxl.intelligentkitchen.ui.activity.SendDynamicActivity;
import com.gxl.intelligentkitchen.ui.adapter.DynamicAdapter;
import com.gxl.intelligentkitchen.ui.dialog.DialogBuilder;
import com.gxl.intelligentkitchen.ui.view.IDynamicFragment;
import com.gxl.intelligentkitchen.utils.NetUtil;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.maxwin.view.XListView;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：朋友圈fragment
 */
public class DynamicFragment extends Fragment implements IDynamicFragment, XListView.IXListViewListener {
    @Bind(R.id.publish)
    ImageView publish;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.xListView)
    XListView xListView;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.tip)
    LinearLayout tip;

    private DynamicFragmentPresenter mPresenter;
    private DynamicAdapter mAdapter;
    private List<DynamicItem> mList = new ArrayList<>();

    private UserModel mUserModel = new UserModel();

    private List<DynamicItem> mDynamicList;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_main, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new DynamicFragmentPresenter(this);
        mAdapter = new DynamicAdapter(getActivity(), R.layout.dynamic_listviewother_item, mList);
        xListView.setAdapter(mAdapter);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(false);
        xListView.setXListViewListener(this);
        mPresenter.onRefresh();
        if (NetUtil.checkNet(getActivity())) {
            mPresenter.onRefresh();
        } else {
            loading.setVisibility(View.GONE);
            tip.setVisibility(View.VISIBLE);
            xListView.setVisibility(View.GONE);
        }
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DynamicItem item = mDynamicList.get(position-1);
                Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DYNAMIC", item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.publish)
    public void onClick() {
        if (new UserModel().isLogin()) {
            mUserModel.getUser(mUserModel.getUserLocal().getObjectId(), new FoodModelImpl.BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    User user = (User) o;
                    Intent intent = new Intent(getActivity(), SendDynamicActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User", user);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }

                @Override
                public void getFailure() {

                }
            });
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onLoadMore(List<DynamicItem> list) {

    }

    @Override
    public void onRefresh(List<DynamicItem> list) {
        mDynamicList = list;
        loading.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        xListView.setVisibility(View.VISIBLE);
        xListView.stopRefresh();
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMore() {

    }
}
