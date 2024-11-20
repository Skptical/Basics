package dev.skptical.basics.Commands;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Basics;
import dev.skptical.basics.Storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandSpy implements TabExecutor, CommandExecutor {

    private static Basics plugin;

    public CommandSpy(Basics instance){
        plugin = instance;
        Util.registerCommand("cmdspy", instance, this, this);
    }

    public static ArrayList<Player> toggledCmdPlayers = new ArrayList<Player>();
    private boolean hasCmdSpy(Player p){
        UUID uuid = p.getUniqueId();
        boolean isenabled = false;
        isenabled = Basics.instance().files.getData().getBoolean(uuid + "." + "commandSpyEnabled");
        return isenabled;
    }

    private boolean toggleCmdSpy(Player p){
        UUID uuid = p.getUniqueId();
        if(hasCmdSpy(p)){
            Basics.instance().files.getData().set(uuid + "." + "commandSpyEnabled", false);
            Basics.instance().files.saveData();
            toggledCmdPlayers.remove(p);
            return false;
        }else{
            Basics.instance().files.getData().set(uuid + "." + "commandSpyEnabled", true);
            Basics.instance().files.saveData();
            toggledCmdPlayers.add(p);
            return true;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("cmdspy") || s.equalsIgnoreCase("commandspy")){
            if(sender instanceof Player){
                Player p = (Player)sender;
                if(p.hasPermission("basics.commandspy")){
                    if(args.length > 1){
                        p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/commandspy [toggle]\n - Toggle commandspy.");
                        return true;
                    }
                    if(args.length == 0){
                        if(toggleCmdSpy(p)){
                            p.sendMessage(Config.commandSpyToggleOn.replaceAll("%player%", p.getName()));
                        }else{
                            p.sendMessage(Config.commandSpyToggleOff.replaceAll("%player%", p.getName()));
                        }
                    }
                    if(args.length == 1){
                        if(args[0].equalsIgnoreCase("on")){
                            p.sendMessage(Config.commandSpyToggleOn.replaceAll("%player%", p.getName()));
                            Basics.instance().files.getData().set(p.getUniqueId() + "." + "commandSpyEnabled", true);
                            Basics.instance().files.saveData();
                            if(!toggledCmdPlayers.contains(p)){
                               toggledCmdPlayers.add(p);
                            }
                            return true;
                        }
                        else if(args[0].equalsIgnoreCase("off")){
                            p.sendMessage(Config.commandSpyToggleOff.replaceAll("%player%", p.getName()));
                            Basics.instance().files.getData().set(p.getUniqueId() + "." + "commandSpyEnabled", false);
                            Basics.instance().files.saveData();
                            if(toggledCmdPlayers.contains(p)){
                                toggledCmdPlayers.remove(p);
                            }
                            return true;
                        }else{
                            p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/commandspy [toggle]\n - Toggle commandspy.");
                            return true;
                        }

                    }


                }else{
                    Basics.permissionAlert("/commandspy", p);
                    p.sendMessage(Util.t(Config.noPermissionMessage));
                }


            }else{
                sender.sendMessage(ChatColor.RED + "You cannot execute this command as console.");
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("cmdspy")){
            List<String> cmd = new ArrayList<>();
            if(strings.length == 1){
                if(commandSender.hasPermission("basics.commandspy")){
                    cmd.add("on");
                    cmd.add("off");
                    return cmd;
                }
            }else{
                return cmd;
            }
        }
        return null;
    }
}
