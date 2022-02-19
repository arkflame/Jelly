package dev._2lstudios.jelly.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev._2lstudios.jelly.JellyPlugin;
import dev._2lstudios.jelly.commands.CommandHandler;

public class CommandPreProcessListener implements Listener {

    private final CommandHandler handler;

    public CommandPreProcessListener(final JellyPlugin plugin) {
        this.handler = plugin.getCommandHandler();
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreCommand(final PlayerCommandPreprocessEvent e) {
        if (!e.isCancelled()) {
        String parts = e.getMessage().substring(1);
        String command = parts.split(" ", 1)[0];
        String[] args = null;

        if (parts.contains(" ")) {
            args = parts.split(" ", 1)[1].split(" ");
        } else {
            args = new String[0];
        }

        if (this.handler.hasSilentcommand(command)) {
            e.setCancelled(true);
            this.handler.runCommand(e.getPlayer(), command, args);
        }}
    }
}
