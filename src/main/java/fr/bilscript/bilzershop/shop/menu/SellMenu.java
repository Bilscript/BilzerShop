package fr.bilscript.bilzershop.shop.menu;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.config.inventory.InventorySection;
import fr.bilscript.bilzershop.config.inventory.ItemSection;
import fr.bilscript.bilzershop.economy.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellMenu implements InventoryHolder {

	private final Inventory inventory;
	private static InventorySection inventorySection;

	public SellMenu(Inventory inventory) {
		this.inventorySection = BilzerShop.getInstance().getConfiguration().getInventorySection();
		List<ItemSection> items = inventorySection.items();

		this.inventory = Bukkit.createInventory(this, inventorySection.size(), inventorySection.title());
		for (int i = 0; i < items.size(); i++) {
			ItemSection item = items.get(i);
			ItemStack itemStack = new ItemStack(item.material(), item.amount());
			ItemMeta itemMeta = itemStack.getItemMeta();

			if (itemMeta == null) {
				itemMeta.setDisplayName("§e" + item.material().name());
				ArrayList<String> lore = new ArrayList<>();
				lore.add("§aPrix de rachat : " + item.sellPrice() + "$");
				lore.add("§7Clique pour vendre!");
				itemMeta.setLore(lore);
				itemStack.setItemMeta(itemMeta);
			}
			inventory.setItem(i, itemStack);
		}
	}

	public static void sellItem(PlayerData player, int key) {
		List<ItemSection> items = inventorySection.items();
		if (key >= items.size())
			return;
		ItemSection itemSection = items.get(key);
		if (itemSection == null)
			return;
		PlayerInventory invPlayer = player.getPlayer().getInventory();
		for (int i = 0; i < invPlayer.getSize(); i++){

		}
	}

	@Override
	public @NotNull Inventory getInventory() {
		return null;
	}
}
