package net.nimbus.economy.commands.exectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.nimbus.economy.Economy;
import net.nimbus.economy.NEconomy;

import java.util.Locale;

public class EcoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length > 0){
            if(args[0].toLowerCase(Locale.ROOT).equals("set")){
                if(sender.hasPermission("neco.setbalance")){
                    if(args.length >= 4){
                        if (Economy.userExists(args[1])) {
                            try {
                                double amount = Double.parseDouble(args[2]);
                                if(Economy.currencies.containsKey(args[3])){
                                    Economy.setBalance(args[1], args[3], amount);
                                    sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Success.eco-set").replace("{player}", args[1]).replace("{amount}", ""+amount).replace("{currency}", Economy.currencies.get(args[3])).replace("&", "\u00a7"));
                                } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-currency").replace("&", "\u00a7"));
                            } catch (Exception e) {
                                sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-number").replace("&", "\u00a7"));
                            }
                        } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-user").replace("&", "\u00a7"));
                    } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Usage.eco-set").replace("&", "\u00a7"));
                } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.no-permissions").replace("&", "\u00a7"));
            } else if(args[0].toLowerCase(Locale.ROOT).equals("give")){
                if(sender.hasPermission("neco.givebalance")){
                    if(args.length >= 4){
                        if (Economy.userExists(args[1])) {
                            try {
                                double amount = Double.parseDouble(args[2]);
                                if(Economy.currencies.containsKey(args[3])){
                                    Economy.addBalance(args[1], args[3], amount);
                                    sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Success.eco-add").replace("{player}", args[1]).replace("{amount}", ""+amount).replace("{currency}", Economy.currencies.get(args[3])).replace("&", "\u00a7"));
                                } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-currency").replace("&", "\u00a7"));
                            } catch (Exception e) {
                                sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-number").replace("&", "\u00a7"));
                            }
                        } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-user").replace("&", "\u00a7"));
                    } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Usage.eco-add").replace("&", "\u00a7"));
                } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.no-permissions").replace("&", "\u00a7"));
            } else if(args[0].toLowerCase(Locale.ROOT).equals("pay")){
                if(sender instanceof Player){
                    Player p = (Player) sender;
                    if(p.hasPermission("neco.pay")){
                        if(args.length >= 4) {
                            if (Economy.userExists(args[1])) {
                                try {
                                    double amount = Double.parseDouble(args[2]);
                                    if (amount > 0) {
                                        if(Economy.currencies.containsKey(args[3])) {
                                            Double dobl = Economy.getBalance(p.getName(), args[3]);
                                            if (dobl >= amount) {
                                                Economy.addBalance(args[1], args[3], amount);
                                                Economy.addBalance(p.getName(), args[3], -amount);
                                                p.sendMessage(NEconomy.pref + NEconomy.a.getConfig().getString("Messages.Economy.Success.eco-pay-from").replace("{player}", args[1]).replace("{amount}", "" + amount).replace("{currency}", Economy.currencies.get(args[3])).replace("&", "\u00a7"));
                                                if (Bukkit.getPlayer(args[1]) != null)
                                                    Bukkit.getPlayer(args[1]).sendMessage(NEconomy.pref + NEconomy.a.getConfig().getString("Messages.Economy.Success.eco-pay-to").replace("{player}", p.getName()).replace("{amount}", "" + amount).replace("{currency}", Economy.currencies.get(args[3])).replace("&", "\u00a7"));
                                            } else p.sendMessage(NEconomy.pref + NEconomy.a.getConfig().getString("Messages.Economy.Errors.insufficient").replace("&", "\u00a7"));
                                        } else p.sendMessage(NEconomy.pref + NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-currency").replace("&", "\u00a7"));
                                    } else p.sendMessage(NEconomy.pref + NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-number").replace("&", "\u00a7"));
                                } catch (Exception e) {
                                    p.sendMessage(NEconomy.pref + NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-number").replace("&", "\u00a7"));
                                }
                            } else p.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-user").replace("&", "\u00a7"));
                        } else p.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Usage.eco-pay").replace("&", "\u00a7"));
                    } else p.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.no-permissions").replace("&", "\u00a7"));
                } else sender.sendMessage(NEconomy.a.getConfig().getString("Messages.for-players").replace("&", "\u00a7"));
            } else if (args[0].equals("info")) {
                if(sender.hasPermission("neco.info")){
                    if(args.length >= 2){
                        if(Economy.userExists(args[1])){
                            sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Info").replace("{player}", args[1]).replace("&", "\u00a7"));
                            for(String id : Economy.currencies.keySet()){
                                sender.sendMessage(("  &f- " + Economy.currencies.get(id)+"&f: "+Economy.getBalance(args[1], id)).replace("&", "\u00a7"));
                            }
                        } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.incorrect-user").replace("&", "\u00a7"));
                    } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Usage.eco-info").replace("&", "\u00a7"));
                } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Economy.Errors.no-permissions").replace("&", "\u00a7"));
            } else sender.sendMessage(NEconomy.pref+ NEconomy.a.getConfig().getString("Messages.Usage.eco").replace("&", "\u00a7"));
        }
        return true;
    }
}
