package dev.skptical.basics.Listeners;

import dev.skptical.basics.Commands.CommandSpy;
import dev.skptical.basics.Storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class PlayerCommandExecuteHandler implements Listener {

    @EventHandler
    public void playerCommandEvent(PlayerCommandPreprocessEvent e){

        if(CommandSpy.toggledCmdPlayers.isEmpty()) return; // If there is no players in the registry then cancel



        for(Player p: Bukkit.getOnlinePlayers()){
            if(CommandSpy.toggledCmdPlayers.contains(p)){
                if(Config.commandSpyShowOwn){
                    p.sendMessage(Config.commandSpy.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%command%", e.getMessage()));
                }else{
                    if(e.getPlayer() != p){
                        p.sendMessage(Config.commandSpy.replaceAll("%player%", e.getPlayer().getName()).replaceAll("%command%", e.getMessage()));
                    }

                }

            }

        }

    }


}
