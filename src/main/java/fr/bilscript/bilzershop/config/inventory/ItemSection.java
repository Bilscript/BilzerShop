package fr.bilscript.bilzershop.config.inventory;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public record ItemSection(Material material, int amount, double buyPrice, double sellPrice) {
	public static ItemSection from(ConfigurationSection section){
		final Material material = Material.getMaterial(section.getString("material"));
		final int amount = section.getInt("amount");
		final double buyPrice = section.getDouble("buy-price");
		final double sellPrice = section.getDouble("sell-price");

		return new ItemSection(material, amount, buyPrice, sellPrice);
	}
}
