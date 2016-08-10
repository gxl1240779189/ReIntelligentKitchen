package com.gxl.intelligentkitchen.https.service;

import com.gxl.intelligentkitchen.entity.FoodSearchJson;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：该接口用来请求聚合数据的美食搜索结果
 */
public interface FoodSearchService {

    /**
     * @param foodname 美食名
     * @return 聚合数据返回的Json
     */
//    menu={foodname}&dtype=&pn=&rn=&albums=&key=62b66f73397452f0bf8354eb599fd43c @Query("dtype") String dtype, @Query("pn") String pn, @Query("albums") String albums,
    @GET("query.php")
    Observable<FoodSearchJson> getSearchFoodList(@Query("menu") String menu, @Query("key") String key);
}
