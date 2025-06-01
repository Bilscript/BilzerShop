package fr.bilscript.bilzershop.config.database;

import org.bukkit.configuration.ConfigurationSection;

public record DatabaseSection(String host, int port, String database, String user, String password, int poolSize) {

    public static DatabaseSection from(final ConfigurationSection section)
    {
        final String host = section.getString("host");
        final int port = section.getInt("port");
        final String database = section.getString("database");
        final String user = section.getString("user");
        final String password = section.getString("password");
        final int poolSize = section.getInt("pool-size");

        return new DatabaseSection(host, port, database, user, password, poolSize);
    }

    @Override
    public String toString() {
        return "DatabaseSection{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", poolSize=" + poolSize +
                '}';
    }
}