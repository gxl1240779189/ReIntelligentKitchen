package com.gxl.intelligentkitchen.https.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
        * 作者：GXL on 2016/8/3 0003
        * 博客: http://blog.csdn.net/u014316462
        * 作用：该接口用来请求美食杰网页数据
        */
public interface FoodService {

    /**
     *
     * @param sortby  最新update和最热renqi两种
     * @param lm      食品的种类
     * @param page    当前的页码
     * @return        当前html页面
     */
    @GET("list.php")
    Observable<String> getFoodList(@Query("sortby") String sortby, @Query("lm") int lm, @Query("page") int page);

    /**
     *
     * @param foodname 食品名
     * @return         当前html页面
     */
    @GET("zuofa/{foodname}.html")
    Observable<String> getDetailFood(@Path("foodname") String foodname);

    /**
     * 获取滚动展示的集合,假如参数为空，要在里面加一个空格
     * @return  当前html页面
     */
    @GET(" ")
    Observable<String> getSliderShowFood();
}
