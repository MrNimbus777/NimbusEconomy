package net.nimbus.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import net.nimbus.economy.MySQL.MySQLUtils;
import net.nimbus.economy.commands.exectors.EcoCommand;
import net.nimbus.economy.commands.tabcompleters.EcoCompleter;
import net.nimbus.economy.events.player.PlayerJoinEvents;
import net.nimbus.economy.events.player.PlayerQuitEvents;

import java.io.File;
import java.sql.Connection;

public class NEconomy extends JavaPlugin {

    public static NEconomy a;
    public static String pref;
    public static String name;


    public String vaultCurrencyId;
    public String vaultCurrencyName;

    public static boolean database;

    public void onEnable() {

        a = this;

        File config = new File(getDataFolder() + "/" + getName() + "/config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        pref = getConfig().getString("Server.prefix").replace("&", "\u00a7");
        name = getConfig().getString("Server.name").replace("&", "\u00a7");

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEvents(), this);

        getCommand("eco").setExecutor(new EcoCommand());
        getCommand("eco").setTabCompleter(new EcoCompleter());

        if (getConfig().getBoolean("DataBase.Enabled")) {
            getLogger().info("Trying to connect to MySQL DataBase");
            Connection trying = MySQLUtils.getConnection();
            if (trying != null) {
                database = true;
                MySQLUtils.con = trying;
                getLogger().info("MySQL DataBase connected!");
            } else {
                database = false;
                getLogger().severe("MySQL DataBase not found! Connecting to files.");
            }
        }
        if (!database) for (String fileName : YmlFile.getDirList("Users")) {
            fileName = fileName.replace(".yml", "");
            YmlFile.exists("Users", fileName, false, true);
            getLogger().info("Loaded " + fileName);
        }

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            try {
                getServer().getServicesManager().register(Economy.class, new EconomyHolder(), this, ServicePriority.Highest);
            } catch (Exception ignored) {
            }
            if (getConfig().getString("Currencies.FromVault.id") != null) {
                try {
                    getLogger().info("Trying to start Vault economy...");
                    vaultCurrencyId = getConfig().getString("Currencies.Vault.id");
                    vaultCurrencyName = getConfig().getString("Currencies.Vault.name").replace("&", "\u00a7");
                    net.nimbus.economy.Economy.currencies.put(vaultCurrencyId, vaultCurrencyName);
                    if (database) MySQLUtils.createIfNotExists(vaultCurrencyId);
                    getLogger().info("Success!");
                } catch (Exception e) {
                    getLogger().severe("Failed");
                    e.printStackTrace();
                }
            } else getLogger().severe("Vault is not loaded. Can not init currency from Vault");
        }

        if (getConfig().getConfigurationSection("Currencies.custom") != null)
            for (String id : getConfig().getConfigurationSection("Currencies.custom").getKeys(false)) {
                net.nimbus.economy.Economy.currencies.put(id, getConfig().getString("Currencies.custom." + id));
                if (database) MySQLUtils.createIfNotExists(id);
                getLogger().info("Loaded currency " + id + " with name " + net.nimbus.economy.Economy.currencies.get(id).replace("&", "\u00a7"));
            }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            try {
                getLogger().info("Trying to load PAPI expansion...");
                new PAPIneco().register();
                getLogger().info("Success!");
            } catch (Exception e) {
                getLogger().severe("Failed!");
                e.printStackTrace();
            }
        }
    }

    public void onDisable() {
        if (!YmlFile.hash.isEmpty()) for (YmlFile file : YmlFile.hash.values()) {
            file.save();
        }
    }
}