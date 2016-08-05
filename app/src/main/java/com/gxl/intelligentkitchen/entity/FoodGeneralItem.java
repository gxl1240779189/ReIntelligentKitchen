package com.gxl.intelligentkitchen.entity;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：缩略展示美食，外部listview使用
 */

import java.io.Serializable;
public class FoodGeneralItem implements Serializable{
    /**
     * 标题
     */
    private String title;
    /**
     * 链接
     *
     */
    private String link;

    /**
     * 图片的链接
     */
    private String imgLink;

    /**
     * 作者名
     */
    private String writer;

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    /**
     * 味道
     */
    private String taste;

    public String getDiscuss() {
        return discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = discuss;
    }

    /**
     * 评论
     */
    private String discuss;

    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     *  步骤 时间
     */
    private String date;



    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return title;
    }
}

