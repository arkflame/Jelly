package dev._2lstudios.jelly.gui;

import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class InventoryGUI {

    public abstract void init(final InventoryGUIContext context);

    public abstract String getTitle(final Player player);

    public abstract int getSize(final Player player);

    public boolean isAutoRefresh() {
        return true;
    }

    public void open(final Player player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);
        this.init(context);
        player.openInventory(context.buildInventory());
    }

    public void openAsync(final Plugin plugin, final Player player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);
        final CompletableFuture<Void> future = new CompletableFuture<>();

        future.whenComplete((value, err) -> {
            player.openInventory(context.buildInventory());
        });

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.init(context);
            future.complete(null);
        });
    }
}
