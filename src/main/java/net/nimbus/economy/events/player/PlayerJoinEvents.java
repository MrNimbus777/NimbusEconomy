package net.nimbus.economy.events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.nimbus.economy.Economy;
import net.nimbus.economy.NEconomy;
import net.nimbus.economy.MySQL.MySQLUtils;
import net.nimbus.economy.YmlFile;

import java.util.ArrayList;

public class PlayerJoinEvents implements Listener {
    @EventHandler
    public void PJE(PlayerJoinEvent e){
        if(NEconomy.database){
            if(!MySQLUtils.userExists(e.getPlayer().getName())){
                MySQLUtils.setValue(new ArrayList<>(Economy.currencies.keySet()).get(0), e.getPlayer().getName(), 0);
            }
        } else YmlFile.exists("Users", e.getPlayer().getName(), true, true);
    }
}
