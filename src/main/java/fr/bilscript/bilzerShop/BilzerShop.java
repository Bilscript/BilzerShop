package fr.bilscript.bilzerShop;

import fr.bilscript.bilzerShop.commands.EconomyCommand;
import fr.bilscript.bilzerShop.economy.EconomyManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class BilzerShop extends JavaPlugin {

	private static BilzerShop instance;
	private EconomyManager economyManager;

	@Override
	public void onEnable() {
		instance = this;
		this.economyManager = new EconomyManager();
		getCommand("eco").setExecutor(new EconomyCommand());
	}

	@Override
	public void onDisable() {
		System.out.println("Le Plugin est desactivé 😭!!");
	}

	public EconomyManager getEconomyManager() {
		return this.economyManager;
	}

	public static BilzerShop getInstance() {
		return instance;
	}
}
