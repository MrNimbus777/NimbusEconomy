package net.nimbus.economy;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PAPIneco extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "neco";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrNimbus777";
    }

    @Override
    public @NotNull String getVersion() {
        return NEconomy.a.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        for(String type : Economy.currencies.keySet()){
            if(params.equalsIgnoreCase("player_"+type)){
                return Economy.getBalance(p.getName(), type).toString();
            }
        }
        return null;
    }
}