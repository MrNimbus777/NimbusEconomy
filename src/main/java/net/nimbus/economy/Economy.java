package net.nimbus.economy;

import org.bukkit.configuration.file.FileConfiguration;
import ru.nimbus.economy.MySQL.MySQLUtils;

import java.util.HashMap;

public class Economy {

    public static HashMap<String, String> currencies = new HashMap<>();

    public static Double getBalance(String name, String type){
        if(currencies.containsKey(type)){
            if(NEconomy.database){
                if(MySQLUtils.userExists(name)) {
                    return MySQLUtils.getValue(type, name);
                }
            } else if(YmlFile.hash.keySet().contains("Users/"+name)){
                FileConfiguration conf = YmlFile.get("Users", name).getConfig();
                return conf.getDouble("Currencies."+type);
            } return null;
        } return null;
    }

    public static void addBalance(String name, String type, Double amount){
        if(currencies.containsKey(type)){
            if(NEconomy.database){
                if(MySQLUtils.userExists(name)){
                    MySQLUtils.setValue(type, name, MySQLUtils.getValue(type, name)+amount);
                }
            } else if(YmlFile.hash.keySet().contains("Users/"+name)){
                FileConfiguration conf = YmlFile.get("Users", name).getConfig();
                conf.set("Currencies."+type, conf.getDouble("Currencies."+type)+amount);
            }
        }
    }
    public static void setBalance(String name, String type, Double amount){
        if(currencies.containsKey(type)){
            if(MySQLUtils.userExists(name)) {
                if (NEconomy.database) {
                    MySQLUtils.setValue(type, name, amount);
                }
            } else if(YmlFile.hash.keySet().contains("Users/"+name)){
                FileConfiguration conf = YmlFile.get("Users", name).getConfig();
                conf.set("Currencies."+type, amount);
            }
        }
    }
    public static boolean userExists(String name){
        if(NEconomy.database){
            return MySQLUtils.userExists(name);
        } else return YmlFile.hash.containsKey("Users/"+ name);
    }
}
