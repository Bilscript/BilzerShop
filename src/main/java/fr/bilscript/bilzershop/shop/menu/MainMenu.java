package fr.bilscript.bilzershop.shop.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu {

	public static void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, "§6§lBilzerShop");

		ItemStack buy = new ItemStack(Material.BUNDLE);
		ItemMeta buyMeta = buy.getItemMeta();
		buyMeta.setDisplayName("§aAcheter des objets");
		buy.setItemMeta(buyMeta);

		ItemStack sell = new ItemStack(Material.REDSTONE);
		ItemMeta sellMeta = sell.getItemMeta();
		sellMeta.setDisplayName("§cVendre des objets");
		sell.setItemMeta(sellMeta);

		inv.setItem(3, buy);
		inv.setItem(5, sell);

		player.openInventory(inv);
	}
}

