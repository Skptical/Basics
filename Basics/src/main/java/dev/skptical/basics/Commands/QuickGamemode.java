package dev.skptical.basics.Commands;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Basics;
import dev.skptical.basics.Storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class QuickGamemode implements CommandExecutor, TabCompleter {

    private static Basics instance;

    public QuickGamemode(Basics plugin){
        instance = plugin;
        Util.registerCommand("gmc", instance, this, this);
        Util.registerCommand("gms", instance, this, this);
        Util.registerCommand("gma",instance, this, this);
        Util.registerCommand("gmsp", instance, this, this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.equalsIgnoreCase("creative") || s.equalsIgnoreCase("gmc") || s.equalsIgnoreCase("creativemode") || s.equalsIgnoreCase("gm1")){


            if(commandSender instanceof Player){
                Player player = (Player)commandSender;
                if(strings.length == 0){
                    if(player.hasPermission("basics.gamemode.creative")){
                        player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "creative").replaceAll("%player%", player.getName()));
                        player.setGameMode(GameMode.CREATIVE);
                        Basics.staffAlert(player, "changed to creative mode");

                    }else{
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gmc", player);
                        }
                    }
                }else{
                    if(player.hasPermission("basics.gamemode.creative") && player.hasPermission("basics.gamemode.other")){
                        if(Bukkit.getPlayer(strings[0]) == null) {
                            player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gmc \n - Change your current gamemode to creative. \n/gmc [player] \n - Change another players gamemode to creative. ");
                        }else{
                            Player target = Util.getPlayer(strings[0]);
                            player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", player.getName()).replaceAll("%gamemode%", "creative"));
                            target.setGameMode(GameMode.CREATIVE);

                            Basics.staffAlert(player, "changed " + target.getName() + "'s gamemode to creative");




                        }




                    }else{
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {

                            Basics.permissionAlert("/gmc", player);
                        }
                    }
                }



            }else {
                if (strings.length == 0) {
                    commandSender.sendMessage(ChatColor.RED + "You cannot use this command as console!");
                } else {
                    if (Bukkit.getPlayer(strings[0]) == null || strings[0] == null) {
                        commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gmc \n - Change your current gamemode to creative. \n/gmc [player] \n - Change another players gamemode to creative. ");

                    } else {
                        Player target = Util.getPlayer(strings[0]);
                        if (target == null) {
                            commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gmc \n - Change your current gamemode to creative. \n/gmc [player] \n - Change another players gamemode to creative. ");
                            return true;

                        }
                        commandSender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "creative"));
                        target.setGameMode(GameMode.CREATIVE);

                        Basics.staffAlert(commandSender, "changed " + target.getName() + "'s gamemode to creative");

                    }
                }


            }
        }else if(s.equalsIgnoreCase("survival") || s.equalsIgnoreCase("gms") || s.equalsIgnoreCase("survivalmode") || s.equalsIgnoreCase("gm0")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (strings.length == 0) {
                    if (player.hasPermission("basics.gamemode.survival")) {
                        player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "survival").replaceAll("%player%", player.getName()));
                        player.setGameMode(GameMode.SURVIVAL);
                        Basics.staffAlert(player, "changed to survival mode");

                    } else {
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gmc", player);
                        }
                    }
                } else {
                    if (player.hasPermission("basics.gamemode.survival") && player.hasPermission("basics.gamemode.other")) {
                        if (Bukkit.getPlayer(strings[0]) == null) {
                            player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gms \n - Change your current gamemode to survival. \n/gms [player] \n - Change another players gamemode to survival. ");
                        } else {
                            Player target = Util.getPlayer(strings[0]);
                            player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", player.getName()).replaceAll("%gamemode%", "survival"));
                            target.setGameMode(GameMode.SURVIVAL);

                            Basics.staffAlert(player, "changed " + target.getName() + "'s gamemode to survival");


                        }


                    } else {
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gms", player);
                        }
                    }
                }


            } else {
                if (strings.length == 0) {
                    commandSender.sendMessage(ChatColor.RED + "You cannot use this command as console!");
                } else {
                    if (Bukkit.getPlayer(strings[0]) == null || strings[0] == null) {
                        commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gms \n - Change your current gamemode to survival. \n/gms [player] \n - Change another players gamemode to survival. ");

                    } else {
                        Player target = Util.getPlayer(strings[0]);
                        if (target == null) {
                            commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gms \n - Change your current gamemode to survival. \n/gms [player] \n - Change another players gamemode to survival. ");
                            return true;

                        }
                        commandSender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "survival"));
                        target.setGameMode(GameMode.SURVIVAL);
                        Basics.staffAlert(commandSender, "changed " + target.getName() + "'s gamemode to survival");

                    }
                }


            }

        }else if(s.equalsIgnoreCase("spectator") || s.equalsIgnoreCase("gmsp") || s.equalsIgnoreCase("spec") || s.equalsIgnoreCase("gm3")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (strings.length == 0) {
                    if (player.hasPermission("basics.gamemode.spectator")) {
                        player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "spectator").replaceAll("%player%", player.getName()));
                        player.setGameMode(GameMode.SPECTATOR);
                        Basics.staffAlert(player, "changed to spectator mode");

                    } else {
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gmsp", player);
                        }
                    }
                } else {
                    if (player.hasPermission("basics.gamemode.spectator") && player.hasPermission("basics.gamemode.other")) {
                        if (Bukkit.getPlayer(strings[0]) == null) {
                            player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gmsp \n - Change your current gamemode to spectator. \n/gmsp [player] \n - Change another players gamemode to spectator. ");
                        } else {
                            Player target = Util.getPlayer(strings[0]);
                            player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", player.getName()).replaceAll("%gamemode%", "spectator"));
                            target.setGameMode(GameMode.SPECTATOR);

                            Basics.staffAlert(player, "changed " + target.getName() + "'s gamemode to spectator");


                        }


                    } else {
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gmsp", player);
                        }
                    }
                }


            } else {
                if (strings.length == 0) {
                    commandSender.sendMessage(ChatColor.RED + "You cannot use this command as console!");
                } else {
                    if (Bukkit.getPlayer(strings[0]) == null || strings[0] == null) {
                        commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gmsp \n - Change your current gamemode to spectator. \n/gmsp [player] \n - Change another players gamemode to spectator. ");

                    } else {
                        Player target = Util.getPlayer(strings[0]);
                        if (target == null) {
                            commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gmsp \n - Change your current gamemode to spectator. \n/gmsp [player] \n - Change another players gamemode to spectator. ");
                            return true;

                        }
                        commandSender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "spectator"));
                        target.setGameMode(GameMode.SPECTATOR);

                        Basics.staffAlert(commandSender, "changed " + target.getName() + "'s gamemode to spectator");

                    }
                }


            }

        }else if(s.equalsIgnoreCase("adventure") || s.equalsIgnoreCase("gma") || s.equalsIgnoreCase("adventuremode") || s.equalsIgnoreCase("gm2")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if (strings.length == 0) {
                    if (player.hasPermission("basics.gamemode.adventure")) {
                        player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "adventure").replaceAll("%player%", player.getName()));
                        player.setGameMode(GameMode.ADVENTURE);
                        Basics.staffAlert(player, "changed to adventure mode");

                    } else {
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gma", player);
                        }
                    }
                } else {
                    if (player.hasPermission("basics.gamemode.adventure") && player.hasPermission("basics.gamemode.other")) {
                        if (Bukkit.getPlayer(strings[0]) == null) {
                            player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gma \n - Change your current gamemode to adventure. \n/gma [player] \n - Change another players gamemode to adventure. ");
                        } else {
                            Player target = Util.getPlayer(strings[0]);
                            player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", player.getName()).replaceAll("%gamemode%", "adventure"));
                            target.setGameMode(GameMode.ADVENTURE);

                            Basics.staffAlert(player, "changed " + target.getName() + "'s gamemode to adventure");


                        }


                    } else {
                        player.sendMessage(Config.noPermissionMessage);
                        if (Config.alertOnNoPermission) {
                            Basics.permissionAlert("/gma", player);
                        }
                    }
                }


            } else {
                if (strings.length == 0) {
                    commandSender.sendMessage(ChatColor.RED + "You cannot use this command as console!");
                } else {
                    if (Bukkit.getPlayer(strings[0]) == null || strings[0] == null) {
                        commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gma \n - Change your current gamemode to adventure. \n/gma [player] \n - Change another players gamemode to adventure. ");

                    } else {
                        Player target = Util.getPlayer(strings[0]);
                        if (target == null) {
                            commandSender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gma \n - Change your current gamemode to adventure. \n/gma [player] \n - Change another players gamemode to adventure. ");
                            return true;
                        }
                        commandSender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "adventure"));
                        target.setGameMode(GameMode.ADVENTURE);

                        Basics.staffAlert(commandSender, "changed " + target.getName() + "'s gamemode to adventure");

                    }
                }


            }

        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("gmc") || command.getName().equalsIgnoreCase("gma") || command.getName().equalsIgnoreCase("gmsp") || command.getName().equalsIgnoreCase("gms")){
            if(commandSender instanceof Player){
                Player player = (Player)commandSender;
                if(player.hasPermission("basics.gamemode.other") && strings.length == 1){
                    List<String> list = new ArrayList<String>();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        list.add(p.getName());
                    }
                    return list;

                }
                if(strings.length > 1){
                    return new ArrayList<String>();
                }

            }else{
                List<String> list = new ArrayList<String>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                }
                return list;

            }
            return new ArrayList<String>();

        }

        return null;
    }
}
