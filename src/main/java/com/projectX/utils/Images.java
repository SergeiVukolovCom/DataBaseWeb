package com.projectX.utils;

import aquality.selenium.browser.AqualityServices;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.ScreenshotException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Images {

    public byte[] createByteFromPng(String pathToScreenShot) {
        try {
            File file = new File(pathToScreenShot);
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to create byte array from PNG: " + e.getMessage(), e);
        }
    }

    public void getScreenShot(String path) {
        File scrFile = AqualityServices.getBrowser().getDriver().getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            throw new ScreenshotException("Failed to capture screenshot", e);
        }
    }

    public String pngToBase64(String path) {
        byte[] pngBytes = createByteFromPng(path);
        return Base64.getEncoder().encodeToString(pngBytes);
    }

}