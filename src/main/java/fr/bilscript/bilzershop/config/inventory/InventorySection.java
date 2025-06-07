package fr.bilscript.bilzershop.config.inventory;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public record InventorySection(String title, int size, List<ItemSection> items) {

	public static InventorySection from(ConfigurationSection section){
		final String title = section.getString("title");
		final int size = section.getInt("size");
		final List<ItemSection> items = new ArrayList<>();
		final ConfigurationSection itemsSection  = section.getConfigurationSection("items");
		for (final String key : itemsSection.getKeys(false)) {
			final ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
			items.add(ItemSection.from(itemSection));
		}
		return new InventorySection(title, size, items);
	}

}
