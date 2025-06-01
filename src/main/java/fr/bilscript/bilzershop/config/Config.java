package fr.bilscript.bilzershop.config;

import fr.bilscript.bilzershop.config.database.DatabaseSection;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private DatabaseSection databaseSection;

	public void reload(FileConfiguration config) {
		this.databaseSection = DatabaseSection.from(config.getConfigurationSection("mysql"));
	}

	public DatabaseSection getDatabaseSection() {
		return databaseSection;
	}
}
