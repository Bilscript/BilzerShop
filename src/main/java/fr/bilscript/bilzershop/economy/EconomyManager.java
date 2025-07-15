package fr.bilscript.bilzershop.economy;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.economy.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager implements Listener {

	private final Map<UUID, PlayerData> data = new HashMap<>();

	public EconomyManager() {
		for (final Player player : Bukkit.getOnlinePlayers())
			this.load(player);
		Bukkit.getPluginManager().registerEvents(this, BilzerShop.getInstance());
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		this.load(event.getPlayer());
	}

	private void load(Player player) {
		final UUID uuid = player.getUniqueId();
		if (this.data.containsKey(uuid))
			return;
		BilzerShop.getInstance().getDatabase().load(uuid, data -> {
			this.data.put(uuid, data);
		});
	}

	public void add(PlayerData player, int amount) {
		int currentBalance = player.getBalance();
		player.setBalance(currentBalance + amount);
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Vous avez reçu " + amount + "$.");
		}
	}

	public void remove(PlayerData player, int amount) {
		int currentBalance = player.getBalance();
		int res = Math.max(0, currentBalance - amount);
		player.setBalance(res);
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Vous avez perdu " + amount + "$. Nouveau solde : " + res + "$.");
		}
	}

	public void displayBalance(PlayerData player) {
		int balance = player.getBalance();
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Le solde de " + target.getName() + " est de " + balance + "$.");
		}
	}

	public void set(PlayerData player, int amount) {
		player.setBalance(amount);
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Votre solde a été modifié. Il est maintenant à " + amount + "$.");
		}
	}

	public void pay(PlayerData sender, PlayerData target, int amount) {
		if (target.getPlayer() == null) {
			sender.getPlayer().sendMessage("Ce joueur n'existe pas ou n'est pas connecté.");
			return;
		}
		if (sender.getBalance() < amount) {
			sender.getPlayer().sendMessage("Vous n'avez pas les fonds nécessaires.");
			return;
		}

		sender.setBalance(sender.getBalance() - amount);
		target.setBalance(target.getBalance() + amount);

		sender.getPlayer().sendMessage("Vous avez envoyé " + amount + "$ à " + target.getPlayer().getName() + ".");
		target.getPlayer().sendMessage("Vous avez reçu " + amount + "$ de la part de " + sender.getPlayer().getName() + ".");
	}

	public PlayerData getPlayerData(Player player) {
		if (player == null) return null;
		return this.data.get(player.getUniqueId());
	}

	public PlayerData getPlayerData(String player) {
		return this.getPlayerData(Bukkit.getPlayer(player));
	}


	public Collection<PlayerData> getPlayers() {
		return this.data.values();
	}
}
