package fr.bilscript.bilzershop.shop.menu;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.config.inventory.InventorySection;
import fr.bilscript.bilzershop.config.inventory.ItemSection;
import fr.bilscript.bilzershop.shop.ShopItem;
import fr.bilscript.bilzershop.shop.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BuyMenu {

	public static void open(Player player) {
		InventorySection inventorySection = BilzerShop.getInstance().getConfiguration().getInventorySection();
		List<ItemSection> items = inventorySection.items();

		Inventory inventory = Bukkit.createInventory(null, inventorySection.size(), inventorySection.title());
		Bukkit.broadcastMessage("CA marche pas ya rien ");
		Bukkit.broadcastMessage("Nombre d'items dans la config : " + items.size());

		for (int i = 0; i < items.size(); i++) {
			ItemSection item = items.get(i);

			ItemStack stack = new ItemStack(item.material(), item.amount());
			ItemMeta meta = stack.getItemMeta();
			if (meta != null) {
				meta.setDisplayName("§e" + item.material().name());
				List<String> lore = new ArrayList<>();
				lore.add("§aPrix d'achat : " + item.buyPrice() + "$");
				lore.add("§7Clique pour acheter !");
				meta.setLore(lore);
				stack.setItemMeta(meta);
			}
			inventory.setItem(i, stack);
		}

		player.openInventory(inventory);
	}
}

