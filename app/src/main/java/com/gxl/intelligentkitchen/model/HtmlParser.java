package com.gxl.intelligentkitchen.model;

import android.util.Log;

import com.gxl.intelligentkitchen.entity.FoodAccessories;
import com.gxl.intelligentkitchen.entity.FoodDetailTeachItem;
import com.gxl.intelligentkitchen.entity.FoodGeneralItem;
import com.gxl.intelligentkitchen.entity.FoodTeachStep;
import com.gxl.intelligentkitchen.ui.customview.SlideShowView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：解析html获得实体对象
 */
public class HtmlParser {

    /**
     * 获得大概的美食集合
     * @param msg
     * @return
     */
    public static  List<FoodGeneralItem> parserHtml(String msg) {
        String content_personame = null;
        List<FoodGeneralItem> foodsItemList=new ArrayList<>();
        org.jsoup.nodes.Document doc = Jsoup.parse(msg);
        Elements units = doc.getElementsByClass("listtyle1");
        Elements detail_units = doc.getElementsByClass("c1");
        Elements date_units = doc.getElementsByClass("c2");
        for (int i = 0; i < units.size(); i++) {
            FoodGeneralItem foodsItem = new FoodGeneralItem();
            Element detail_ele = units.get(i);
            Elements links = detail_ele.getElementsByTag("a");
            String link = links.get(0).attr("href");
            String title = links.get(0).attr("title");
            Element imagelink_ele = detail_ele.getElementsByTag("img").get(0);
            String imagelink = imagelink_ele.attr("src");
            Element content_ele = detail_units.get(i);
            Elements content_pinglus = detail_ele.getElementsByTag("span");
            String content_pinglun = content_pinglus.get(0).text();
            Elements content_pername = detail_ele.getElementsByTag("em");
            if(content_pername.size()!=0) {
                content_personame = content_pername.get(0).text();
            }
            Element date_ele = date_units.get(i);
            Elements date_shijian = detail_ele.getElementsByTag("ul");
            String content_bushu = date_shijian.get(0)
                    .getElementsByClass("li1").text();
            String content_weidao = date_shijian.get(0)
                    .getElementsByClass("li2").text();
            String Personname = content_personame;
            String Date = content_bushu;
            foodsItem.setTitle(title);
            foodsItem.setLink(link);
            foodsItem.setWriter(Personname);
            foodsItem.setTaste(content_weidao);
            foodsItem.setDate(Date);
            foodsItem.setDiscuss(content_pinglun);
            foodsItem.setImgLink(imagelink);
            foodsItemList.add(foodsItem);
        }
        return foodsItemList;
    }

