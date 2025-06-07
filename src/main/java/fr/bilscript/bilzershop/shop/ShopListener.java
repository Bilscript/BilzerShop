package fr.bilscript.bilzershop.shop;

import fr.bilscript.bilzershop.shop.menu.BuyMenu;
import fr.bilscript.bilzershop.shop.menu.SellMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();

		Inventory inv = event.getInventory();
		if (event.getView().getTitle().equals("§6§lBilzerShop")) {
			event.setCancelled(true);
			ItemStack clicked = event.getCurrentItem();
			if (clicked == null || !clicked.hasItemMeta()) return;

			String name = clicked.getItemMeta().getDisplayName();
			if (name.equals("§aAcheter des objets")) {
				BuyMenu.open(player);
			} else if (name.equals("§cVendre des objets")) {
				SellMenu.open(player);
			}
		}
	}

}
