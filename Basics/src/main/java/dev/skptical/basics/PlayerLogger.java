package dev.skptical.basics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerLogger {


    private static boolean ipExists(Player p){
        if(Basics.instance().files.getData().get(p.getUniqueId() + "." + "ip") == null){
            return false;
        }
        return true;
    }

    private static boolean nameExists(Player p){
        if(Basics.instance().files.getData().get(p.getUniqueId() + "." + "inGameName") == null){
            return false;
        }
        return true;
    }




    private static void set(Player player, String arg1, Object arg2){
        Basics.instance().files.getData().set(player.getUniqueId() + "." + arg1, arg2);

    }

    public static void updatePlayer(Player p)
    {
        String gamemode = p.getGameMode().toString().toLowerCase();
        String location = p.getLocation().getBlockX() + ", "  + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
        set(p, "gamemode", gamemode.substring(0, 1).toUpperCase() + gamemode.substring(1));
        set(p, "isOp", p.isOp());
        set(p,"location", location);
        set(p,"world", p.getWorld().getName());
        set(p,"health", p.getHealth());
        set(p,"location", location);
        set(p,"exp", p.getTotalExperience());
        set(p,"level", Experience.getIntLevelFromExp(Experience.getExp(p)));
        set(p,"hunger", p.getFoodLevel());
        set(p,"nick", p.getDisplayName());
        Basics.instance().files.saveData();

    }

    public static void playerLeave(Player p){
        updatePlayer(p);
    }

    public static void playerJoin(Player p){
        if(nameExists(p)){
            if(!Basics.instance().files.getData().getString((p.getUniqueId() + "." + "inGameName")).equalsIgnoreCase(p.getName())){
                Basics.instance().files.getData().set(p.getUniqueId() + "." + "inGameName", p.getName());
                Basics.instance().files.saveData();
            }


        }else{
            Basics.instance().files.getData().set(p.getUniqueId() + "." + "inGameName", p.getName());
            Basics.instance().files.saveData();

        }

        if(ipExists(p)){
            if(!Basics.instance().files.getData().getString((p.getUniqueId() + "." + "ip")).equalsIgnoreCase(p.getAddress().getAddress().getHostAddress())){
                Basics.instance().files.getData().set(p.getUniqueId() + "." + "ip", p.getAddress().getAddress().getHostAddress());
                Basics.instance().files.saveData();
            }


        }else{
            Basics.instance().files.getData().set(p.getUniqueId() + "." + "ip", p.getAddress().getAddress().getHostAddress());
            Basics.instance().files.saveData();

        }
        Basics.instance().files.saveData();
    }








}
