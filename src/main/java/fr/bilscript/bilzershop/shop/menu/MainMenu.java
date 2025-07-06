package fr.bilscript.bilzershop.shop.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class MainMenu implements InventoryHolder {
	private final Inventory inventory;

	public MainMenu() {
		this.inventory = Bukkit.createInventory(this, 9, "§6§lBilzerShop");

		ItemStack buy = new ItemStack(Material.BARREL);
		ItemMeta buyMeta = buy.getItemMeta();
		buyMeta.setDisplayName("§aAcheter des objets");
		buy.setItemMeta(buyMeta);

		ItemStack sell = new ItemStack(Material.REDSTONE);
		ItemMeta sellMeta = sell.getItemMeta();
		sellMeta.setDisplayName("§cVendre des objets");
		sell.setItemMeta(sellMeta);

		inventory.setItem(3, buy);
		inventory.setItem(5, sell);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public static void open(Player player) {
		player.openInventory(new MainMenu().getInventory());
	}
}

