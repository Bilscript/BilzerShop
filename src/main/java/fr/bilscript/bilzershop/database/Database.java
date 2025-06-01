package fr.bilscript.bilzershop.database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.bilscript.bilzershop.BilzerShop;
import fr.bilscript.bilzershop.config.database.DatabaseSection;
import org.bukkit.Bukkit;

import java.sql.*;

public class Database {
	private static final String TABLE_NAME = "economy";
	private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (uuid VARCHAR(50) PRIMARY KEY NOT NULL, balance BIGINT)";
	private HikariDataSource source;

	public Database(final DatabaseSection section) {
		Bukkit.broadcastMessage(section.toString());
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

	public Connection getConnection() throws SQLException {
		return source.getConnection();
	}

}
