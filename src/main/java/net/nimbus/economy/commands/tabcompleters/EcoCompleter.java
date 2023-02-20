package net.nimbus.economy.commands.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import net.nimbus.economy.Economy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EcoCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> listAll = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        if(args.length == 1){
            listAll.add("set");
            listAll.add("give");
            listAll.add("pay");
            listAll.add("info");
            for(String elem : listAll){
                if(elem.toLowerCase(Locale.ROOT).startsWith(args[0].toLowerCase(Locale.ROOT))) list.add(elem);
            }
            return list;
        } else if(args.length == 2){
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase(Locale.ROOT).startsWith(args[1].toLowerCase(Locale.ROOT))) list.add(p.getName());
            }
            return list;
        } else if(args.length == 3){
            if(!args[1].toLowerCase(Locale.ROOT).equals("info")){
                if(args[2].length() == 0) {
                    list.add("10");
                    list.add("100");
                    list.add("500");
                    list.add("1000");
                } else {
                    list.add(args[2]);
                }
                return list;
            }
        } else if(args.length == 4){
            if(!args[1].toLowerCase(Locale.ROOT).equals("info")){
                listAll = new ArrayList<>(Economy.currencies.keySet());
                for(String elem : listAll){
                    if (elem.toLowerCase(Locale.ROOT).startsWith(args[3].toLowerCase(Locale.ROOT))) list.add(elem);
                }
                return list;
            }
        }
        return list;
    }
}
