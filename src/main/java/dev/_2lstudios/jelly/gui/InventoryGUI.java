package dev._2lstudios.jelly.gui;

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

    public void openAsync(final Plugin plugin, final Player player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);

        new BukkitRunnable() {
            @Override
            public void run() {
                init(context);
                final Inventory inv = context.buildInventory();
                new BukkitRunnable() {
                    public void run() {
                        player.openInventory(inv);
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
}
