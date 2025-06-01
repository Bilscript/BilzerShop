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

public class EconomyCommand implements CommandExecutor, TabCompleter {

	private static final List<String> PARAMETERS = List.of("add", "remove", "set", "get");

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
		if (!(sender instanceof Player player))
			return true;

		EconomyManager manager = BilzerShop.getInstance().getEconomyManager();
		PlayerData playerData = manager.getPlayerData((Player) sender);

		if (args.length == 0) {
			player.sendMessage("Votre solde actuel est de " + playerData.getBalance() + "$.");
			return true;
		} else if (args.length == 1) {
			Player playerDisplay = Bukkit.getPlayer(args[0]);
			if (playerDisplay == null) {
				player.sendMessage("Joueur introuvable.");
				return true;
			}
			manager.displayBalance(manager.getPlayerData(playerDisplay));
			return true;
		}
		if (!player.hasPermission("bilzershop.admin")) {
			player.sendMessage("Tu n'as pas la permission d'utiliser cette commande.");
			return true;
		}
		String invalidArg = "Somme invalide ! Le montant doit etre un nombre entier positif.";
		switch (args[0].toLowerCase()) {
			case "add":
				if (args.length != 3) {
					player.sendMessage("Utilisation : /eco add <joueur> <montant>");
					return true;
				}
				PlayerData dataAdd = manager.getPlayerData(Bukkit.getPlayer(args[1]));
				if (dataAdd == null) {
					player.sendMessage("Joueur introuvable.");
					return true;
				}
				int amountAdd = 0;
				try {
					amountAdd = Integer.parseInt(args[2]);
					if (amountAdd < 0) {
						player.sendMessage(invalidArg);
						return true;
					}
				} catch (NumberFormatException e) {
					player.sendMessage(invalidArg);
					return true;
				}
				manager.add(dataAdd, amountAdd);
				player.sendMessage("Vous avez ajouté " + amountAdd + "$ à " + dataAdd.getPlayer().getName() + ".");
				break;

			case "remove":
				if (args.length != 3) {
					player.sendMessage("Utilisation : /eco remove <joueur> <montant>");
					return true;
				}
				PlayerData dataRemove = manager.getPlayerData(Bukkit.getPlayer(args[1]));
				if (dataRemove == null) {
					player.sendMessage("Joueur introuvable.");
					return true;
				}
				int amountRemove = 0;
				try {
					amountRemove = Integer.parseInt(args[2]);
					if (amountRemove < 0) {
						player.sendMessage(invalidArg);
						return true;
					}
				} catch (NumberFormatException e) {
					player.sendMessage(invalidArg);
					return true;
				}
				manager.remove(dataRemove, amountRemove);
				player.sendMessage("Vous avez retiré " + amountRemove + "$ à " + dataRemove.getPlayer().getName() + ".");
				break;
			case "set":
				if (args.length != 3) {
					player.sendMessage("Utilisation : /eco set <joueur> <montant>");
					return true;
				}
				PlayerData dataSet = manager.getPlayerData(Bukkit.getPlayer(args[1]));
				if (dataSet == null) {
					player.sendMessage("Joueur introuvable.");
					return true;
				}
				int amountSet = 0;
				try {
					amountSet = Integer.parseInt(args[2]);
					if (amountSet < 0) {
						player.sendMessage(invalidArg);
						return true;
					}
				} catch (NumberFormatException e) {
					player.sendMessage(invalidArg);
					return true;
				}
				manager.set(dataSet, amountSet);
				player.sendMessage(dataSet.getPlayer().getName() + " à maintenant " + amountSet + "$.");
				break;
			case "get":
				if (args.length != 2) {
					player.sendMessage("Utilisation : /eco get <joueur>");
					return true;
				}
				PlayerData dataGet = manager.getPlayerData(Bukkit.getPlayer(args[1]));
				if (dataGet == null) {
					player.sendMessage("Joueur introuvable.");
					return true;
				}
				player.sendMessage(dataGet.getPlayer().getName() + " à " + dataGet.getBalance() + "$.");
				break;
		}
		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
		if (args.length == 1)
			return PARAMETERS;

		if (args.length == 2 && PARAMETERS.contains(args[0]))
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.startsWith(args[1])).toList();
		if (args.length == 3 && List.of("add", "remove", "set").contains(args[0]))
			return List.of("<Montant>");
		return List.of();
	}
}
