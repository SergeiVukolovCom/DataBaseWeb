package com.projectX.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@UtilityClass
public class RandomString {
    private static final String latinSymbols = "abcdefghijklmnopqrstuvwxyz";
    private static final String counts = "1234567890";
    private static final Random random = new Random();

    public static String getRandomString() {
        StringBuilder randStringTitle = new StringBuilder();
        int count = random.nextInt(latinSymbols.length());
        for (int i = 0; i<count; i++) {
            randStringTitle.append(latinSymbols.charAt(nextInt() * latinSymbols.length()));
        }
        return randStringTitle.toString();
    }

    public static long getRandomLong() {
        StringBuilder randInt = new StringBuilder();
        int count = Enum.SIXTEEN.getValue();
        for (int i = 0; i<count; i++) {
            randInt.append(counts.charAt(nextInt() * counts.length()));
        }
        return Long.parseLong(randInt.toString());
    }

}