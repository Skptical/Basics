package dev.skptical.basics;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Commands.*;
import dev.skptical.basics.Listeners.ChatListener;
import dev.skptical.basics.Listeners.Logger;
import dev.skptical.basics.Listeners.PlayerCommandExecuteHandler;
import dev.skptical.basics.Listeners.SmiteHandler;
import dev.skptical.basics.Storage.Config;
import dev.skptical.basics.Storage.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.IOException;


public class Basics extends JavaPlugin {

    public static final String PREFIX = ChatColor.GREEN + "[BASICS]";
    public static String version;
    private static Basics plugin;
    public static Basics instance(){return plugin;}
    public static FileManager files;
    protected static ConsoleCommandSender console;

    public static void reload(Basics plugin){
        console = plugin.getServer().getConsoleSender();
        console.sendMessage(ChatColor.GREEN + "Basics has successfully been reloaded.");
        try{
            files.generateConfig();
            files.generateMessages();
            files.generateData();
            files.saveConfig();
            files.saveMessages();
            files.saveData();
            files.reloadConfig();
            files.reloadMessages();
            files.reloadData();


        }catch (IOException | InvalidConfigurationException exception){
            exception.printStackTrace();
        }

        Config.setupVars();


    }
    public static void permissionAlert(String command, CommandSender player){
        if(Config.alertOnNoPermission){
            String message = Config.permissionAlert.replaceAll("%player%", player.getName()).replaceAll("%command%", command);
            console.sendMessage(Util.t(message));
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("staff.alerts.deniedpermission")) {
                    p.sendMessage(Util.t(message));
                }

            }
        }
        return;
    }

    public static void permissionAlert(String command, Player player){
        if(Config.alertOnNoPermission){
            String message = Config.permissionAlert.replaceAll("%player%", player.getName()).replaceAll("%command%", command);
            console.sendMessage(Util.t(message));
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("staff.alerts.deniedpermission")) {
                    p.sendMessage(Util.t(message));
                }

            }
        }
        return;
    }

    public static void staffAlert(CommandSender p, String action) {
        if(Config.staffAlerts){
            String message = Config.staffNotifyMessage.replaceAll("%player%", p.getName()).replaceAll("%action%", action);
            console.sendMessage(Util.t(message));
            for (Player e : Bukkit.getOnlinePlayers()) {
                if (e.hasPermission("basics.notify")) {
                    e.sendMessage(Util.t(message));
                }

            }



        }
        return;
    }
    public static void staffAlert(Player p, String action) {
        if(Config.staffAlerts){
            String message = Config.staffNotifyMessage.replaceAll("%player%", p.getName()).replaceAll("%action%", action);
            console.sendMessage(Util.t(message));
            for (Player e : Bukkit.getOnlinePlayers()) {
                if (e.hasPermission("basics.notify")) {
                    e.sendMessage(Util.t(message));
                }

            }



        }
        return;
    }




    public static void alert(String message, String permission){
        console.sendMessage(Util.t(message));
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.hasPermission(permission)){
                player.sendMessage(Util.t(message));
            }

        }

    }

    public void registerEvent(Listener listener){

        Util.registerEvent(instance(), listener);
    }

    private static void log(String message){
        // Already translated



    }

    private static final String INFO_PREFIX="&e[INFO]", ERROR_PREFIX="&c[ERROR]", DEBUG_PREFIX="&b&l[DEBUG]";

    public static void logInfo(String message){
        message = PREFIX + "" + INFO_PREFIX +"&f:" + message;
        log(Util.t(message));
    }


    public static void logError(String message){
        message = PREFIX + "" + ERROR_PREFIX +"&f:" + message;
        log(Util.t(message));
    }

    public static void logDebug(String message){
        message = PREFIX + "" + DEBUG_PREFIX +"&f:" + message;

        log(Util.t(message));
    }



    private void mainStartup(Basics instance) {
        console = instance.getServer().getConsoleSender();
        console.sendMessage(ChatColor.GREEN + "Basics has successfully been loaded.");
        try{
            files.generateConfig();
            files.generateMessages();
            files.generateData();
            files.saveConfig();
            files.saveMessages();
            files.saveData();
            files.reloadConfig();
            files.reloadMessages();
            files.reloadData();


        }catch (IOException | InvalidConfigurationException exception){
            exception.printStackTrace();
        }

        Config.setupVars();
        registerEvent(new SmiteHandler());
        registerEvent(new PlayerCommandExecuteHandler());
        registerEvent(new Logger());
        registerEvent(new ChatListener());


        new Gamemode(instance);
        new Reload(instance);
        new QuickGamemode(instance);
        new Smite(instance);
        new CommandSpy(instance);
        new WhoIs(instance);
        new Message(instance);


    }

    void updatePlayers(){
        for(Player p: Bukkit.getOnlinePlayers()){
            PlayerLogger.updatePlayer(p);
        }
    }

    private void mainShutdown(Basics instance) {
        console = instance.getServer().getConsoleSender();
        console.sendMessage(ChatColor.RED + "Basics has successfully been unloaded.");

        for(Player p: CommandSpy.toggledCmdPlayers){
            files.getConfig().set(p.getUniqueId() + "." + "commandSpyEnabled", true);
            files.saveConfig();
        }
        updatePlayers();




    }

    @Override
    public void onEnable(){
        plugin = this;
        files = new FileManager();
        version = getDescription().getVersion();

        mainStartup(this);



    }

    @Override
    public void onDisable(){
        mainShutdown(this);


    }



}
