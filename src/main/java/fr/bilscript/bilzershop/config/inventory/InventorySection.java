package fr.bilscript.bilzershop.config.inventory;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public record InventorySection(String title, int size, List<ItemSection> items) {

	public static InventorySection from(ConfigurationSection section){
		//Bukkit.broadcastMessage("§csection =" + section);
		final String title = section.getString("title");
		final int size = section.getInt("size");
		final List<ItemSection> items = new ArrayList<>();
		final ConfigurationSection itemsSection  = section.getConfigurationSection("items");
		//Bukkit.broadcastMessage("ITEMSSECTION =" + itemsSection);
		for (final String key : itemsSection.getKeys(false)) {
			//Bukkit.broadcastMessage("§ckey = " + key);
			final ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
			items.add(ItemSection.from(itemSection));
			//Bukkit.broadcastMessage("§citem = " + itemSection);
		}
		final ConfigurationSection test= itemsSection  .getConfigurationSection("1");
		//Bukkit.broadcastMessage("§etest=§f" + test);
		//Bukkit.broadcastMessage("size = " + itemsSection.getKeys(false).size());
		return new InventorySection(title, size, items);
	}

}
