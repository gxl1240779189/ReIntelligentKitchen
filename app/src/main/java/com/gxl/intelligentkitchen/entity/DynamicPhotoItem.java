package com.gxl.intelligentkitchen.entity;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：发送朋友圈照片对象
 */
public class DynamicPhotoItem {
    private String filePath;
    private boolean isPick;//标识是不是+

    public DynamicPhotoItem() {
    }

    public DynamicPhotoItem(String filePath, boolean isPick) {
        this.filePath = filePath;
        this.isPick = isPick;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }
}
