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
	private static final String INVALID_MESSAGE = "Somme invalide ! Le montant doit etre un nombre entier positif.";

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String msg, @NotNull String[] args) {
		if (!(sender instanceof Player player))
			return true;

		EconomyManager manager = BilzerShop.getInstance().getEconomyManager();
		PlayerData playerData = manager.getPlayerData(player);

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
		switch (args[0].toLowerCase()) {
			case "add":
				this.modifyValue(args, player, ActionType.ADD);
				break;
			case "remove":
				this.modifyValue(args, player, ActionType.REMOVE);
				break;
			case "set":
				this.modifyValue(args, player, ActionType.SET);
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


	private void modifyValue(final String[] args, final Player player, final ActionType type) {
		final EconomyManager manager = BilzerShop.getInstance().getEconomyManager();

		if (args.length != 3) {
			player.sendMessage("Utilisation : /eco " + type.getArg() + " <joueur> <montant>");
			return;
		}
		PlayerData data = manager.getPlayerData(Bukkit.getPlayer(args[1]));
		if (data == null) {
			player.sendMessage("Joueur introuvable.");
			return;
		}
		int amount;
		try {
			amount = Integer.parseInt(args[2]);
			if (amount < 0) {
				player.sendMessage(INVALID_MESSAGE);
				return;
			}
		} catch (NumberFormatException e) {
			player.sendMessage(INVALID_MESSAGE);
			return;
		}
		if (type == ActionType.ADD) {
			manager.add(data, amount);
			player.sendMessage("Vous avez ajouté " + amount + "$ à " + args[1] + ".");
		} else if(type == ActionType.REMOVE) {
			manager.remove(data, amount);
			player.sendMessage("Vous avez retiré " + amount + "$ à " + args[1] + ".");
		} else {
			manager.set(data, amount);
			player.sendMessage(args[1] + " a maintenant " + amount + "$.");
		}
	}

	private enum ActionType {
		ADD("add"), REMOVE("remove"), SET("set");

		private final String arg;

		ActionType(final String arg) {
			this.arg = arg;
		}

		public String getArg() {
			return this.arg;
		}
	}
}
