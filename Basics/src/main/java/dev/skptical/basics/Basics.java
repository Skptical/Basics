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
import org.checkerframework.framework.qual.AnnotatedFor;


import java.io.IOException;


public class Basics extends JavaPlugin {

    public static final String PREFIX = ChatColor.GREEN + "[BASICS]"; //  PREFIX Constant contains prefix for messages
    public static String version; // Version Constaint contains current version release
    private static Basics plugin; // Private plugin instance (Class Effect Only)

    /**
     * Getter method for the main class instance for Basics.
     * @since 1.0
     * @return Plugin Instance Object
     *
     * */
    public static Basics instance(){return plugin;}
    public static FileManager files; // Private file manager instance
    protected static ConsoleCommandSender console; // Console instance UNSET


    /**
     * Reload method for all plugin instances.
     * Reloads the config to memory, resetting all variable objects.
     * Attempts config, messages, data regeneration if needed.
     * @param plugin Basics plugin instance
     * */
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

    /**
     * Sends an alert to all members with the permission staff.alerts.deniedpermission that a player tried to access a command they dont have access to.
     * Format can be altered and changed in messages.yml
     * @param command The command that was denied access.
     * @param player The player who tried to access the command.
     * */
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
    /**
     * Sends an alert to all members with the permission staff.alerts.deniedpermission that a player tried to access a command they dont have access to.
     * Format can be altered and changed in messages.yml
     * @param command The command that was denied access.
     * @param player The player who tried to access the command.
     * */
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
    /**
     * Sends an alert to all members with the permission basics.notify.
     * Format can be altered and changed in messages.yml
     * @param action Message to be sent.
     * @param p Player who is sending the action/message..
     * */
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

    /**
     * Sends an alert to all members with the permission basics.notify.
     * Format can be altered and changed in messages.yml
     * @param action Message to be sent.
     * @param p Player who is sending the action/message..
     * */
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


    /**
     * Sends an alert to all members with a specific permission.
     * Format can be altered and changed in messages.yml
     * @param message The message to be sent.
     * @param permission The permission the player needs to recieve the message.
     * */

    public static void alert(String message, String permission){
        console.sendMessage(Util.t(message));
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.hasPermission(permission)){
                player.sendMessage(Util.t(message));
            }

        }

    }

    /**
     * Registers an event with the plugin.
     * @param listener Event listener instance
     * */
    public void registerEvent(Listener listener){

        Util.registerEvent(instance(), listener);
    }

    /**
     * Log method, logs contained message to console.
     * @deprecated This is only intended to be used by other log methods, not to directly log to console.
     * @param message The message to be logged.
     * */

    @Deprecated
    private static void log(String message){
        // TODO: ADD LOG FEATURE



    }

    // Constants for log prefixes, prefix before each logged message.
    private static final String INFO_PREFIX="&e[INFO]", ERROR_PREFIX="&c[ERROR]", DEBUG_PREFIX="&b&l[DEBUG]";

    /**
     * Log method, logs contained message to console.
     * @param message The message to be logged.
     * */
    public static void logInfo(String message){
        message = PREFIX + "" + INFO_PREFIX +"&f:" + message;
        log(Util.t(message));
    }

    /**
     * Log method, logs contained message to console.
     * @param message The message to be logged.
     * */
    public static void logError(String message){
        message = PREFIX + "" + ERROR_PREFIX +"&f:" + message;
        log(Util.t(message));
    }
    /**
     * Log method, logs contained message to console.
     * @param message The message to be logged.
     * */
    public static void logDebug(String message){
        message = PREFIX + "" + DEBUG_PREFIX +"&f:" + message;

        log(Util.t(message));
    }


    /**
     * Main Startup protocol, called when plugin is enabled.
     * @param instance The live plugin instance.
     **/
    private void mainStartup(Basics instance) {
        // Getting the console through the instance.
        console = instance.getServer().getConsoleSender();
        // Update message
        console.sendMessage(ChatColor.GREEN + "Basics has successfully been loaded.");
        // Try and generate config files
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
            // Any config generation related errors.
            exception.printStackTrace();
        }
        // Set the config variables at startup
        Config.setupVars();

        // Register events
        registerEvent(new SmiteHandler());
        registerEvent(new PlayerCommandExecuteHandler());
        registerEvent(new Logger());
        registerEvent(new ChatListener());

        // Register commands
        new Gamemode(instance);
        new Reload(instance);
        new QuickGamemode(instance);
        new Smite(instance);
        new CommandSpy(instance);
        new WhoIs(instance);
        new Message(instance);


    }
    /**
     * Updates all players online via player logger class
     **/
    void updatePlayers(){
        for(Player p: Bukkit.getOnlinePlayers()){
            PlayerLogger.updatePlayer(p);
        }
    }
    /**
     * Main Shutdown protocol, called when plugin is disabled.
     * @param instance The live plugin instance.
     **/
    private void mainShutdown(Basics instance) {
        // Getting the console
        console = instance.getServer().getConsoleSender();
        // Update message
        console.sendMessage(ChatColor.RED + "Basics has successfully been unloaded.");
        //
        for(Player p: CommandSpy.toggledCmdPlayers){
            // Update data file for cmdSpy
            files.getData().set(p.getUniqueId() + "." + "commandSpyEnabled", true);
            files.saveData();
        }
        // Update all player info via data.yml
        updatePlayers();
        // End of shutdown protocol



    }


    // Enable method
    @Override
    public void onEnable(){
        // Setting instance for plugin
        plugin = this;
        // Setting instance for file manager
        files = new FileManager();
        // Version grabber
        version = getDescription().getVersion();
        // Calling main startup protocol
        mainStartup(this);



    }

    // Disable method
    @Override
    public void onDisable(){
        // Calls main shutdown protocol
        mainShutdown(this);


    }



}
