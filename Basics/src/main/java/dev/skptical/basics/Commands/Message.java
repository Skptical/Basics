package dev.skptical.basics.Commands;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Basics;
import dev.skptical.basics.Storage.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Message implements CommandExecutor, TabExecutor {

    private static Basics instance;

    public Message(Basics plugin){
        instance = plugin;
        Util.registerCommand("message", instance, this, this);
        Util.registerCommand("r", instance, this, this);
    }
    public static ArrayList<Player> socialSpy = new ArrayList<Player>();
    public static ArrayList<Player> toggledPm = new ArrayList<Player>();
    public static HashMap<Player,String> lastMessaged = new HashMap<Player, String>();

    public static ArrayList<Player> toggledReply = new ArrayList<>();
    List<String> aliases = Arrays.asList("message", "w", "m", "t", "pm", "msg", "tell", "whisper", "r", "reply");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(aliases.contains(s.toLowerCase())){
            if(sender instanceof Player) {
                Player p = (Player)sender;

                if(s.equalsIgnoreCase("r") || s.equalsIgnoreCase("reply")){
                    if(!p.hasPermission("basics.msg")) {
                        sender.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/reply", sender);
                        }
                        return true;
                    }else{
                        if(args.length == 0){
                            if(lastMessaged.containsKey(p)){
                                if(Util.isOnline(lastMessaged.get(p))){
                                    if(toggledReply.contains(p)){
                                        toggledReply.remove(p);
                                        p.sendMessage(Config.toggledReply.replaceAll("%toggled%", ChatColor.RED + "Disabled"));

                                    }else{
                                        toggledReply.add(p);
                                        p.sendMessage(Config.toggledReply.replaceAll("%toggled%", ChatColor.GREEN + "Enabled"));
                                    }



                                }else{
                                    p.sendMessage(Config.noReply);
                                }
                            }else{
                                p.sendMessage(Config.noReply);;
                            }

                            return true;
                        }else{
                            if(lastMessaged.containsKey(p)){
                                if(Util.isOnline(lastMessaged.get(p))){
                                    lastMessaged.put(Util.getPlayer(lastMessaged.get(p)), p.getName());
                                    String message = "";
                                    for (int i = 0; i < args.length; i++) {
                                        message += args[i] + " ";
                                    }
                                    message = message.trim();
                                    Player target = Util.getPlayer(lastMessaged.get(p));

                                    if(toggledPm.contains(target)){
                                        p.sendMessage(Config.pmDisabledOtherMessage);
                                        return true;
                                    }
                                    for(Player player : socialSpy){
                                        player.sendMessage(); //TODO: SOCIAL SPY
                                    }

                                    p.sendMessage(Config.messageSent.replaceAll("%message%", message).replaceAll("%target%", target.getName()));
                                    target.sendMessage(Config.messageReceived.replaceAll("%message%", message).replaceAll("%player%", p.getName()));

                                }else{
                                    p.sendMessage(Config.noReply);
                                }
                            }else{
                                p.sendMessage(Config.noReply);;
                            }

                        }



                    }



                    return true;
                }




                 if(!p.hasPermission("basics.msg")) {
                     sender.sendMessage(Config.noPermissionMessage);
                     if (Config.alertOnNoPermission) {
                         Basics.permissionAlert("/message", sender);
                     }
                     return true;
                 }
                 if(args.length == 0){
                     p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/msg [player] [message] \n - Message another player privately.");

                  }
                if(args.length == 1){
                    p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/msg [player] [message] \n - Message another player privately.");

                }
                if(args.length > 1){
                    if(!Util.isOnline(args[0])){
                        p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/msg [player] [message] \n - Message another player privately.");
                        return true;
                    }
                    lastMessaged.put(p, args[0]);
                    lastMessaged.put(Util.getPlayer(args[0]), p.getName());
                    String message = "";
                    for (int i = 1; i < args.length; i++) {
                        message += args[i] + " ";
                    }
                    message = message.trim();
                    Player target = Util.getPlayer(args[0]);

                    if(toggledPm.contains(target)){
                        p.sendMessage(Config.pmDisabledOtherMessage);
                        return true;
                    }
                    for(Player player : socialSpy){
                        player.sendMessage(); //TODO: SOCIAL SPY
                    }

                    p.sendMessage(Config.messageSent.replaceAll("%message%", message).replaceAll("%target%", Util.getPlayer(args[0]).getName()));
                    target.sendMessage(Config.messageReceived.replaceAll("%message%", message).replaceAll("%player%", p.getName()));



                }



            }
            //console


        }

        return true;
    }
    //TODO: add other util stuff (Checking for onlin\e players and shit that is repeated (Clean up code essentially ))
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
