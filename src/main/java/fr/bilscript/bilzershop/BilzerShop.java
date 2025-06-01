package fr.bilscript.bilzershop;

import fr.bilscript.bilzershop.commands.EconomyCommand;
import fr.bilscript.bilzershop.commands.PayCommand;
import fr.bilscript.bilzershop.config.Config;
import fr.bilscript.bilzershop.database.Database;
import fr.bilscript.bilzershop.economy.EconomyManager;
import fr.bilscript.bilzershop.economy.data.PlayerDataRepository;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class BilzerShop extends JavaPlugin {

	private static BilzerShop instance;
	private EconomyManager economyManager;
	private Config config;
	private Database database;

	@Override
	public void onEnable() {
		instance = this;
		reload();
		this.database = new Database(config.getDatabaseSection());

		PlayerDataRepository repository = new PlayerDataRepository(database);
		this.economyManager = new EconomyManager(repository);

		getCommand("eco").setExecutor(new EconomyCommand());
		getCommand("pay").setExecutor(new PayCommand());
	}

	@Override
	public void onDisable() {
		System.out.println("Le Plugin est desactivé 😭!!");
	}

	public EconomyManager getEconomyManager() {
		return this.economyManager;
	}

	public Config getConfiguration() {
		return this.config;
	}

	public Database getDatabase() {
		return database;
	}

	public void reload() {
		saveDefaultConfig();
		reloadConfig();
		if (this.config == null)
			this.config = new Config();
		this.config.reload(this.getConfig());
	}

	public static BilzerShop getInstance() {
		return instance;
	}
}
