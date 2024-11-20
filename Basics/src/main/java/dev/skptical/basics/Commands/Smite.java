package dev.skptical.basics.Commands;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Basics;
import dev.skptical.basics.Storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Smite implements CommandExecutor, TabCompleter {

    private static Basics instance;

    public static HashMap<Player, Integer> lightningPlayers = new HashMap<Player, Integer>();

    public Smite(Basics plugin){
        instance = plugin;
        Util.registerCommand("smite", instance, this, this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("smite") || label.equalsIgnoreCase("lightning") || label.equalsIgnoreCase("shock") || label.equalsIgnoreCase("thor")) {
            if (sender instanceof Player) {
                Player p = ((Player) sender);
                if(args.length == 0){
                    if(p.hasPermission("basics.smite")){
                        p.sendMessage(Util.t(Config.smiteMessage.replaceAll("%power%", "1").replaceAll("%player%", p.getName())));
                        Block targetBlock = p.getTargetBlockExact(Config.smiteDistance);
                        if(targetBlock == null){
                            Bukkit.getConsoleSender().sendMessage(Basics.PREFIX + ChatColor.RED + " " + p.getName() + " tried to use /smite but the target block was too far, this can be changed in the config.yml");
                            return true;
                        }
                        p.getWorld().strikeLightning(targetBlock.getLocation());


                    }else{
                        p.sendMessage(Util.t(Config.noPermissionMessage));
                        Basics.permissionAlert("/smite", p);
                    }

                }
                if(args.length == 1){
                    if(p.hasPermission("basics.smite.others")){
                        if(Util.getPlayer(args[0]) == null){
                            sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. ");
                            return true;
                        }else{
                            Player target = Util.getPlayer(args[0]);
                            target.getWorld().strikeLightning(target.getLocation());
                            p.sendMessage(Util.t(Config.smiteOtherMessage.replaceAll("%power%", "1").replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName())));
                        }


                    }else{
                        p.sendMessage(Util.t(Config.noPermissionMessage));
                        Basics.permissionAlert("/smite", p);
                    }


                }

                if(args.length == 2){
                    if(p.hasPermission("basics.smite.others")){
                        if(Util.getPlayer(args[0]) == null){
                            sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. ");
                            return true;
                        }
                        try{
                            int power = Integer.parseInt(args[1]);
                            Player target = Util.getPlayer(args[0]);

                            lightningPlayers.put(target, power);
                            target.getWorld().strikeLightning(target.getLocation());
                            p.sendMessage(Util.t(Config.smiteOtherMessage.replaceAll("%power%", "" + power).replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName())));




                        }catch (Exception e){
                            sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. ");
                            return true;
                        }


                    }else{
                        p.sendMessage(Util.t(Config.noPermissionMessage));
                        Basics.permissionAlert("/smite", p);
                    }



                }

                if(args.length > 2){
                    if (p.hasPermission("basics.smite")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. "));



                    } else {
                        p.sendMessage(Config.noPermissionMessage);
                        Basics.permissionAlert("/smite", p);

                    }

                }






            }else{
                if(args.length == 0){

                    sender.sendMessage(ChatColor.RED + "Console cannot execute this command.");

                }
                if(args.length == 1){
                    if(sender.hasPermission("basics.smite.others")){
                        if(Util.getPlayer(args[0]) == null){
                            sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. ");
                            return true;
                        }else{
                            Player target = Util.getPlayer(args[0]);
                            target.getWorld().strikeLightning(target.getLocation());
                            sender.sendMessage(Util.t(Config.smiteOtherMessage.replaceAll("%power%", "1").replaceAll("%player%", sender.getName()).replaceAll("%target%", target.getName())));
                        }


                    }else{
                        sender.sendMessage(Util.t(Config.noPermissionMessage));
                        Basics.permissionAlert("/smite", sender);
                    }


                }

                if(args.length == 2){
                    if(sender.hasPermission("basics.smite.others")){
                        if(Util.getPlayer(args[0]) == null){
                            sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. ");
                            return true;
                        }
                        try{
                            int power = Integer.parseInt(args[1]);
                            Player target = Util.getPlayer(args[0]);

                            lightningPlayers.put(target, power);
                            target.getWorld().strikeLightning(target.getLocation());
                            sender.sendMessage(Util.t(Config.smiteOtherMessage.replaceAll("%power%", "" + power).replaceAll("%player%", sender.getName()).replaceAll("%target%", target.getName())));




                        }catch (Exception e){
                            sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. ");
                            return true;
                        }


                    }else{
                        sender.sendMessage(Util.t(Config.noPermissionMessage));
                        Basics.permissionAlert("/smite", sender);
                    }



                }

                if(args.length > 2){
                    if (sender.hasPermission("basics.smite")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/smite \n - Summon lightning at a position. \n/smite [player] [power]\n - Summon lightning at a players position. "));



                    } else {
                        sender.sendMessage(Config.noPermissionMessage);
                        Basics.permissionAlert("/smite", sender);

                    }

                }


            }

        }
        return true;
    }




    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("smite")){
            if(sender.hasPermission("basics.smite")){
                List<String> smite = new ArrayList<>();
                if(args.length == 1){
                    if(sender.hasPermission("basics.smite.others")){
                        for(Player p: Bukkit.getOnlinePlayers()){
                            smite.add(p.getName());
                        }
                        return smite;
                    }
                    return new ArrayList<String>();
                }else if(args.length == 2){
                    if(sender.hasPermission("basics.smite.others")){
                        for(int i = 1; i < 100; i++){
                            smite.add(i + "");
                        }
                        return smite;
                    }
                }else{
                    return new ArrayList<String>();
                }
            }
            return new ArrayList<String>();
        }
        return null;
    }
}
