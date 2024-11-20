package dev.skptical.basics.API;

import dev.skptical.basics.Basics;
import dev.skptical.basics.Listeners.SmiteHandler;
import dev.skptical.basics.Storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Util {

    public static void registerEvent(Basics instance, Listener e){
        instance.getServer().getPluginManager().registerEvents(e, instance);
    }
    public static void registerCommand(String command, Basics instance, CommandExecutor executor, TabCompleter tabExecutor){
        instance.getCommand(command).setExecutor(executor);
        instance.getCommand(command).setTabCompleter(tabExecutor);
    }

    public static String t(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }


    public static Player getPlayer(String name){

        return Bukkit.getPlayerExact(name);
    }

    public static boolean isOnline(String name){

        return Bukkit.getPlayerExact(name) != null;
    }

    private static Object get(String s){
        return Basics.instance().files.getData().get(s);
    }


    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }


    public static String replaceWhoIsMessage(String m, String foundUUID)
    {
        ArrayList<String> namedAlts = new ArrayList<>();
        Collection<String> items = Basics.instance().files.getData().getConfigurationSection("").getKeys(false);
        String IP = (String) get(foundUUID + "." + "ip");


        for(String item : items) {
            if (Basics.instance().files.getData().getString(item + "." + "ip").equalsIgnoreCase(IP)) {
                namedAlts.add(Basics.instance().files.getData().getString(item + "." + "inGameName"));
            }
        }
        namedAlts = removeDuplicates(namedAlts);

        int i = 0;
        String alts = "";
        for(String name : namedAlts){
            if(i == 0){
                alts = name;
            }
            i++;
            alts = alts + ", " + name;
        }





        String message = m.replaceAll("%newline%", "\n").replaceAll("%uuid%", foundUUID).replaceAll("%player%", ((String)get(foundUUID + "." + "inGameName")));
        message = message.replaceAll("%nickname%", ((String)get(foundUUID + "." + "nick")));
        message = message.replaceAll("%hunger%", (get(foundUUID + "." + "hunger") + ""));
        message = message.replaceAll("%location%", get(foundUUID + "." + "location") + "" + " (" + get(foundUUID + "." + "world") + ")");
        message = message.replaceAll("%exp%", (get(foundUUID + "." + "exp") + ""));
        message = message.replaceAll("%ip%", (get(foundUUID + "." + "ip") + ""));
        message = message.replaceAll("%gamemode%", ((String)get(foundUUID + "." + "gamemode")));
        message = message.replaceAll("%isop%", (get(foundUUID + "." + "isOp") + ""));
        message = message.replaceAll("%health%", (get(foundUUID + "." + "health") + ""));
        message = message.replaceAll("%level%", (get(foundUUID + "." + "level") + ""));
        message = message.replaceAll("%alts%", alts);

        message = message.replaceAll("true", ChatColor.GREEN + "True");
        message = message.replaceAll("false", ChatColor.RED + "False");
        return message;

    }



}
