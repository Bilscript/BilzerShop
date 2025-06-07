package fr.bilscript.bilzershop.shop;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ShopManager {

	private final List<ShopItem> buyItems = new ArrayList<>();
	private final List<ShopItem> sellItems = new ArrayList<>();

	public void addBuyItem(ShopItem item) {
		buyItems.add(item);
	}

	public void addSellItem(ShopItem item) {
		sellItems.add(item);
	}

	public void removeBuyItem(ShopItem item) {
		buyItems.remove(item);
	}

	public void removeSellItem(ShopItem item) {
		sellItems.remove(item);
	}

	public List<ShopItem> getBuyItems() {
		return Collections.unmodifiableList(buyItems);
	}

	public List<ShopItem> getSellItems() {
		return Collections.unmodifiableList(sellItems);
	}

}