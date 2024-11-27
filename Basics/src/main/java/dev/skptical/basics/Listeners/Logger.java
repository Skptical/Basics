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
    /**
     * Checks weather a specified player has CMDspy enabled.
     * @param p The player to check
     * @return Returns true/false
     **/
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

    // TODO: FINISH WHOISITEMS METHOD
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

    // Leave event
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        // Player
        Player p = e.getPlayer();
        // Set the whois data items
        setWhoIsItems(p, true);
        // Save file
        Basics.instance().files.saveData();
    }
    // Join Event
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        // Player
        Player p = e.getPlayer();
        // Set the whois data items
        setWhoIsItems(p, false);
        // If cmdspy is enabled in their file and they are not in the local registry
        if(hasCmdSpy(p) && !CommandSpy.toggledCmdPlayers.contains(p)){
            // add them
            CommandSpy.toggledCmdPlayers.add(p);
        }
        // Save data
        Basics.instance().files.saveData();




    }




}