    /**
     * 解析手机端的Html获得详细的做菜步骤
     * @param canshu
     * @return
     */
    public static FoodDetailTeachItem parserHtmlToDetailInMobile(String canshu) {
        FoodDetailTeachItem detailTeachList=new FoodDetailTeachItem();
        ArrayList<FoodAccessories> accessoriesList=new ArrayList<>();
        ArrayList<FoodTeachStep>   teachStepList=new ArrayList<>();
        Document doc = Jsoup.parse(canshu);
        String food_title = doc.getElementsByClass("fade_topbar").get(0)
                .getElementsByTag("h2").get(0).text();
        Log.i("food_title", food_title);
        detailTeachList.setFoodTitle(food_title);

        String showfood_text = doc.getElementsByClass("cp_main").attr("style");
        Log.i("showfood_text", showfood_text);
        detailTeachList.setFoodIntroduction(showfood_text);

        String NewStr = showfood_text.substring(showfood_text.indexOf("(") + 1,
                showfood_text.lastIndexOf(")"));
        Log.i("showfood_text", NewStr);
        detailTeachList.setFoodImage(NewStr);

        String show_writer_name_text = doc.getElementsByClass("con_main")
                .get(0).getElementsByTag("a").get(0).getElementsByTag("span")
                .text();
        detailTeachList.setWriteName(show_writer_name_text);

        String show_writer_image_text = doc.getElementsByClass("con_main")
                .get(0).getElementsByTag("a").get(0).getElementsByTag("img")
                .attr("src");
        detailTeachList.setWritePhoto(show_writer_image_text);

        /*
        * 下面解析出做该菜要用到的食材和辅料
        */
        if (doc.select("div.material_coloum_type1").size() > 0) {
            Elements units = doc.getElementsByClass("material_coloum_type1");
            Elements units_zl = units.get(0).getElementsByClass(
                    "material_coloum");
            for (int i = 0; i < units_zl.size(); i++) {
                FoodAccessories accessories = new FoodAccessories();
                Element content_zl_name = units_zl.get(i)
                        .getElementsByTag("span").get(0);
                String content_name = content_zl_name.text();
                Element content_zl_shuliang = units_zl.get(i)
                        .getElementsByTag("em").get(0);
                String content_shuliang = content_zl_shuliang.text();
                accessories.setName(content_name);
                accessories.setNumber(content_shuliang);
                Log.i("content_name", content_name);
                Log.i("content_shuliang", content_shuliang);
                accessoriesList.add(accessories);
            }
        }

        if (doc.select("div.material_coloum_type2").size() > 0) {
            Elements units_fuliao = doc
                    .getElementsByClass("material_coloum_type2");
            Elements Units_fuliao = units_fuliao.get(0).getElementsByClass(
                    "material_coloum");
            Log.i("content_fuliao_shuliang", Units_fuliao.size() + "");
            for (int i = 0; i < Units_fuliao.size(); i++) {
                FoodAccessories fuliao = new FoodAccessories();
                Element content_fuliao = Units_fuliao.get(i)
                        .getElementsByTag("span").get(0);
                String content_fuliao_name = content_fuliao.text();
                Element content_zl_shuliang = Units_fuliao.get(i)
                        .getElementsByTag("em").get(0);
                String content_fuliao_shuliang = content_zl_shuliang.text();
                Log.i("content_fuliao_name", content_fuliao_name);
                Log.i("content_fuliao_shuliang", content_fuliao_shuliang);
                fuliao.setName(content_fuliao_name);
                fuliao.setNumber(content_fuliao_shuliang);
                accessoriesList.add(fuliao);
            }
        }


        /**
         * 下面解析出详细的做菜方法
         */
        if (doc.select("div.cp_step").size() > 0) {
            Elements units_teach = doc.getElementsByClass("cp_step").get(0)
                    .getElementsByTag("h2");
            String teachtext;
            String imagelink;
            for (int i = 0; i < units_teach.size() - 1; i++) {
                FoodTeachStep foodcontent = new FoodTeachStep();
                Log.i("teachtext", units_teach.get(i).text());
                if (units_teach.get(i).nextElementSibling()
                        .getElementsByTag("img").size() > 0) {
                    imagelink = units_teach.get(i).nextElementSibling()
                            .getElementsByTag("img").get(0).attr("src");
                    teachtext = units_teach.get(i).nextElementSibling()
                            .nextElementSibling().text();
                    Log.i("imagelink", imagelink);
                    Log.i("teachtext", teachtext);
                    showfood_text = imagelink;
                } else {
                    imagelink = "noimagelink";
                    teachtext = units_teach.get(i).nextElementSibling().text();
                    Log.i("imagelink", imagelink);
                    Log.i("teachtext", teachtext);
                }
                foodcontent.setNum(units_teach.get(i).text());
                foodcontent.setImagelink(imagelink);
                foodcontent.setTeachtext(teachtext);
                teachStepList.add(foodcontent);
            }
        }
        detailTeachList.setAccessoriesList(accessoriesList);
        detailTeachList.setStepList(teachStepList);
        return detailTeachList;
    }


