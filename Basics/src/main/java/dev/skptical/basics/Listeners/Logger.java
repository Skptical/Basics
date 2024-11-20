package dev.skptical.basics.Listeners;

import dev.skptical.basics.Basics;
import dev.skptical.basics.Commands.CommandSpy;
import dev.skptical.basics.Experience;
import dev.skptical.basics.PlayerLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class Logger implements Listener {

    private boolean hasCmdSpy(Player p){
        UUID uuid = p.getUniqueId();
        boolean isenabled = false;
        isenabled = Basics.instance().files.getData().getBoolean(uuid + "." + "commandSpyEnabled");
        return isenabled;
    }

    private boolean hasSocialSpy(Player p){
        UUID uuid = p.getUniqueId();
        boolean isenabled = false;
        isenabled = Basics.instance().files.getData().getBoolean(uuid + "." + "socialSpyEnabled");
        return isenabled;
    }

    public void setWhoIsItems(Player p, boolean isLeaving){
        if(isLeaving){
            PlayerLogger.playerLeave(p);
        }else{
            PlayerLogger.playerJoin(p);
        }

                // Is flying
                // Playtime
                // Balance
                // God Mode





    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        setWhoIsItems(p, true);
        Basics.instance().files.saveData();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        setWhoIsItems(p, false);
        if(hasCmdSpy(p) && !CommandSpy.toggledCmdPlayers.contains(p)){

            CommandSpy.toggledCmdPlayers.add(p);
        }

        Basics.instance().files.saveData();




    }




}
