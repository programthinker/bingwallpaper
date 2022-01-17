package com.example.bingwallpaper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.time.LocalDate;

/**
 * @ProjectName bingwallpaper
 * @PackageName com.example.bingwallpaper
 * @ClassName BingWallPaperDownload
 * @Author zhanggeyang
 * @Date 2021-12-09 17:00
 * @Description
 * @Version 1.0
 */

public class BingWallPaperDownload {

    //dadasdasdsadsa
    //官方壁纸API，包含下载地址，壁纸中文简介，
    private static String BING_API = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
    //自定义的下载地址前缀
    private static String BING_URL = "https://cn.bing.com";

    public static void main(String[] args) throws Exception {
        HttpGet httpGet = new HttpGet(BING_API);
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpGet);
        String content = EntityUtils.toString(httpResponse.getEntity());

        JSONObject jsonObject = JSON.parseObject(content);
        JSONArray jsonArray = jsonObject.getJSONArray("images");

        jsonObject = (JSONObject) jsonArray.get(0);
        // 图片地址
        String url = BING_URL + (String) jsonObject.get("url");
        // 图片地址
        String realDownLoadUrl = BING_URL + (String) jsonObject.get("urlbase") + "_UHD";

        //获取图片的后缀名
        String split = url.split("&")[0];
        String suffix = split.substring(split.length() - 4, split.length());

        //图片的版权信息描述，可用于图片名
        String copyright = (String) jsonObject.get("copyright");
        String imageName = "BingWallPaper-" + LocalDate.now() + "-" + copyright.split("\\(")[0].trim() + "-4K" + suffix;

        String imageUrl = realDownLoadUrl + suffix;

        //将每一天的下载链接和对应的图片名称保存到README.md

        ReadMeUtils.writeToReadme(imageName, imageUrl);
    }

}
