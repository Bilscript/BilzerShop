package fr.bilscript.bilzershop.shop;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.economy.EconomyManager;
import fr.bilscript.bilzershop.shop.menu.BuyMenu;
import fr.bilscript.bilzershop.shop.menu.MainMenu;
import fr.bilscript.bilzershop.shop.menu.SellMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

	EconomyManager manager = BilzerShop.getInstance().getEconomyManager();

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getView().getTopInventory();

		if (inv.getHolder() instanceof MainMenu) {
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
		if (inv.getHolder() instanceof BuyMenu){
			event.setCancelled(true);
			BuyMenu.buyItem(manager.getPlayerData(player) ,event.getSlot());
		}
		if (inv.getHolder() instanceof SellMenu){
			event.setCancelled(true);
			SellMenu.sellItem(manager.getPlayerData(player) ,event.getSlot());
		}
	}
}
