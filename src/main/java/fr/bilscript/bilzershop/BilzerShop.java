package fr.bilscript.bilzershop;

import fr.bilscript.bilzershop.commands.EconomyCommand;
import fr.bilscript.bilzershop.commands.PayCommand;
import fr.bilscript.bilzershop.commands.ShopCommand;
import fr.bilscript.bilzershop.config.Config;
import fr.bilscript.bilzershop.config.inventory.InventorySection;
import fr.bilscript.bilzershop.database.Database;
import fr.bilscript.bilzershop.economy.EconomyManager;
import fr.bilscript.bilzershop.economy.data.PlayerDataRepository;
import fr.bilscript.bilzershop.shop.ShopItem;
import fr.bilscript.bilzershop.shop.ShopListener;
import fr.bilscript.bilzershop.shop.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BilzerShop extends JavaPlugin{

	private static BilzerShop instance;
	private EconomyManager economyManager;
	private Config config;
	private Database database;
	private ShopManager shopManager;

	@Override
	public void onEnable() {
		instance = this;
		reload();
		this.shopManager = new ShopManager();
		this.database = new Database(config.getDatabaseSection());
		PlayerDataRepository repository = new PlayerDataRepository(database);
		this.economyManager = new EconomyManager(repository);
		Bukkit.getPluginManager().registerEvents(new ShopListener(), this);

		getCommand("eco").setExecutor(new EconomyCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("shop").setExecutor(new ShopCommand());

	}

	@Override
	public void onDisable() {
		System.out.println("Le Plugin est desactivé 😭!!");
	}

	public ShopManager getShopManager() {
		return shopManager;
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
