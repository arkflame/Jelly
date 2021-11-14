package dev._2lstudios.jelly.utils;

public class StringUtils {
    public static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            sb.append(s);
        }

        return sb.toString();
    }

    public static String capitalize (final String str) {
        String[] parts = str.split(" ");
        for (int i = 0; i < parts.length; i++) {
            final String part = parts[i];
            parts[i] = Character.toUpperCase(part.charAt(0)) + part.substring(1);
        }
        return ArrayUtils.join(parts, " ");
    }
}
