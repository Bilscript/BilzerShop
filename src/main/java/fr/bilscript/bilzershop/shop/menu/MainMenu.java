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
		buy.editMeta(meta -> meta.setDisplayName("§aAcheter des objets"));

		ItemStack sell = new ItemStack(Material.REDSTONE);
		sell.editMeta(meta -> meta.setDisplayName("§cVendre des objets"));

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

