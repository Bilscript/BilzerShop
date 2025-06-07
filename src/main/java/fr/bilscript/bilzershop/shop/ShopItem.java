package fr.bilscript.bilzershop.shop;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ShopItem {
	private final ItemStack item;
	private final int price;
	private final UUID sellerUuid;

	public ShopItem(ItemStack item, Integer price, UUID sellerUuid) {
		this.item = item;
		this.price = price;
		this.sellerUuid = sellerUuid;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getPrice() {
		return price;
	}

	public UUID getSellerUuid() {
		return sellerUuid;
	}

	public String getSellerName() {
		if (sellerUuid == null) return "Boutique";
		OfflinePlayer seller = Bukkit.getOfflinePlayer(String.valueOf(sellerUuid));
		return seller.getName() != null ? seller.getName() : "Inconnu";
	}
}

