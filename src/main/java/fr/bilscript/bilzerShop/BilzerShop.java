package fr.bilscript.bilzerShop;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class BilzerShop extends JavaPlugin {

	private static BilzerShop instance;

	@Override
	public void onEnable() {
		instance = this;

	}

	@Override
	public void onDisable() {
		System.out.println("Le Plugin est desactivé 😭!!");
	}

	public static BilzerShop getInstance() {
		return instance;
	}
}
