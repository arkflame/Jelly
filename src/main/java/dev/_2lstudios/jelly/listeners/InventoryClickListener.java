package dev._2lstudios.jelly.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.jelly.gui.CustomInventoryHolder;
import dev._2lstudios.jelly.gui.ItemBuilder;
import dev._2lstudios.jelly.gui.ItemCollection;

public class InventoryClickListener implements Listener {

    private final Plugin plugin;

    public InventoryClickListener (final Plugin plugin) {
        this.plugin = plugin;
    }

    private boolean handleClick (final Inventory inventory, final int slot, final ClickType click) {
        final InventoryHolder holder = inventory.getHolder();
        final ItemCollection itemCollection = ((CustomInventoryHolder) holder).getItemCollection();
        final ItemBuilder item = itemCollection.getItemAtSlot(slot);

        if (item != null && click == ClickType.LEFT) {
            item.handleLeftClick();
        } else if (item != null && click == ClickType.RIGHT) {
            item.handleRightClick();
        }
                
        if (item == null || !item.isDraggable()) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        System.out.println(e.getCursor());
        if (e.getWhoClicked() instanceof Player && (e.getSlotType() == SlotType.CONTAINER || e.getSlotType() == SlotType.QUICKBAR)) {
            final Inventory clickedInventory = e.getClickedInventory();
            final InventoryHolder clickedHolder = clickedInventory.getHolder();

            if (clickedHolder instanceof CustomInventoryHolder) {
                if (this.handleClick(e.getClickedInventory(), e.getSlot(), e.getClick())) {
                    e.setCancelled(true);
                    e.setResult(Result.DENY);
                }

                else {

                }
            }
            

            /*
            final Inventory inventory = this.getInventory(e);
            final InventoryHolder holder = inventory.getHolder();
            final AtomicInteger slot = new AtomicInteger(e.getSlot());

            if (!(holder instanceof CustomInventoryHolder)) {
                return;
            }

            if (e.isShiftClick()) {
                slot.set(e.getInventory().firstEmpty());
            }

            final ItemCollection itemCollection = ((CustomInventoryHolder) inventory.getHolder()).getItemCollection();
            final ItemBuilder item = itemCollection.getItemAtSlot(slot.get());

            if (item == null || !item.isShow()) {
                return;
            }

            if (!item.isDraggable()) {
                e.setCancelled(true);
                e.setResult(Result.DENY);
                
                switch (e.getClick()) {
                    case LEFT:
                        item.handleLeftClick();
                        break;
                    case RIGHT:
                        item.handleRightClick();
                        break;
                    default:
                        break;
                }
            } /* else {
                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                    final ItemStack itemInSlot = e.getInventory().getItem(slot.get());

                    if (item.getItemDragged() == null && itemInSlot != null) {
                        item.handleDragIn(itemInSlot);
                    }

                    else if (item.getItemDragged() != null && itemInSlot == null) {
                        item.handleDragOut();
                    }
                }, 2);
            } */
        }
    }
}
