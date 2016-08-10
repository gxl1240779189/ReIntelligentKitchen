package com.gxl.intelligentkitchen.model;

import android.util.Log;

import com.gxl.intelligentkitchen.application.BaseApplication;
import com.gxl.intelligentkitchen.entity.FoodSearchJson;
import com.gxl.intelligentkitchen.https.service.FoodSearchService;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.model.impl.FoodSearchModelImpl;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;
import com.gxl.intelligentkitchen.utils.StringUtils;
import com.gxl.intelligentkitchen.utils.ToastUtils;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：搜索美食的Model类
 */
public class FoodSearchModel implements FoodSearchModelImpl {

    private Retrofit retrofit;
    private final String BASE_URL = "http://apis.juhe.cn/cook/";

    public FoodSearchModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Override
    public void onSearchFood(String foodname, final FoodModelImpl.BaseListener listener) {
        FoodSearchService service = retrofit.create(FoodSearchService.class);
        service.getSearchFoodList(foodname, "62b66f73397452f0bf8354eb599fd43c").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FoodSearchJson>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.getFailure();
                    }

                    @Override
                    public void onNext(FoodSearchJson item) {
                        switch (item.getError_code()) {
                            case 0:
                                listener.getSuccess(item);
                                break;
                            case 204601:
                                ToastUtils.showLong(BaseApplication.getmContext(), "菜谱名称不能为空");
                                break;
                            case 204602:
                                ToastUtils.showLong(BaseApplication.getmContext(), "查询不到相关信息");
                                break;
                            case 204603:
                                ToastUtils.showLong(BaseApplication.getmContext(), "菜谱名过长");
                                break;
                            case 204604:
                                ToastUtils.showLong(BaseApplication.getmContext(), "错误的标签ID");
                                break;
                            case 204605:
                                ToastUtils.showLong(BaseApplication.getmContext(), "查询不到数据");
                                break;
                            case 204606:
                                ToastUtils.showLong(BaseApplication.getmContext(), "错误的菜谱ID");
                                break;
                        }
                    }
                });
    }
}
