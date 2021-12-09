package com.example.bingwallpaper;

import org.apache.commons.io.FileUtils;

import java.io.File;
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
    private static Path readmePath = Paths.get("README.md");

    public static void writeToReadme(String imageName, String url) throws Exception {
        if (!Files.exists(readmePath)) {
            Files.createFile(readmePath);
        }
        Files.write(readmePath, imageName.getBytes(), StandardOpenOption.APPEND);
        Files.write(readmePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(readmePath, url.getBytes(), StandardOpenOption.APPEND);
        Files.write(readmePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);

    }

}
