package com.example.bingwallpaper;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @ProjectName bingwallpaper
 * @PackageName com.example.bingwallpaper
 * @ClassName DownloadUtils
 * @Author zhanggeyang
 * @Date 2021-12-09 21:07
 * @Description
 * @Version 1.0
 */

public class DownloadUtils {

    private static Path readmePath = Paths.get("READ.md");

    private static String downloadLocation = "/Users/zhanggeyang/Pictures/BingWallPaper/";

    //解析readme，下载到本地
    public static void downloadImage() throws Exception {

        List<String> strings = Files.readAllLines(readmePath);

        for (String string : strings) {
            String name = string.split("&&")[0];
            String url = string.split("&&")[1];
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpGet);
            InputStream content = httpResponse.getEntity().getContent();
            Path path = Paths.get(downloadLocation + name);
            Files.copy(content,path, StandardCopyOption.REPLACE_EXISTING);
            IOUtils.closeQuietly(content);
        }
    }

    public static void main(String[] args) throws Exception {
        downloadImage();
    }

}
