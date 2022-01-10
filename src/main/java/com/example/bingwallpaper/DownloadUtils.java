package com.example.bingwallpaper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            hashMap.put(strings.get(i), strings.get(i + 1));
            i++;
        }
        for (String name : hashMap.keySet()) {
            String url = hashMap.get(name);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            urlConnection.setConnectTimeout(5000);
            InputStream inputStream = urlConnection.getInputStream();

            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            inputStream.close();
            byte[] imageData = byteArrayOutputStream.toByteArray();

            File file = new File(downloadLocation + name);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(imageData);
            fileOutputStream.close();
        }
    }

    public static void main(String[] args) throws Exception {
        downloadImage();
    }
}
