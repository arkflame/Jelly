package dev._2lstudios.jelly.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

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

    public void openSync(final Plugin plugin, final Player player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);
        this.init(context);
        final Inventory inventory = context.buildInventory();
        if (!Bukkit.isPrimaryThread()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.openInventory(inventory);
                }
            }.runTask(plugin);
        } else {
            player.openInventory(inventory);
        }
    }

    public void openAsync(final Plugin plugin, final Player player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);
        this.init(context);
        final Inventory inventory = context.buildInventory();
        if (Bukkit.isPrimaryThread()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.openInventory(inventory);
                }
            }.runTaskAsynchronously(plugin);
        } else {
            player.openInventory(inventory);
        }
    }
}
