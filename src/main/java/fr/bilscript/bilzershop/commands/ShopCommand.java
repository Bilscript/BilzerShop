package fr.bilscript.bilzershop.commands;

import fr.bilscript.bilzershop.shop.menu.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (!(sender instanceof Player player)) return false;
		MainMenu.open(player);
		return true;
	}
}