    /**
     * 解析电脑端的Html获得详细的做菜步骤
     * @param canshu
     * @return
     */
    public static FoodDetailTeachItem parserHtmlToDetailInPc(String canshu) {
        FoodDetailTeachItem detailTeachList=new FoodDetailTeachItem();
        ArrayList<FoodAccessories> accessoriesList=new ArrayList<>();
        ArrayList<FoodTeachStep>   teachStepList=new ArrayList<>();
        Document doc = Jsoup.parse(canshu);
        String food_title = doc.getElementsByClass("info1").get(0)
                .getElementsByTag("a").get(0).text();
        Log.i("food_title",food_title);
        detailTeachList.setFoodTitle(food_title);

        String showfood_image = doc.getElementsByClass("cp_headerimg_w").get(0)
                .getElementsByTag("img").get(0).attr("src");
        detailTeachList.setFoodImage(showfood_image);

        String showdetail_text = doc.getElementsByClass("materials").get(0)
                .getElementsByTag("p").get(0).text();
        detailTeachList.setFoodIntroduction(showdetail_text);

        String show_writer_name_text = doc.getElementsByClass("user").get(0)
                .getElementsByClass("info").get(0).getElementsByTag("h4")
                .get(0).getElementsByTag("a").get(0).text();
        detailTeachList.setWriteName(show_writer_name_text);

        String show_writer_image_text = doc.getElementsByClass("user").get(0)
                .getElementsByTag("a").get(0).getElementsByTag("img").get(0)
                .attr("src");
        detailTeachList.setWritePhoto(show_writer_image_text);

        String show_writer_date_text = doc.getElementsByClass("user").get(0)
                .getElementsByClass("info").get(0).getElementsByTag("strong")
                .get(0).text();
        show_writer_date_text="创建于"+show_writer_date_text.substring(0,show_writer_date_text.lastIndexOf('/')-1);
        detailTeachList.setWriteDate(show_writer_date_text);
        Log.i("tishi", food_title);
		/*
		 * 下面解析出做该菜要用到的食材和辅料
		 */
        if (doc.select("div.zl").size() > 0) {
            Elements units = doc.getElementsByClass("zl");
            Elements units_zl = units.get(0).getElementsByTag("li");
            for (int i = 0; i < units_zl.size(); i++) {
                FoodAccessories fuliao = new FoodAccessories();
                Element content_zl_name = units_zl.get(i)
                        .getElementsByClass("c").get(0).getElementsByTag("h4")
                        .get(0).getElementsByTag("a").get(0);
                String content_name = content_zl_name.text();
                Element content_zl_shuliang = units_zl.get(i)
                        .getElementsByClass("c").get(0).getElementsByTag("h4")
                        .get(0).getElementsByTag("span").get(0);
                String content_shuliang = content_zl_shuliang.text();
                fuliao.setName(content_name);
                fuliao.setNumber(content_shuliang);
                Log.i("content_name", content_name);
                Log.i("content_shuliang", content_shuliang);
                accessoriesList.add(fuliao);
            }
        }
        if (doc.select("div.fuliao").size() > 0) {
            Elements units_fuliao = doc.getElementsByClass("fuliao");
            Elements Units_fuliao = units_fuliao.get(0).getElementsByTag("li");
            Log.i("content_fuliao_shuliang", Units_fuliao.size() + "");
            for (int i = 0; i < Units_fuliao.size(); i++) {
                FoodAccessories fuliao = new FoodAccessories();
                Element content_fuliao = Units_fuliao.get(i)
                        .getElementsByTag("h4").get(0).getElementsByTag("a")
                        .get(0);
                String content_fuliao_name = content_fuliao.text();
                Element content_zl_shuliang = Units_fuliao.get(i)
                        .getElementsByTag("span").get(0);
                String content_fuliao_shuliang = content_zl_shuliang.text();
                Log.i("content_fuliao_name", content_fuliao_name);
                Log.i("content_fuliao_shuliang", content_fuliao_shuliang);
                fuliao.setName(content_fuliao_name);
                fuliao.setNumber(content_fuliao_shuliang);
                accessoriesList.add(fuliao);
            }
        }
         /**
         * 下面解析出详细的做菜方法
         */
        if (doc.select("div.content").size() > 0) {
            Log.i("1111", "1111");
            Elements units_teach = doc.getElementsByClass("measure").get(0)
                    .getElementsByClass("edit").get(0)
                    .getElementsByClass("content");
            Log.i("units_teach", units_teach.size() + "");
            String teachtext;
            String imagelink;
            for (int i = 0; i < units_teach.size(); i++) {
                FoodTeachStep foodcontent = new FoodTeachStep();

                if (units_teach.get(i).getElementsByClass("c").get(0)
                        .getElementsByTag("p").size() == 2) {
                    teachtext = units_teach.get(i).getElementsByClass("c")
                            .get(0).getElementsByTag("p").get(0).text();
                    imagelink = units_teach.get(i).getElementsByClass("c")
                            .get(0).getElementsByTag("p").get(1)
                            .getElementsByTag("img").get(0).attr("src");
                } else {
                    if (units_teach.get(i).getElementsByClass("c").get(0)
                            .getElementsByTag("p").get(0).hasAttr("src")) {
                        teachtext = "";
                        imagelink = units_teach.get(i).getElementsByClass("c")
                                .get(0).getElementsByTag("p").get(0)
                                .getElementsByTag("img").get(0).attr("src");
                    } else {
                        teachtext = units_teach.get(i).getElementsByClass("c")
                                .get(0).getElementsByTag("p").get(0).text();
                        imagelink = "noimagelink";
                    }
                }

                Log.i("teachtext", teachtext);
                Log.i("imagelink", imagelink);
                foodcontent.setNum(String.valueOf(i + 1));
                foodcontent.setImagelink(imagelink);
                foodcontent.setTeachtext(teachtext);
                teachStepList.add(foodcontent);
            }
        } else {
            Elements units_teach = doc.getElementsByClass("measure").get(0)
                    .getElementsByClass("edit").get(0).getElementsByTag("p");
            Log.i("units_teach", units_teach.size() + "");
            Log.i("units_teach_texy", units_teach.get(0).text());
            int num = 0;
            for (int i = 0, j = 0; i < units_teach.size() - 1; i++, j++) {
                FoodTeachStep foodcontent = new FoodTeachStep();
                String teachtext = null;
                String imagelink = null;
                if (units_teach.get(i).getElementsByTag("em").size() > 0) {
                    teachtext = units_teach.get(i).text();
                    int flag = i + 1;
                    if (units_teach.get(flag).getElementsByClass("conimg")
                            .size() > 0) {
                        imagelink = units_teach.get(flag)
                                .getElementsByClass("conimg").get(0)
                                .attr("src");
                        i++;
                    } else {
                        imagelink = "noimagelink";
                    }
                } else if (units_teach.get(i).getElementsByTag("img").size() > 0) {
                    teachtext = "";
                    imagelink = units_teach.get(i).getElementsByClass("conimg")
                            .get(0).attr("src");
                } else {
                    continue;
                }
                Log.i("teachtext", teachtext);
                Log.i("imagelink", imagelink);
                foodcontent.setNum(String.valueOf(++num));
                foodcontent.setImagelink(imagelink);
                foodcontent.setTeachtext(teachtext);
                teachStepList.add(foodcontent);
            }
        }
        detailTeachList.setAccessoriesList(accessoriesList);
        detailTeachList.setStepList(teachStepList);
        return detailTeachList;
    }


