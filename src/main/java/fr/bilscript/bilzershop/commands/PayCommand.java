package fr.bilscript.bilzershop.commands;

import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.economy.EconomyManager;
import fr.bilscript.bilzershop.economy.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PayCommand implements CommandExecutor, TabCompleter {

	private static final String INVALID_MESSAGE = "Somme invalide ! Le montant doit etre un nombre entier positif.";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
		if (!(sender instanceof Player player))
			return true;

		EconomyManager manager = BilzerShop.getInstance().getEconomyManager();
		PlayerData playerData = manager.getPlayerData(player);
		if (args.length != 2) {
			sender.sendMessage("Utilisation : /pay <joueur> <montant>");
			return false;
		}
		PlayerData target = manager.getPlayerData(Bukkit.getPlayer(args[0]));
		if (target == null) {
			sender.sendMessage("Joueur introuvable");
			return false;
		}

		int amountAdd;
		try {
			amountAdd = Integer.parseInt(args[1]);
			if (amountAdd < 0) {
				player.sendMessage(INVALID_MESSAGE);
				return true;
			}
		} catch (NumberFormatException e) {
			player.sendMessage(INVALID_MESSAGE);
			return true;
		}
		manager.pay(playerData, target, amountAdd);
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
		if (args.length == 1) {
			return Bukkit.getOnlinePlayers().stream()
					.map(Player::getName)
					.filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
					.toList();
		}
		if (args.length == 2) {
			return List.of("<Montant>");
		}
		return List.of();
	}

}
