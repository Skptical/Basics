package dev.skptical.basics.Commands;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Basics;
import dev.skptical.basics.Experience;
import dev.skptical.basics.PlayerLogger;
import dev.skptical.basics.Storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class WhoIs implements CommandExecutor, TabExecutor {

    private Basics instance;

    public WhoIs(Basics plugin){
        this.instance = plugin;
        Util.registerCommand("whois", this.instance, this, this);
    }
    //pinfo playerinfo







    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("whois") || label.equalsIgnoreCase("playerinfo") || label.equalsIgnoreCase("pinfo")) {
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(p.hasPermission("basics.whois")){
                    if(args.length == 0){
                        sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/whois [player]\n - See basic information about a specific player.");
                        return true;
                    }
                    if(args.length >= 1){
                        Collection<String> items = Basics.instance().files.getData().getConfigurationSection("").getKeys(false);
                        boolean found = false;
                        String foundUUID = "";
                        for(String item : items){
                            if(Basics.instance().files.getData().getString(item + "." + "inGameName").equalsIgnoreCase(args[0])){
                                found = true;
                                foundUUID = item;
                            }
                            if(found){
                                break;
                            }
                        }
                        if(!found){
                            p.sendMessage(Config.whoIsNotFoundMessage.replaceAll("%player%", args[0]));

                            return true;
                        }else{

                            if(Util.getPlayer(args[0]) != null){
                                PlayerLogger.updatePlayer(Util.getPlayer(args[0]));

                            }

                            String message = Util.replaceWhoIsMessage(Config.whoisMessage, foundUUID);
                            p.sendMessage(message);

                        }










                    }



                }else{
                    p.sendMessage(Util.t(Config.noPermissionMessage));
                    Basics.permissionAlert("/whois", p);
                }

            }else{
                if(args.length == 0){
                    sender.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/whois [player]\n - See basic information about a specific player.");
                    return true;
                }
                if(args.length >= 1){
                    Collection<String> items = Basics.instance().files.getData().getConfigurationSection("").getKeys(false);
                    boolean found = false;
                    String foundUUID = "";
                    for(String item : items){
                        if(Basics.instance().files.getData().getString(item + "." + "inGameName").equalsIgnoreCase(args[0])){
                            found = true;
                            foundUUID = item;
                        }
                        if(found){
                            break;
                        }
                    }
                    if(!found){
                        sender.sendMessage(Config.whoIsNotFoundMessage.replaceAll("%player%", args[0]));

                        return true;
                    }else{
                        if(Util.getPlayer(args[0]) != null){
                            PlayerLogger.updatePlayer(Util.getPlayer(args[0]));

                        }
                        String message = Util.replaceWhoIsMessage(Config.whoisMessage, foundUUID);
                        sender.sendMessage(message);

                    }


                    // TODO: ADD CONSOLE SUPPORT







                }





                // Console

            }
        }





        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("whois")){
            if(sender.hasPermission("basics.whois")){
                List<String> whois = new ArrayList<>();
                if(args.length == 1){
                    if(sender.hasPermission("basics.smite.others")){
                        for(Player p: Bukkit.getOnlinePlayers()){
                            whois.add(p.getName());
                        }

                        Collection<String> items = Basics.instance().files.getData().getConfigurationSection("").getKeys(false);
                        for(String item : items){
                            String name = Basics.instance().files.getData().getString(item + "." + "inGameName");

                            if(!whois.contains(name)){
                                whois.add(name);
                            }

                        }

                        return whois;
                    }
                }
                return new ArrayList<String>();
            }
            return new ArrayList<String>();
        }
        return null;
    }
}
