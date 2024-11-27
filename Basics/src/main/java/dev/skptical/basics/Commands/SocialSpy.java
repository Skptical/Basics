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
import java.util.List;
import java.util.UUID;

public class SocialSpy implements TabExecutor, CommandExecutor {

    private static Basics plugin;

    public SocialSpy(Basics instance){
        plugin = instance;
        Util.registerCommand("socialspy", instance, this, this);
    }
    private boolean hasSocialSpy(Player p){
        UUID uuid = p.getUniqueId();
        boolean isenabled = false;
        isenabled = Basics.instance().files.getData().getBoolean(uuid + "." + "socialSpyEnabled");
        return isenabled;
    }

    private boolean toggleSocialSpy(Player p){
        UUID uuid = p.getUniqueId();
        if(hasSocialSpy(p)){
            Basics.instance().files.getData().set(uuid + "." + "socialSpyEnabled", false);
            Basics.instance().files.saveData();
            Message.socialSpy.remove(p);
            return false;
        }else{
            Basics.instance().files.getData().set(uuid + "." + "socialSpyEnabled", true);
            Basics.instance().files.saveData();
            Message.socialSpy.add(p);
            return true;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(s.equalsIgnoreCase("socialspy") || s.equalsIgnoreCase("messagespy")){
            if(sender instanceof Player){
                Player p = (Player)sender;
                if(p.hasPermission("basics.socialspy")){
                    if(args.length > 1){
                        p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/socialspy [toggle]\n - Toggle socialspy.");
                        return true;
                    }
                    if(args.length == 0){
                        if(toggleSocialSpy(p)){
                            p.sendMessage(Config.socialSpyToggleOn.replaceAll("%player%", p.getName()));
                        }else{
                            p.sendMessage(Config.socialSpyToggleOff.replaceAll("%player%", p.getName()));
                        }
                    }
                    if(args.length == 1){
                        if(args[0].equalsIgnoreCase("on")){
                            p.sendMessage(Config.socialSpyToggleOn.replaceAll("%player%", p.getName()));
                            Basics.instance().files.getData().set(p.getUniqueId() + "." + "socialSpyEnabled", true);
                            Basics.instance().files.saveData();
                            if(!Message.socialSpy.contains(p)){
                                Message.socialSpy.add(p);
                            }
                            return true;
                        }
                        else if(args[0].equalsIgnoreCase("off")){
                            p.sendMessage(Config.commandSpyToggleOff.replaceAll("%player%", p.getName()));
                            Basics.instance().files.getData().set(p.getUniqueId() + "." + "socialSpyEnabled", false);
                            Basics.instance().files.saveData();
                            if(Message.socialSpy.contains(p)){
                                Message.socialSpy.remove(p);
                            }
                            return true;
                        }else{
                            p.sendMessage(Config.invalidSyntaxMessage + "Incorrect syntax! \nPlease use one of the following: \n/socialspy [toggle]\n - Toggle socialspy.");
                            return true;
                        }

                    }


                }else{
                    Basics.permissionAlert("/socialspy", p);
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
        if(command.getName().equalsIgnoreCase("socialspy")){
            List<String> cmd = new ArrayList<>();
            if(strings.length == 1){
                if(commandSender.hasPermission("basics.socialspy")){
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
