package fr.bilscript.bilzershop.task;

import fr.bilscript.bilzershop.BilzerShop;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {

	private static final long PERIOD = 20L * 300;

	public SaveTask(final BilzerShop main) {
		Bukkit.getScheduler().runTaskTimerAsynchronously(main, this::run, PERIOD, PERIOD);
	}

	@Override
	public void run() {
		BilzerShop.getInstance().getDatabase().save();
	}
}
