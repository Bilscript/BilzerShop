package fr.bilscript.bilzershop.config;

import fr.bilscript.bilzershop.config.database.DatabaseSection;
import fr.bilscript.bilzershop.config.inventory.InventorySection;
import fr.bilscript.bilzershop.config.inventory.ItemSection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private DatabaseSection databaseSection;
	private InventorySection inventorySection;

	public void reload(FileConfiguration config) {
		this.databaseSection = DatabaseSection.from(config.getConfigurationSection("mysql"));
		this.inventorySection = InventorySection.from(config.getConfigurationSection("inventory"));
	}

	public InventorySection getInventorySection() {
		return inventorySection;
	}

	public DatabaseSection getDatabaseSection() {
		return databaseSection;
	}
}
