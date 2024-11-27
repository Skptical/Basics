package dev.skptical.basics.Listeners;

import dev.skptical.basics.API.Util;
import dev.skptical.basics.Commands.Message;
import dev.skptical.basics.Storage.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(Message.toggledReply.contains(p)){
            e.setCancelled(true);
            if(Message.lastMessaged.containsKey(p)){
                if(Util.isOnline(Message.lastMessaged.get(p))){
                    Message.lastMessaged.put(Util.getPlayer(Message.lastMessaged.get(p)), p.getName());
                    String message = e.getMessage();
                    message = message.trim();
                    Player target = Util.getPlayer(Message.lastMessaged.get(p));

                    if(Message.toggledPm.contains(target)){
                        p.sendMessage(Config.pmDisabledOtherMessage);
                        return;
                    }
                    for(Player player : Message.socialSpy){
                        if(!player.getUniqueId().equals(p.getUniqueId())){
                            player.sendMessage(Config.socialSpyMessage.replaceAll("%player%",p.getName()).replaceAll("%target%", target.getName())
                                    .replaceAll("%message%", message));
                        }

                    }




                    p.sendMessage(Config.messageSent.replaceAll("%message%", message).replaceAll("%target%", target.getName()));
                    target.sendMessage(Config.messageReceived.replaceAll("%message%", message).replaceAll("%player%", p.getName()));

                }else{
                    p.sendMessage(Config.noReply);
                }
            }else{
                p.sendMessage(Config.noReply);;
            }
        }

    }

}
