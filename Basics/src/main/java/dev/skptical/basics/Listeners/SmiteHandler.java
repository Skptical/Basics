package dev.skptical.basics.Listeners;

import dev.skptical.basics.Commands.Smite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.LIGHTNING;


public class SmiteHandler implements Listener {


    @EventHandler
    public void lightningDamageEvent(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

            if(e.getCause() == LIGHTNING){

                if(Smite.lightningPlayers.containsKey(p)){
                    e.setDamage(Smite.lightningPlayers.get(p));
                    Smite.lightningPlayers.remove(p);
                }
            }
        }


    }



}
