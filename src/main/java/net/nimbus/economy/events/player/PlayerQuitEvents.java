package net.nimbus.economy.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import net.nimbus.economy.NEconomy;
import net.nimbus.economy.YmlFile;

public class PlayerQuitEvents implements Listener {
    @EventHandler
    public void PQE(PlayerQuitEvent e){
        if(!NEconomy.database) YmlFile.get("Users", e.getPlayer().getName()).save();
    }
}
