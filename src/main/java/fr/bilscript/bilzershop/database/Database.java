package fr.bilscript.bilzershop.database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.config.database.DatabaseSection;
import fr.bilscript.bilzershop.economy.data.PlayerData;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.function.Consumer;

public class Database {
	private static final String TABLE_NAME = "economy";
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (uuid VARCHAR(50) PRIMARY KEY NOT NULL, balance BIGINT)";
	private static final String LOAD_ECONOMY = "SELECT balance FROM "+ TABLE_NAME + " WHERE uuid = ?";
	private static final String SAVE_ECONOMY = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?) ON DUPLICATE KEY UPDATE balance=VALUES(balance)";

	private HikariDataSource source;

	public Database(final DatabaseSection section) {
		final HikariConfig config = new HikariConfig();
		config.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", section.host(), section.port(), section.database()));
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setUsername(section.user());
		config.setPassword(section.password());
		config.setMaximumPoolSize(section.poolSize());
		config.setConnectionTimeout(30_000);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		try {
			this.source = new HikariDataSource(config);
			this.createTable();
		} catch (final Exception e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(BilzerShop.getInstance());
		}
	}

	private void createTable() {
		try (final Connection connection = this.source.getConnection()) {
			final PreparedStatement ps = connection.prepareStatement(CREATE_TABLE);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void load(final UUID uuid, final Consumer<PlayerData> then) {
		Bukkit.getScheduler().runTaskAsynchronously(BilzerShop.getInstance(), () -> {
			try (final Connection conn = this.source.getConnection()) {
				final PreparedStatement ps = conn.prepareStatement(LOAD_ECONOMY);
				ps.setString(1, uuid.toString());
				final ResultSet rs = ps.executeQuery();
				final PlayerData data = new PlayerData(uuid);

				if (rs.next())
					data.setBalance(rs.getInt("balance"));
				then.accept(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void save() {
		Bukkit.getScheduler().runTaskAsynchronously(BilzerShop.getInstance(), () -> {
			for(final PlayerData data : BilzerShop.getInstance().getEconomyManager().getPlayers()) {
				try (final Connection conn = this.source.getConnection()) {
					final PreparedStatement ps = conn.prepareStatement(SAVE_ECONOMY);
					ps.setInt(1, data.getBalance());
					ps.setString(2, data.getUuid().toString());
					ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



}
