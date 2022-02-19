package dev._2lstudios.jelly.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import dev._2lstudios.jelly.gui.CustomInventoryHolder;
import dev._2lstudios.jelly.gui.ItemBuilder;
import dev._2lstudios.jelly.gui.ItemCollection;

public class InventoryCloseListener implements Listener {
    
    @EventHandler
    public void onInventoryClose (final InventoryCloseEvent e) {
        final Inventory inventory = e.getInventory();
        final InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof CustomInventoryHolder)) {
            return;
        }

        final ItemCollection itemCollection = ((CustomInventoryHolder) holder).getItemCollection();
        for (final ItemBuilder item : itemCollection.getItems()) {
            if (item.getItemDragged() != null && item.isDraggable()) {
                e.getPlayer().getInventory().addItem(item.getItemDragged());
            }
        }
    }
}
