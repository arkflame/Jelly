package dev._2lstudios.jelly.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class NumberUtils {

    final static private String[] numberNames = new String[] { "Zero", "First", "Second", "Third", "Fourth" };

    public static String formatNumber(final int number) {
        if (NumberUtils.numberNames.length < number) {
            return String.valueOf(number);
        } else {
            return numberNames[number];
        }
    }

    public static int randomNumber(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String formatNumberRoman(int number) {
        final LinkedHashMap<String, Integer> romanNumerals = new LinkedHashMap<String, Integer>();

        romanNumerals.put("M", 1000);
        romanNumerals.put("CM", 900);
        romanNumerals.put("D", 500);
        romanNumerals.put("CD", 400);
        romanNumerals.put("C", 100);
        romanNumerals.put("XC", 90);
        romanNumerals.put("L", 50);
        romanNumerals.put("XL", 40);
        romanNumerals.put("X", 10);
        romanNumerals.put("IX", 9);
        romanNumerals.put("V", 5);
        romanNumerals.put("IV", 4);
        romanNumerals.put("I", 1);

        String res = "";
        
        for(Map.Entry<String, Integer> entry : romanNumerals.entrySet()){
          int matches = number / entry.getValue();
          res += StringUtils.repeat(entry.getKey(), matches);
          number = number % entry.getValue();
        }

        return res;
    }
}
