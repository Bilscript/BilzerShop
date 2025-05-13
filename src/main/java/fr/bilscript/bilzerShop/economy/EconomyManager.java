package fr.bilscript.bilzerShop.economy;

import fr.bilscript.bilzerShop.BilzerShop;
import fr.bilscript.bilzerShop.economy.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager implements Listener {

	private final Map<UUID, PlayerData> data = new HashMap<>();


	public EconomyManager(){
		Bukkit.getPluginManager().registerEvents(this, BilzerShop.getInstance());
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.load(player);
		}
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent event){
		this.load(event.getPlayer());
	}

	private void load(Player player){
		if (this.data.containsKey(player.getUniqueId()))
			return;
		this.data.put(player.getUniqueId(),new PlayerData(player.getUniqueId()));
	}

	public void editBalance(PlayerData player, int amount, )

	public void add(PlayerData player, int amount){
		int currentBalance = player.getBalance();
		player.setBalance(currentBalance + amount);

		Player target = player.getPlayer();
		if (target != null){
			target.sendMessage("Vous avez recu " + amount + "$.");
		}
	}

	public void remove(PlayerData player, int amount){
		int currentBalance = player.getBalance();
		int res = currentBalance - amount;
		if (res < 0){
			res = 0;
			player.setBalance(res);
		}
		else
			player.setBalance(res);
		Player target = player.getPlayer();
		if (target != null)
			target.sendMessage("Vous avez perdu " + amount + "$. Nouveau solde : " + res + "$.");
	}

	public void displayBalance(PlayerData player){
		int balance = player.getBalance();

		Player target = player.getPlayer();
		if (target != null){
			target.sendMessage("Le solde de " + target.getName() + " est de " + balance + "$.");
		}
		else
			target.sendMessage("Ce joueur n'existe pas ou n'est pas connecté.");
	}

	public void set(PlayerData player, int amount){
		player.setBalance(amount);
		Player target = player.getPlayer();
		if (target != null) {
			target.sendMessage("Votre solde a été modifié. il est maintenant à " + amount + "$.");
		}
	}

	public PlayerData getPlayerData(Player player){
		if (player == null)
			return null;
		return this.data.get(player.getUniqueId());
	}
	public PlayerData getPlayerData(String player){
		return this.getPlayerData(Bukkit.getPlayer(player));
	}
}
