package com.projectX.utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class JsonHelper {
    private static final ISettingsFile environment = new JsonSettingsFile("config.json");

    public static String getValueFromJson(String key) {
        return environment.getValue(String.format("/%s", key)).toString();
    }

    public static <T> T getJsonData(String pathToFile, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        File fileData = new File(pathToFile);
        try {
            return objectMapper.readValue(fileData, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("File not found or another error", e);
        }
    }

}