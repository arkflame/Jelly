package dev._2lstudios.jelly.commands;

import org.bukkit.entity.Player;

import dev._2lstudios.jelly.JellyPlugin;
import dev._2lstudios.jelly.annotations.Command;
import dev._2lstudios.jelly.player.PluginPlayer;

public class CommandContext {
    private final CommandArguments arguments;
    private final Command command;
    private final JellyPlugin plugin;
    private final PluginCommandSender sender;

    private final boolean isPlayer;

    private PluginPlayer pluginPlayer;

    public CommandContext(final JellyPlugin plugin, final Command command, final PluginCommandSender sender, final CommandArguments arguments) {
        this.arguments = arguments;
        this.command = command;
        this.plugin = plugin;
        this.sender = sender;

        this.isPlayer = sender.getBukkitCommandSender() instanceof Player;

        if (plugin.getPluginPlayerManager() != null && this.isExecutedByPlayer()) {
            this.pluginPlayer = plugin.getPluginPlayerManager().getPlayer(this.getPlayer());
        }
    }

    public CommandArguments getArguments() {
        return this.arguments;
    }

    public Command getCommand () {
        return this.command;
    }

    public boolean isExecutedByPlayer() {
        return this.isPlayer;
    }

    public Player getPlayer() {
        if (this.isExecutedByPlayer()) {
            return (Player) this.sender.getBukkitCommandSender();
        } else {
            return null;
        }
    }

    public PluginPlayer getPluginPlayer() {
        return this.pluginPlayer;
    }

    public JellyPlugin getPlugin() {
        return this.plugin;
    }

    public PluginCommandSender getSender() {
        return this.sender;
    }
}
