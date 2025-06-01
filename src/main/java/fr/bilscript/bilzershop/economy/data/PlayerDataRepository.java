package fr.bilscript.bilzershop.economy.data;

import fr.bilscript.bilzershop.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class PlayerDataRepository {

	private final Database database;

	public PlayerDataRepository(Database database) {
		this.database = database;
	}

	public PlayerData load(UUID uuid) {
		try (Connection conn = database.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT balance FROM economy WHERE uuid = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int balance = rs.getInt("balance");
				return new PlayerData(uuid, balance);
			} else {
				ps = conn.prepareStatement("INSERT INTO economy(uuid, balance) VALUES (?, 0)");
				ps.setString(1, uuid.toString());
				ps.executeUpdate();
				return new PlayerData(uuid, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PlayerData(uuid, 0);
		}
	}

	public void save(PlayerData data) {
		try (Connection conn = database.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("UPDATE economy SET balance = ? WHERE uuid = ?");
			ps.setInt(1, data.getBalance());
			ps.setString(2, data.getUuid().toString());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
