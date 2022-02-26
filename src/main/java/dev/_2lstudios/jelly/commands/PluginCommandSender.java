package dev._2lstudios.jelly.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import dev._2lstudios.jelly.JellyPlugin;

public class PluginCommandSender {
    protected JellyPlugin plugin;
    protected CommandSender sender;

    public PluginCommandSender(final JellyPlugin plugin, final CommandSender sender) {
        this.plugin = plugin;
        this.sender = sender;
    }

    public CommandSender getBukkitCommandSender() {
        return this.sender;
    }

    public String getLocale() {
        return this.plugin.getLanguageManager().getDefaultLocale();
    }

    public String getI18nString(final String key) {
        final String locale = this.getLocale().toLowerCase();
        final String value = this.plugin.getLanguageManager().getLanguage(locale).getString(key);
        return value != null ? value : "&cMissing translation key " + key + " for locale " + locale + ".";
    }

    public void sendMessage(final String message) {
        this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendI18nMessage(final String key) {
        this.sendMessage(this.getI18nString(key));
    }
}
