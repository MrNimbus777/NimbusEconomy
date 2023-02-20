package net.nimbus.economy;
import java.util.List;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class EconomyHolder implements net.milkbowl.vault.economy.Economy {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return Bukkit.getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String player) {
        return Economy.userExists(player);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public boolean hasAccount(String player, String type) {
        return hasAccount(player);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String type) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String player) {
        return Economy.getBalance(player, NEconomy.a.vaultCurrencyId);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return Economy.getBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId);
    }

    @Override
    public double getBalance(String player, String type) {
        return Economy.getBalance(player, type);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String type) {
        return Economy.getBalance(offlinePlayer.getName(), type);
    }

    @Override
    public boolean has(String player, double amount) {
        return Economy.getBalance(player, NEconomy.a.vaultCurrencyId) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        return has(offlinePlayer.getName(), amount);
    }

    @Override
    public boolean has(String player, String type, double amount) {
        return Economy.getBalance(player, type) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String type, double amount) {
        return has(offlinePlayer.getName(), type, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String player, double amount) {
        Economy.addBalance(player, NEconomy.a.vaultCurrencyId, -amount);
        return new EconomyResponse(getBalance(player, NEconomy.a.vaultCurrencyId), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        Economy.addBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId, -amount);
        return new EconomyResponse(getBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse withdrawPlayer(String player, String type, double amount) {
        Economy.addBalance(player, NEconomy.a.vaultCurrencyId, -amount);
        return new EconomyResponse(getBalance(player, type), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String type, double amount) {
        Economy.addBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId, -amount);
        return new EconomyResponse(getBalance(offlinePlayer.getName(), type), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse depositPlayer(String player, double amount) {
        Economy.addBalance(player, NEconomy.a.vaultCurrencyId, amount);
        return new EconomyResponse(getBalance(player, NEconomy.a.vaultCurrencyId), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        Economy.addBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId, amount);
        return new EconomyResponse(getBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse depositPlayer(String player, String type, double amount) {
        Economy.addBalance(player, NEconomy.a.vaultCurrencyId, amount);
        return new EconomyResponse(getBalance(player, type), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String type, double amount) {
        Economy.addBalance(offlinePlayer.getName(), NEconomy.a.vaultCurrencyId, amount);
        return new EconomyResponse(getBalance(offlinePlayer.getName(), type), amount, EconomyResponse.ResponseType.SUCCESS, "Not enough money");
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String player) {
        return YmlFile.exists("Users", player, true, true);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return YmlFile.exists("Users", offlinePlayer.getName(), true, true);
    }

    @Override
    public boolean createPlayerAccount(String player, String type) {
        return YmlFile.exists("Users", player, true, true);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String type) {
        return YmlFile.exists("Users", offlinePlayer.getName(), true, true);
    }
}