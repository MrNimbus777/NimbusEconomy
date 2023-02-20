package net.nimbus.economy.MySQL;

import org.bukkit.Bukkit;
import net.nimbus.economy.Economy;
import net.nimbus.economy.NEconomy;

import java.sql.*;
import java.util.ArrayList;
/*
*выв
 */
public class MySQLUtils {
    public static Connection con;
    public static Connection getConnection(){
        try{
            String adress = NEconomy.a.getConfig().getString("DataBase.adress");
            String name = NEconomy.a.getConfig().getString("DataBase.name");
            String user = NEconomy.a.getConfig().getString("DataBase.user");
            String password = NEconomy.a.getConfig().getString("DataBase.password");
            return DriverManager.getConnection("jdbc:mysql://"+adress+"/"+name, user, password);
        } catch (Exception e){
            return null;
        }
    }
    public static boolean setValue(String type, String player, double amount) {
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM `"+ type +"` WHERE player = '" + player + "'");
            st.execute("INSERT INTO "+type+"(player,amount) VALUES ('"+player+"', " + amount + ") ON DUPLICATE KEY UPDATE amount = " + amount);
            st.close();
            return true;
        } catch (SQLException e) {
            con = getConnection();
            Bukkit.getLogger().severe("Trying to recconect to MySQL Database...");
            if(con != null) {
                setValue(type, player, amount);
                return true;
            }
            return false;
        }
    }


    public static double getValue(String type, String player) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM `"+ type +"` WHERE player = '"+ player +"'");
            ResultSet rs = statement.executeQuery();
            double ret = 0;
            if (rs.next()) {
                ret = rs.getDouble("amount");
            }
            rs.close();
            statement.close();
            return ret;
        } catch (SQLException e) {
            con = getConnection();
            Bukkit.getLogger().severe("Trying to recconect to MySQL Database...");
            if(con != null) return getValue(type, player);
            return 0;
        }
    }
    public static boolean userExists(String player) {
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM `"+ new ArrayList<>(Economy.currencies.keySet()).get(0) +"` WHERE player = '"+ player +"'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                rs.close();
                statement.close();
                return true;
            } else {
                rs.close();
                statement.close();
                return false;
            }
        } catch (SQLException e) {
            con = getConnection();
            Bukkit.getLogger().severe("Trying to recconect to MySQL Database...");
            if(con != null) return userExists(player);
            return false;
        }
    }

    public static boolean createIfNotExists(String type){
        try {
            Bukkit.getLogger().info("Trying to create "+type+" mysql table...");
            PreparedStatement statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS `"+ type +"` (`player` VARCHAR(20) PRIMARY KEY, `amount` FLOAT(30,2))");
            statement.execute();
            statement.close();
            Bukkit.getLogger().info("Success!");
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Failed!");
            e.printStackTrace();
            return false;
        }
    }
}
