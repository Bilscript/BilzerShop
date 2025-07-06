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
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuyMenu implements InventoryHolder {

	private final Inventory inventory;
	private static InventorySection inventorySection;

	public BuyMenu() {
		this.inventorySection = BilzerShop.getInstance().getConfiguration().getInventorySection();
		List<ItemSection> items = inventorySection.items();

		this.inventory = Bukkit.createInventory(this, inventorySection.size(), inventorySection.title());

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
	}

	public static void buyItem(PlayerData player, int key){
		List<ItemSection> items = inventorySection.items();
		if (key >= items.size())
			return;
		ItemSection itemSection = items.get(key);
		if (itemSection == null)
			return;
		int amount = itemSection.amount();
		if (player.getBalance() < itemSection.buyPrice()){
			player.getPlayer().sendMessage("§cTu est pauvre, tu n'a pas assez d'argent.");
			return;
		}
		if (itemSection == null)
			return;
		HashMap<Integer, ItemStack> itemLeft;
		itemLeft = player.getPlayer().getInventory().addItem(new ItemStack(itemSection.material(), amount));
		if (itemLeft.isEmpty()){
			player.getPlayer().sendMessage("§Vous avez achete " + amount + " " + itemSection.material().toString().toLowerCase() + "!");
			BilzerShop.getInstance().getEconomyManager().remove(player , (int) itemSection.buyPrice());
		}
		else
			player.getPlayer().sendMessage("§cTon inventaire est plein!");
	}

	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}
	public static void open(Player player){
		player.openInventory(new BuyMenu().getInventory());
	}
}

