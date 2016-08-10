package com.gxl.intelligentkitchen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.eventbus.UserEventBus;
import com.gxl.intelligentkitchen.presenter.UserFragmentPresenter;
import com.gxl.intelligentkitchen.ui.activity.FoodLoveActivity;
import com.gxl.intelligentkitchen.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.AppVersion;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import de.greenrobot.event.EventBus;

public class UserFragment extends Fragment {
    @Bind(R.id.UserPhoto)
    ImageView UserPhoto;
    @Bind(R.id.loginText)
    TextView loginText;
    @Bind(R.id.sendFood)
    TextView sendFood;
    @Bind(R.id.sendDynamic)
    TextView sendDynamic;
    @Bind(R.id.love)
    TextView love;
    @Bind(R.id.send)
    TextView send;
    @Bind(R.id.update)
    TextView update;
    @Bind(R.id.guanyu)
    TextView guanyu;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    private final String LOGINUSER = "loginuser";
    private User mUser;

    private UserFragmentPresenter mUserFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.user_fragment_main, container, false);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_empty_dish)
                .showImageForEmptyUri(R.drawable.ic_empty_dish)
                .showImageOnFail(R.drawable.ic_empty_dish).cacheInMemory()
                .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ButterKnife.bind(this, v);
        EventBus.getDefault().register(this);
        mUserFragmentPresenter = new UserFragmentPresenter();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.UserPhoto, R.id.loginText, R.id.sendFood, R.id.sendDynamic, R.id.love, R.id.send, R.id.update, R.id.guanyu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UserPhoto:
                break;
            case R.id.loginText:
                mUserFragmentPresenter.onLogin(getActivity());
                break;
            case R.id.sendFood:
                break;
            case R.id.sendDynamic:
                mUserFragmentPresenter.onSendDynamic(getActivity(), mUser);
                break;
            case R.id.love:
                startActivity(new Intent(getActivity(), FoodLoveActivity.class));
                break;
            case R.id.send:

                break;
            case R.id.update:
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        if (updateStatus == UpdateStatus.No) {
                            Toast.makeText(getActivity(), "版本无更新", Toast.LENGTH_SHORT).show();
                        } else if (updateStatus == UpdateStatus.Yes) {
                            BmobUpdateAgent.forceUpdate(getActivity());
                        }
                    }
                });
                BmobUpdateAgent.update(getActivity());
                break;
            case R.id.guanyu:

                break;
        }
    }

    /**
     * Eventbus的处理函数
     *
     * @param event
     */
    public void onEventMainThread(UserEventBus event) {
        mUser = event.getmUser();
        LogUtils.i("test", event.getmUser().getName());
        if (event.getmUser().getPhoto() != null) {
            imageLoader.displayImage(event.getmUser().getPhoto().getUrl(), UserPhoto, options);
        }
        loginText.setText(event.getmUser().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (SPUtils.contains(getActivity(), LOGINUSER)) {
//            User defaultuser = new User();
//            User user = (User) SPUtils.get(getActivity(), LOGINUSER, defaultuser);
////            if (user.getPhoto() != null) {
////                imageLoader.displayImage(user.getPhoto().getUrl(), UserPhoto, options);
////            }
//            loginText.setText(user.getName());
//        }
    }
}
