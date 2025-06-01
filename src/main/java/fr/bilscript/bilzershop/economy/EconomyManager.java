package fr.bilscript.bilzershop.economy;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.economy.data.PlayerData;
import fr.bilscript.bilzershop.economy.data.PlayerDataRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager implements Listener {

	private final Map<UUID, PlayerData> data = new HashMap<>();
	private final PlayerDataRepository repository;

	public PlayerDataRepository getRepository() {
		return repository;
	}

	public EconomyManager(PlayerDataRepository repository) {
		this.repository = repository;
		Bukkit.getPluginManager().registerEvents(this, BilzerShop.getInstance());
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.load(player);
		}
		Bukkit.getScheduler().runTaskTimerAsynchronously(
				BilzerShop.getInstance(),
				this::saveAll,
				20 * 60L,
				20 * 60L
		);
	}

	public void saveAll() {
		for (PlayerData playerData : data.values()) {
			repository.save(playerData);
		}
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		this.load(event.getPlayer());
	}

	private void load(Player player) {
		UUID uuid = player.getUniqueId();

		if (this.data.containsKey(uuid))
			return;

		PlayerData loaded = this.repository.load(uuid);
		this.data.put(uuid, loaded);
	}

	public void add(PlayerData player, int amount) {
		int currentBalance = player.getBalance();
		player.setBalance(currentBalance + amount);
		repository.save(player);
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Vous avez reçu " + amount + "$.");
		}
	}

	public void remove(PlayerData player, int amount) {
		int currentBalance = player.getBalance();
		int res = Math.max(0, currentBalance - amount);
		player.setBalance(res);
		repository.save(player);

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
		repository.save(player);
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Votre solde a été modifié. Il est maintenant à " + amount + "$.");
		}
	}

	public PlayerData getPlayerData(Player player) {
		if (player == null) return null;
		return this.data.get(player.getUniqueId());
	}

	public PlayerData getPlayerData(String player) {
		return this.getPlayerData(Bukkit.getPlayer(player));
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
		repository.save(sender);
		target.setBalance(target.getBalance() + amount);
		repository.save(target);

		sender.getPlayer().sendMessage("Vous avez envoyé " + amount + "$ à " + target.getPlayer().getName() + ".");
		target.getPlayer().sendMessage("Vous avez reçu " + amount + "$ de la part de " + sender.getPlayer().getName() + ".");
	}
}
