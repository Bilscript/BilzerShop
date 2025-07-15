package fr.bilscript.bilzershop.shop.menu;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.config.inventory.InventorySection;
import fr.bilscript.bilzershop.config.inventory.ItemSection;
import fr.bilscript.bilzershop.economy.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
	private final InventorySection inventorySection;

	public SellMenu() {
		this.inventorySection = BilzerShop.getInstance().getConfiguration().getInventorySection();
		List<ItemSection> items = inventorySection.items();
		this.inventory = Bukkit.createInventory(this, inventorySection.size(), inventorySection.title());

		for (int i = 0; i < items.size(); i++) {
			ItemSection item = items.get(i);

			ItemStack stack = new ItemStack(item.material(), item.amount());
			stack.editMeta(meta -> {
				meta.setDisplayName("§e" + item.material().name());
				List<String> lore = new ArrayList<>();
				lore.add("§aPrix de vente : " + item.sellPrice() + "$");
				lore.add("§7Clique pour vendre !");
				meta.setLore(lore);
			});
			inventory.setItem(i, stack);
		}
	}

	public void sellItem(PlayerData player, int key) {

		List<ItemSection> items = inventorySection.items();
		if (key >= items.size())
			return;
		ItemSection itemSection = items.get(key);
		Material material = itemSection.material();
		PlayerInventory inv = player.getPlayer().getInventory();

		int totalSold = 0;
		if (itemSection == null)
			return;
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack != null && stack.getType() == material) {
				totalSold += stack.getAmount();
				inv.setItem(i, null);
			}
		}
		if (totalSold == 0){
			player.getPlayer().sendMessage("Tu dois avoir cette item dans ton inventaire pour le vendre!");
			return;
		}
		int amountItem = totalSold;
		totalSold *= itemSection.sellPrice();
		player.setBalance(player.getBalance() + totalSold);
		player.getPlayer().sendMessage("§cVous avez vendue " + amountItem + " " + itemSection.material().toString().toLowerCase() + " pour " + totalSold + "$ !");
		player.getPlayer().sendMessage("Nouveau solde : " + player.getBalance() + "$");
	}
	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}
	public static void open(Player player){
		player.openInventory(new SellMenu().getInventory());
	}
}
