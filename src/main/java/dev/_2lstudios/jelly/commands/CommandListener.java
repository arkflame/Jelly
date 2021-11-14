package dev._2lstudios.jelly.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import dev._2lstudios.jelly.annotations.Command;
import dev._2lstudios.jelly.errors.ArgumentParserException;
import dev._2lstudios.jelly.errors.CommandException;
import dev._2lstudios.jelly.errors.PlayerOfflineException;

public abstract class CommandListener {

    /* Subcommand handling */
    private List<CommandListener> subcommands = new ArrayList<>();

    public void addSubcommand(final CommandListener obj) {
        this.subcommands.add(obj);
    }

    
    public CommandListener getSubcommand(final String name) {
        for (final CommandListener subcommand : this.subcommands) {
            Command cmd = subcommand.getClass().getAnnotation(Command.class);
            if (cmd.name().equalsIgnoreCase(name)) {
                return subcommand;
            }
        }

        return null;
    }

    /* Utils */
    public String mapCommandListToString(final String format, final String separator) {
        String output = null;

        for (final CommandListener cmd : this.subcommands) {
            final Command cmdInfo = cmd.getClass().getAnnotation(Command.class);
            final String entry = format.replace("{name}", cmdInfo.name()).replace("{permission}", cmdInfo.permission())
                    .replace("{usage}", cmdInfo.usage()).replace("{description}", cmdInfo.description());

            if (output == null) {
                output = entry;
            } else {
                output += separator + entry;
            }
        }

        return output;
    }

    /* Override default methods */
    public void onMissingPermissions (final CommandContext context, final String permission) {
        context.getPluginPlayer().sendMessage("&cError, missing permission &e" + permission + "&c.");
    }

    public void onBadArgument (final CommandContext context, final ArgumentParserException e) {
        context.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', context.getCommand().usage()));
        context.getSender().sendMessage("§c" + e.getMessage());
    }

    public void onOnlyConsole (final CommandContext context) {
        context.getPluginPlayer().sendMessage("&cThis command can run only in console.");
    }

    public void onOnlyPlayer (final CommandContext context) {
        context.getSender().sendMessage("§cThis command can run only by a player.");
    }

    public void onMissingArguments (final CommandContext context, final int provided, final int required) {
        context.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', context.getCommand().usage()));
    }

    public void onPlayerOffline (final CommandContext context, final PlayerOfflineException e) {
        context.getSender().sendMessage("§c" + e.getMessage());
    }

    public void onCommandException (final CommandContext context, final CommandException e) {
        context.getSender().sendMessage("§c" + e.getMessage());
    }

    /* Abstract methods */
    public abstract void handle(final CommandContext context) throws Exception;
}