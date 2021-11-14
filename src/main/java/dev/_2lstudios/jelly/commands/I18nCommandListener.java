package dev._2lstudios.jelly.commands;

import dev._2lstudios.jelly.errors.ArgumentParserException;
import dev._2lstudios.jelly.errors.CommandException;
import dev._2lstudios.jelly.errors.PlayerOfflineException;

public abstract class I18nCommandListener extends CommandListener {

    private final String missingPermissionKey;
    private final String usageKey;
    private final String onlyConsoleKey;
    private final String offlinePlayerKey;

    public I18nCommandListener (
        final String missingPermissionKey, 
        final String usageKey,
        final String onlyConsoleKey,
        final String offlinePlayerKey
    ) {
        this.missingPermissionKey = missingPermissionKey;
        this.usageKey = usageKey;
        this.onlyConsoleKey = onlyConsoleKey;
        this.offlinePlayerKey = offlinePlayerKey;
    }
    
    @Override
    public void onMissingPermissions (final CommandContext context, final String permission) {
        context.getPluginPlayer().sendI18nMessage(this.missingPermissionKey);
    }

    @Override
    public void onBadArgument (final CommandContext context, final ArgumentParserException e) {
        context.getPluginPlayer().sendI18nMessage(this.usageKey);
    }

    @Override
    public void onOnlyConsole (final CommandContext context) {
        context.getPluginPlayer().sendI18nMessage(this.onlyConsoleKey);
    }

    @Override
    public void onMissingArguments (final CommandContext context, final int provided, final int required) {
        context.getPluginPlayer().sendI18nMessage(this.usageKey);
    }

    @Override
    public void onPlayerOffline (final CommandContext context, final PlayerOfflineException e) {
        context.getPluginPlayer().sendI18nMessage(this.offlinePlayerKey);
    }

    @Override
    public void onCommandException (final CommandContext context, final CommandException e) {
        final String key = e.getI18nKey();
        if (key == null) {
            super.onCommandException(context, e);
        } else {
            context.getPluginPlayer().sendI18nMessage(key);
        }
    }
}
