package dev._2lstudios.jelly.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import dev._2lstudios.jelly.JellyPlugin;
import dev._2lstudios.jelly.annotations.Command;
import dev._2lstudios.jelly.errors.ArgumentParserException;
import dev._2lstudios.jelly.errors.CommandException;
import dev._2lstudios.jelly.errors.PlayerOfflineException;
import dev._2lstudios.jelly.utils.ArrayUtils;

public class CommandHandler implements CommandExecutor {

    private final Map<String, CommandListener> commands;
    private final JellyPlugin plugin;

    public CommandHandler(final JellyPlugin plugin) {
        this.commands = new HashMap<>();
        this.plugin = plugin;
    }

    public boolean hasCommand(final String name) {
        return this.commands.containsKey(name);
    }

    public boolean hasSilentcommand(final String name) {
        CommandListener listener = this.commands.get(name);
        if (listener != null) {
            Command command = this.commands.get(name).getClass().getAnnotation(Command.class);
            if (command.silent()) {
                return true;
            }
        }

        return false;
    }

    public void addCommand(final CommandListener listener) {
        if (listener.getClass().isAnnotationPresent(Command.class)) {
            Command command = listener.getClass().getAnnotation(Command.class);

            if (!command.silent()) {
                this.plugin.getCommand(command.name()).setExecutor(this);
            }

            for (final String alias : command.aliases()) {
                this.commands.put(alias, listener);
            }

            this.commands.put(command.name(), listener);
        }
    }

    public boolean runCommand(CommandSender sender, String name, String[] args) {
        CommandListener auxListener = this.commands.get(name);

        while (args.length > 0) {
            CommandListener subCommand = auxListener.getSubcommand(args[0]);
            if (subCommand != null) {
                auxListener = subCommand;
                args = ArrayUtils.shift(args, 0);
            } else {
                break;
            }
        }

        final CommandListener listener = auxListener;

        // Read command data from annotation
        Command command = listener.getClass().getAnnotation(Command.class);

        // Parse arguments
        final Object[] argList = new Object[args.length];
        final int argumentDefinedLength = command.arguments().length;
        Exception exception = null;

        for (int i = 0; i < args.length; i++) {
            if (argumentDefinedLength >= (i + 1)) {
                final Class<?> clazz = command.arguments()[i];
                try {
                    final Object arg = CommandArgumentParser.parse(clazz, i + 1, args[i]);
                    argList[i] = arg;
                } catch (final Exception e) {
                    exception = e;
                    break;
                }
            } else {
                argList[i] = args[i];
            }
        }

        // Create command context
        final CommandArguments arguments = new CommandArguments(argList);
        final CommandContext context = new CommandContext(this.plugin, command, sender, arguments);

        if (exception != null) {
            if (exception instanceof ArgumentParserException) {
                listener.onBadArgument(context, (ArgumentParserException) exception);
            }

            else if (exception instanceof PlayerOfflineException) {
                listener.onPlayerOffline(context, (PlayerOfflineException) exception);
            }
        }

        // Check execution target
        if (sender instanceof Player && command.target() == CommandExecutionTarget.ONLY_CONSOLE) {
            listener.onOnlyConsole(context);
            return true;
        } else if (sender instanceof ConsoleCommandSender && command.target() == CommandExecutionTarget.ONLY_PLAYER) {
            listener.onOnlyPlayer(context);
            return true;
        }

        // Check for permission
        final String permission = command.permission();
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            listener.onMissingPermissions(context, permission);
            return true;
        }

        // Check for min arguments
        int minArguments = command.minArguments() == Integer.MIN_VALUE ? command.arguments().length
                : command.minArguments();

        if (args.length < minArguments) {
            listener.onMissingArguments(context, args.length, minArguments);
            return true;
        }

        // Execute command
        try {
            if (command.async()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            listener.handle(context);
                        } catch (Exception e) {
                            if (e instanceof CommandException) {
                                listener.onCommandException(context, (CommandException) e);
                            } else {
                                sender.sendMessage(
                                        "§cFatal exception ocurred while executing command. See console for more details.");
                                e.printStackTrace();
                            }
                        }
                    }
                }.runTaskAsynchronously(plugin);
            } else {
                listener.handle(context);
            }
        } catch (Exception e) {
            if (e instanceof CommandException) {
                listener.onCommandException(context, (CommandException) e);
            } else {
                sender.sendMessage("§cFatal exception ocurred while executing command. See console for more details.");
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmdInfo, String label, String[] args) {
        String name = cmdInfo.getName().toLowerCase();
        return this.runCommand(sender, name, args);
    }

}
