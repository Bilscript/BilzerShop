package fr.bilscript.bilzershop.economy.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {
	private final UUID uuid;
	private int balance;

	public PlayerData(UUID uuid, int account) {
		this.uuid = uuid;
		this.balance = account;
	}

	public PlayerData(UUID uuid) {
		this.uuid = uuid;
		this.balance = 0;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}

	public UUID getUuid() {
		return uuid;
	}
}
