package com.example.bingwallpaper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @ProjectName bingwallpaper
 * @PackageName com.example.bingwallpaper
 * @ClassName BingWallPaperDownload
 * @Author zhanggeyang
 * @Date 2021-12-09 17:00
 * @Description
 * @Version 1.0
 */

public class BingWallPaperDownloadLocal {

    //自定义的下载地址前缀
    private static String CN_BING_URL = "https://cn.bing.com";
    private static String downloadLocation = "/Users/zhanggeyang/Pictures/BingWallPaper/";

    public static void main(String[] args) throws Exception {
        //idx:0：到今天，-1：到明天，1：到昨天
        //n:返回的图片数量，最多8张
        //mkt:地区
        String[] imageAPI = {
                "https://bing.com/HPImageArchive.aspx?format=js&idx=0&n=8&mkt=zh-CN",
                "https://bing.com/HPImageArchive.aspx?format=js&idx=0&n=8&mkt=EN-US",
                "https://bing.com/HPImageArchive.aspx?format=js&idx=0&n=8&mkt=EN-US"
        };
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < imageAPI.length; i++) {
            HttpGet httpGet = new HttpGet(imageAPI[i]);
            CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpGet);
            String content = EntityUtils.toString(httpResponse.getEntity());
            JsonNode node = objectMapper.readTree(content);
            JsonNode images = node.get("images");
            for (long l = 0; l < images.size(); l++) {
                JsonNode image = images.get((int) l);
                String enddate = image.get("enddate").asText();
                String year = enddate.substring(0, 4);
                String month = enddate.substring(4, 6);
                String day = enddate.substring(6);
                String url = CN_BING_URL + image.get("url").asText();
                String realDownLoadUrl = CN_BING_URL + image.get("urlbase").asText() + "_UHD";
                String split = url.split("&")[0];
                String suffix = split.substring(split.length() - 4, split.length());
                String copyright = image.get("copyright").asText();
                String imageName = ("BingWallPaper-" + year + "-" + month + "-" + day + "-" + copyright.split("\\(")[0].trim() + "-4K" + suffix).replaceAll("/", "-");
                String imageUrl = realDownLoadUrl + suffix;
                HttpGet httpGet1 = new HttpGet(imageUrl);
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet1);
                InputStream inputStream = closeableHttpResponse.getEntity().getContent();
                Path path = Paths.get(downloadLocation + imageName);
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
                //ReadMeUtils.writeToReadme(imageName, imageUrl);
            }
        }

    }

}
