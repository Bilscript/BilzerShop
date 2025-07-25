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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

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

			final ItemMeta meta = clicked.getItemMeta();
			final PersistentDataContainer container = meta.getPersistentDataContainer();
			if (container.has(MainMenu.BUY_KEY)) {
				BuyMenu.open(player);
			} else if (container.has(MainMenu.SELL_KEY)) {
				SellMenu.open(player);
			}
		}
		if (inv.getHolder() instanceof BuyMenu menu){
			event.setCancelled(true);
			menu.buyItem(manager.getPlayerData(player) ,event.getSlot());
		}
		if (inv.getHolder() instanceof SellMenu menu){
			event.setCancelled(true);
			menu.sellItem(manager.getPlayerData(player) ,event.getSlot());
		}
	}
}