    public static List<SlideShowView.SliderShowViewItem> parserHtmlToSliderShow(String msg) {
        ArrayList<SlideShowView.SliderShowViewItem> list=new ArrayList<>();
        SlideShowView.SliderShowViewItem viewItem = null;
        org.jsoup.nodes.Document doc = Jsoup.parse(msg);
        Elements content = doc.getElementsByClass("zzw_item_2");
        Elements links = content.get(0).getElementsByTag("li");
        Log.i("msg", msg);
        Log.i("content.size()", content.size() + "");
        Log.i("links.size()", links.size() + "");

        for (int i = 0; i < links.size(); i++) {
            viewItem = new SlideShowView.SliderShowViewItem();
            Elements links_a = links.get(i).getElementsByTag("a");
            String link = links_a.get(0).attr("href");
            Element imagelink_ele = links_a.get(0).getElementsByTag("img")
                    .get(0);
            String imagelink = imagelink_ele.attr("src");
            String foodname = links_a.get(0).attr("title");
            viewItem.setImgLink(imagelink);
            viewItem.setLink(link);
            viewItem.setName(foodname);
            Log.i("link", link);
            Log.i("imagelink", imagelink);
            Log.i("foodname", foodname);
            list.add(viewItem);
        }
        return list;
    }
}
