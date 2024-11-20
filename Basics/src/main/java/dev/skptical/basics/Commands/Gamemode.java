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
import java.util.Arrays;
import java.util.List;

public class Gamemode implements CommandExecutor, TabCompleter {

    private static Basics instance;

    public Gamemode(Basics plugin){
        instance = plugin;
        Util.registerCommand("gamemode", instance, this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. "));
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
                        if (player.hasPermission("basics.gamemode.creative")) {
                            player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "creative").replaceAll("%player%", player.getName()));
                            player.setGameMode(GameMode.CREATIVE);
                            Basics.staffAlert(player,  "changed to creative mode");

                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) {
                        if (player.hasPermission("basics.gamemode.survival")) {
                            player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "survival").replaceAll("%player%", player.getName()));
                            player.setGameMode(GameMode.SURVIVAL);
                            Basics.staffAlert(player,  "changed to survival mode");

                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }
                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) {
                        if (player.hasPermission("basics.gamemode.adventure")) {
                            player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "adventure").replaceAll("%player%", player.getName()));
                            player.setGameMode(GameMode.ADVENTURE);
                            Basics.staffAlert(player,  "changed to adventure mode");


                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }
                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp")) {
                        if (player.hasPermission("basics.gamemode.spectator")) {
                            player.sendMessage(Config.gamemodeChangeMessage.replaceAll("%gamemode%", "spectator").replaceAll("%player%", player.getName()));
                            player.setGameMode(GameMode.SPECTATOR);
                            Basics.staffAlert(player,  "changed to spectator mode");


                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }
                    } else {
                        player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
                        if (player.hasPermission("basics.gamemode.creative")) {
                            if (player.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "creative"));
                                    target.setGameMode(GameMode.CREATIVE);

                                    Basics.staffAlert(player,  "changed " + target.getName() + "'s gamemode to creative");

                                }

                            } else {
                                player.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", player);
                                }

                            }

                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) {
                        if (player.hasPermission("basics.gamemode.survival")) {
                            if (player.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "survival"));
                                    target.setGameMode(GameMode.SURVIVAL);
                                    Basics.staffAlert(player,  "changed " + target.getName() + "'s gamemode to survival");

                                }

                            } else {
                                player.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", player);
                                }

                            }

                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) {
                        if(player.hasPermission("basics.gamemode.adventure")) {
                            if (player.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "adventure"));
                                    target.setGameMode(GameMode.ADVENTURE);
                                    Basics.staffAlert(player,  "changed " + target.getName() + "'s gamemode to adventure");

                                }

                            } else {
                                player.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", player);
                                }

                            }

                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp")) {
                        if (player.hasPermission("basics.gamemode.spectator")) {
                            if (player.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    player.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "spectator"));
                                    target.setGameMode(GameMode.SPECTATOR);
                                    Basics.staffAlert(player,  "changed " + target.getName() + "'s gamemode to spectator");

                                }

                            } else {
                                player.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", player);
                                }

                            }

                        } else {
                            player.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", player);
                            }

                        }


                    }else{
                        player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");
                    }

                }else{
                    player.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                }


            }else{

                if (args.length == 0) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. "));
                } else if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "You cannot use this command as console!");

                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
                        if (sender.hasPermission("basics.gamemode.creative")) {
                            if (sender.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    sender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "creative"));
                                    target.setGameMode(GameMode.CREATIVE);
                                    Basics.staffAlert(sender,  "changed " + target.getName() + "'s gamemode to creative");

                                }

                            } else {
                                sender.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", sender);
                                }

                            }

                        } else {
                            sender.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", sender);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) {
                        if (sender.hasPermission("basics.gamemode.survival")) {
                            if (sender.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    sender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "survival"));
                                    target.setGameMode(GameMode.SURVIVAL);
                                    Basics.staffAlert(sender,  "changed " + target.getName() + "'s gamemode to survival");

                                }

                            } else {
                                sender.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", sender);
                                }

                            }

                        } else {
                            sender.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", sender);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a")) {
                        if(sender.hasPermission("basics.gamemode.adventure")) {
                            if (sender.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    sender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "adventure"));
                                    target.setGameMode(GameMode.ADVENTURE);
                                    Basics.staffAlert(sender,  "changed " + target.getName() + "'s gamemode to adventure");

                                }

                            } else {
                                sender.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", sender);
                                }

                            }

                        } else {
                            sender.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", sender);
                            }

                        }


                    } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp")) {
                        if (sender.hasPermission("basics.gamemode.spectator")) {
                            if (sender.hasPermission("basics.gamemode.other")) {
                                if (Bukkit.getPlayer(args[1]) == null) {
                                    sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! Specified player is not online. \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                                } else {
                                    Player target = Util.getPlayer(args[1]);
                                    sender.sendMessage(Config.gamemodeChangeOtherMessage.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", "spectator"));
                                    target.setGameMode(GameMode.SPECTATOR);
                                    Basics.staffAlert(sender,  "changed " + target.getName() + "'s gamemode to spectator");

                                }

                            } else {
                                sender.sendMessage(Config.noPermissionMessage);
                                if (Config.alertOnNoPermission) {
                                    Basics.permissionAlert("/gamemode", sender);
                                }

                            }

                        } else {
                            sender.sendMessage(Config.noPermissionMessage);
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/gamemode", sender);
                            }

                        }


                    }

                }else{
                    sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/gamemode [mode]\n - Change your current gamemode. \n/gamemode [mode] [player] \n - Change another players gamemode. ");

                }




            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> gamemodes = new ArrayList<String>();
        if(command.getName().equalsIgnoreCase("gamemode") && strings.length == 1){
            if(commandSender instanceof Player){
                Player p = (Player) commandSender;
                if(p.hasPermission("basics.gamemode.creative")){
                    gamemodes.add("creative");

                }
                if(p.hasPermission("basics.gamemode.survival")){
                    gamemodes.add("survival");

                }
                if(p.hasPermission("basics.gamemode.spectator")){
                    gamemodes.add("spectator");

                }
                if(p.hasPermission("basics.gamemode.adventure")){
                    gamemodes.add("adventure");

                }

                return gamemodes;



            }else{
                return Arrays.asList("creative", "survival", "adventure", "spectator");
            }


        }
        if(command.getName().equalsIgnoreCase("gamemode") && strings.length == 2){
            if(commandSender instanceof Player){
                Player player = (Player)commandSender;
                if(player.hasPermission("basics.gamemode.other")){
                    List<String> list = new ArrayList<String>();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        list.add(p.getName());
                    }
                    return list;

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
        if(command.getName().equalsIgnoreCase("gamemode") && strings.length > 2){
            return new ArrayList<String>();
        }

        return null;

    }
}
