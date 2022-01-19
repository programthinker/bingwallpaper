package com.example.bingwallpaper;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @ProjectName bingwallpaper
 * @PackageName com.example.bingwallpaper
 * @ClassName ReadMeUtils
 * @Author zhanggeyang
 * @Date 2021-12-09 17:18
 * @Description
 * @Version 1.0
 */

public class ReadMeUtils {
    private static Path readmePath = Paths.get("READ.md");

    public static void writeToReadme(String imageName, String url) throws Exception {
        if (!Files.exists(readmePath)) {
            Files.createFile(readmePath);
        }
        Files.write(readmePath, System.lineSeparator().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        Files.write(readmePath, (imageName + "&&" + url).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }

}
