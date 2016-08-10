package com.gxl.intelligentkitchen.model;

import android.util.Log;
import android.widget.Toast;

import com.gxl.intelligentkitchen.application.BaseApplication;
import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.model.impl.DynamicModelImpl;
import com.gxl.intelligentkitchen.model.impl.FoodModelImpl;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：对朋友圈数据操作的model
 */
public class DynamicModel implements DynamicModelImpl {


    /**
     * 获取所有的朋友圈消息
     * @param listener
     */
    @Override
    public void getDynamicItem(final FoodModelImpl.BaseListener listener) {
        BmobQuery<DynamicItem> query = new BmobQuery<DynamicItem>();
        query.setLimit(1);
        query.findObjects(BaseApplication.getmContext(), new FindListener<DynamicItem>() {
            @Override
            public void onSuccess(List<DynamicItem> object) {
                if (object != null && object.size() != 0) {
                    listener.getSuccess(object.get(0));
                }
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    /**
     * 上传动态
     * @param dynamicitem
     */
    public void sendDynamicItem(final DynamicItem dynamicitem){
        final String[] array=new String[dynamicitem.getPhotoList().size()];
        for(int i=0;i<dynamicitem.getPhotoList().size();i++){
            array[i]=dynamicitem.getPhotoList().get(i).getLocalFile().getAbsolutePath();
            Log.i("path", "sendDynamicItem: "+array[i]+" "+dynamicitem.getPhotoList().size());
        }
        BmobFile.uploadBatch( BaseApplication.getmContext(),array, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files,List<String> urls) {
                if(urls.size()==array.length){
                    dynamicitem.setPhotoList(files);
                    dynamicitem.save(BaseApplication.getmContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(BaseApplication.getmContext(),"上传成功",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(BaseApplication.getmContext(),"上传失败",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onError(int statuscode, String errormsg) {
                Log.i("TAG", "onError: "+errormsg+statuscode);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                Log.i("TAG", "onProgress: "+curIndex+" "+curPercent+" "+total);
            }
        });


    }



}
