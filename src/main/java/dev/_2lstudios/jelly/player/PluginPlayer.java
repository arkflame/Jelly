package dev._2lstudios.jelly.player;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import dev._2lstudios.jelly.JellyPlugin;
import dev._2lstudios.jelly.utils.ReflectionUtils;
import dev._2lstudios.jelly.utils.ServerUtils;

public class PluginPlayer {
    private final JellyPlugin plugin;
    private final Player player;

    public PluginPlayer(final JellyPlugin plugin, final Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public Player getBukkitPlayer() {
        return this.player;
    }

    public String getLocale() {
        try {
            final Object ep = ReflectionUtils.getMethod("getHandle", player.getClass()).invoke(player, (Object[]) null);
            final Field f = ep.getClass().getDeclaredField("locale");
            f.setAccessible(true);
            return (String) f.get(ep);
        } catch (final Exception e) {
            e.printStackTrace();
            return "en";
        }
    }

    public String getI18nString(final String key) {
        final String locale = this.getLocale().toLowerCase();
        final String value = this.plugin.getLanguageManager().getLanguage(locale).getString(key);
        return value != null ? value : "&cMissing translation key " + key + " for locale " + locale + ".";
    }

    public void playSound(final Sound sound) {
        if (sound != null) {
            this.getBukkitPlayer().playSound(this.getBukkitPlayer().getLocation(), sound, 1, 1);
        }
    }

    public void teleport(final Location loc) {
        this.getBukkitPlayer().teleport(loc);
    }

    public void sendTitle(final String title, final String subtitle, final int duration) {
        this.sendTitle(title, subtitle, 2, duration * 20, 2);
    }

    @SuppressWarnings("deprecation")
    public void sendTitle(final String title, final String subtitle, final int fadeInTime, final int showTime,
            final int fadeOutTime) {
        if (ServerUtils.isLegacy()) {
            this.getBukkitPlayer().resetTitle();
            this.getBukkitPlayer().sendTitle(title, subtitle);
        } else {
            this.getBukkitPlayer().sendTitle(title, subtitle, fadeInTime, showTime, fadeOutTime);
        }
    }

    public void sendMessage(final String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendI18nMessage(final String key) {
        this.sendMessage(this.getI18nString(key));
    }
}
