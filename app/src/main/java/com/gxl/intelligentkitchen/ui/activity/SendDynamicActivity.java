package com.gxl.intelligentkitchen.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxl.intelligentkitchen.R;
import com.gxl.intelligentkitchen.entity.DynamicItem;
import com.gxl.intelligentkitchen.entity.DynamicPhotoItem;
import com.gxl.intelligentkitchen.entity.User;
import com.gxl.intelligentkitchen.model.DynamicModel;
import com.gxl.intelligentkitchen.ui.adapter.DynamicPhotoAdapter;
import com.gxl.intelligentkitchen.utils.SPUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：
 */
public class SendDynamicActivity extends Activity {
    private final int REQUEST_CODE = 0x01;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.send)
    TextView send;
    @Bind(R.id.edit_content)
    EditText editContent;
    @Bind(R.id.gridView)
    GridView gridView;
    private DynamicPhotoAdapter mDynamicPhotoAdapter;
    private final String LOGINUSER = "loginuser";
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.senddynamic_activity_main);
        mUser= (User) getIntent().getSerializableExtra("User");
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        final PhotoPickerIntent intent = new PhotoPickerIntent(SendDynamicActivity.this);
        intent.setPhotoCount(6);
        intent.setShowCamera(true);
        mDynamicPhotoAdapter = new DynamicPhotoAdapter(SendDynamicActivity.this);
        gridView.setAdapter(mDynamicPhotoAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mDynamicPhotoAdapter.getCount() - 1) {
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            List<DynamicPhotoItem> list = new ArrayList<>();
            if (pathList.size() != 0) {
                for (String path : pathList
                        ) {
                    list.add(new DynamicPhotoItem(path, false));
                }
            }
            mDynamicPhotoAdapter.addData(list);
        }
    }

    @OnClick({R.id.cancel, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.send:
                DynamicItem dynamicItem = new DynamicItem();
                dynamicItem.setWriter(mUser);
                Log.i("TAG", "onClick: "+(mUser).getName());
                List<BmobFile> fileList = new ArrayList<>();
                ArrayList<DynamicPhotoItem> photoItems = (ArrayList<DynamicPhotoItem>) mDynamicPhotoAdapter.getData();
                for(int i=0;i<photoItems.size()-1;i++){
                    fileList.add(new BmobFile(new File(photoItems.get(i).getFilePath())));
                }
                dynamicItem.setText(editContent.getText().toString());
                dynamicItem.setPhotoList(fileList);
                new DynamicModel().sendDynamicItem(dynamicItem);
                break;
        }
    }
}
