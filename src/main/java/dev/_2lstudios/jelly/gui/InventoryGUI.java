package dev._2lstudios.jelly.gui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.jelly.player.PluginPlayer;

public abstract class InventoryGUI {

    private final Map<Integer, Integer> items;
    private final Inventory inventory;
    private InventoryGUI prevGui;

    public InventoryGUI(final String name, final int size, final InventoryGUI prevGui) {
        this.items = new HashMap<>();
        this.inventory = Bukkit.createInventory(null, size, name);
        this.prevGui = prevGui;
    }

    public InventoryGUI(final String name, final int size) {
        this(name, size, null);
    }

    public InventoryGUI(final String name) {
        this(name, 9, null);
    }

    public abstract void init();

    public abstract void handle(final int id, final PluginPlayer player);

    public int calcPos(int x, int y) {
        return (x - 1) + (9 * (y - 1));
    }

    public int getItemID(final int slot) {
        return items.get(slot);
    }

    public void open(final PluginPlayer player) {
        this.init();
        player.getBukkitPlayer().openInventory(this.inventory);
        InventoryManager.openInventory(player.getBukkitPlayer(), this);
    }

    public void addItem(final int id, final ItemStack item, final int slot) {
        this.inventory.setItem(slot, item);
        this.items.put(slot, id);
    }

    public void addItem(final int id, final ItemStack item, final int x, final int y) {
        this.addItem(id, item, this.calcPos(x, y));
    }

    public void addItem(final int id, final ItemStack item) {
        this.addItem(id, item, items.size());
    }

    public ItemStack createItem(final String name, final Material material, final String lore, final int amount) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore.split("\n")));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createItem(final String name, final Material material, final String lore) {
        return this.createItem(name, material, lore, 1);
    }

    public ItemStack createItem(final String name, final Material material) {
        return this.createItem(name, material, "");
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void close(final PluginPlayer player) {
        player.getBukkitPlayer().closeInventory();
        InventoryManager.closeInventory(player.getBukkitPlayer());
    }

    public void back(final PluginPlayer player) {
        if (this.prevGui != null) {
            this.prevGui.open(player);
        } else {
            this.close(player);
        }
    }
}