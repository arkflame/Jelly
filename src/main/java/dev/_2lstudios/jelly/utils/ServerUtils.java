package dev._2lstudios.jelly.utils;

import org.bukkit.Bukkit;

public class ServerUtils {
    public static boolean isLegacy() {
        final String version = Bukkit.getVersion();
        return version.contains("1.12")
            || version.contains("1.11")
            || version.contains("1.10")
            || version.contains("1.9")
            || version.contains("1.8")
            || version.contains("1.7")
            || version.contains("1.6")
            || version.contains("1.5")
            || version.contains("1.4");
    }
}
