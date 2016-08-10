package com.gxl.intelligentkitchen.model;

import android.util.Log;

import com.gxl.intelligentkitchen.application.BaseApplication;
import com.gxl.intelligentkitchen.entity.FoodAccessories;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.entity.FoodTeachStep;
import com.gxl.intelligentkitchen.https.RetrofitWrapper;
import com.gxl.intelligentkitchen.https.service.FoodService;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：Food请求model
 */
public class FoodModel implements FoodModelImpl {
    @Override
    public void getGeneralFoodsItem(String sortby, int type, int page, final BaseListener listener) {
        FoodService service = RetrofitWrapper.getInstance().create(FoodService.class);
        service.getFoodList(sortby,type,page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, List<FoodGeneralItem>>(){
                    @Override
                    public List<FoodGeneralItem> call(String s) {
                        return HtmlParser.parserHtml(s);
                    }
                }).subscribe(new Subscriber<List<FoodGeneralItem>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.getFailure();
            }

            @Override
            public void onNext(List<FoodGeneralItem> s) {
                listener.getSuccess(s);
            }
        });
    }


    @Override
    public void getFoodDetailTeachItem(String foodlink, final BaseListener listener) {
        String foodname=foodlink.substring(foodlink.lastIndexOf("/")+1,foodlink.lastIndexOf("."));
        FoodService service = RetrofitWrapper.getInstance().create(FoodService.class);
        service.getDetailFood(foodname).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                 .map(new Func1<String, FoodDetailTeachItem>() {
                     @Override
                     public FoodDetailTeachItem call(String s) {
                         Log.i("TAG", "call: "+s);
                         return HtmlParser.parserHtmlToDetailInPc(s);
                     }
                 })
                .subscribe(new Subscriber<FoodDetailTeachItem>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG", "call:"+e);
                        listener.getFailure();
                    }

                    @Override
                    public void onNext(FoodDetailTeachItem foodDetailTeachItem) {
                        listener.getSuccess(foodDetailTeachItem);
                    }
                });
    }

    @Override
    public void getSliderShowFood(final BaseListener listener) {
        FoodService service = RetrofitWrapper.getInstance().create(FoodService.class);
        service.getSliderShowFood().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, List<SlideShowView.SliderShowViewItem>>() {
                    @Override
                    public List<SlideShowView.SliderShowViewItem> call(String s) {
                        return HtmlParser.parserHtmlToSliderShow(s);
                    }
                })
                .subscribe(new Subscriber< List<SlideShowView.SliderShowViewItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.getFailure();
                    }

                    @Override
                    public void onNext(List<SlideShowView.SliderShowViewItem> sliderShowViewItems) {
                        listener.getSuccess(sliderShowViewItems);
                    }

                });
    }
}