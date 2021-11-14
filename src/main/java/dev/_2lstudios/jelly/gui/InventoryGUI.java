package dev._2lstudios.jelly.gui;

import org.bukkit.entity.Player;

public abstract class InventoryGUI {

    public abstract void init (final InventoryGUIContext context);
    public abstract String getTitle (final Player player);
    public abstract int getSize (final Player player);

    public boolean isAutoRefresh () {
        return true;
    }

    public void open (final Player player) {
        final InventoryGUIContext context = new InventoryGUIContext(player, this);
        this.init(context);
        player.openInventory(context.buildInventory());
    }
}
