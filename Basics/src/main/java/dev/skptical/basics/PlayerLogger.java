package dev.skptical.basics;

import dev.skptical.basics.Commands.CommandSpy;
import dev.skptical.basics.Commands.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerLogger {

    // TODO: ADD ALL IPS THE CLIENT HAS LOGGED IN WITH TO A SECTION IN THE DATA.YML, AND THEN HAVE A NEW LOCATION FOR THEIR CURRENT/MOST USED IP

    /**
     * Check if players ip exists in the data.yml
     * @param p Player to check.
     * @return Returns true/false.
     **/
    private static boolean ipExists(Player p){
        if(Basics.instance().files.getData().get(p.getUniqueId() + "." + "ip") == null){
            return false;
        }
        return true;
    }


    /**
     * Check if players name exists in the data.yml
     * @param p Player to check.
     * @return Returns true/false.
     **/
    private static boolean nameExists(Player p){
        if(Basics.instance().files.getData().get(p.getUniqueId() + "." + "inGameName") == null){
            return false;
        }
        return true;
    }



    /**
     * Private method for setting data tags inside data.yml.
     * @param player The player to set.
     * @param arg1 Destination
     * @param arg2 Value
     **/
    private static void set(Player player, String arg1, Object arg2){
        Basics.instance().files.getData().set(player.getUniqueId() + "." + arg1, arg2);

    }
    /**
     * Updates a players values in the data.yml file.
     * @param p The player to update
     **/
    public static void updatePlayer(Player p)
    {
        // Getting location and gamemode in string formats
        String gamemode = p.getGameMode().toString().toLowerCase();
        String location = p.getLocation().getBlockX() + ", "  + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
        // Gamemode data
        set(p, "gamemode", gamemode.substring(0, 1).toUpperCase() + gamemode.substring(1));
        // Op data
        set(p, "isOp", p.isOp());
        // Location data
        set(p,"location", location);
        // World Data
        set(p,"world", p.getWorld().getName());
        // Health data
        set(p,"health", p.getHealth());
        // EXP Data
        set(p,"exp", p.getTotalExperience());
        // Levels data
        set(p,"level", Experience.getIntLevelFromExp(Experience.getExp(p)));
        // Hunger Data
        set(p,"hunger", p.getFoodLevel());
        // Nick Data
        set(p,"nick", p.getDisplayName());
        // Cmd Spy
        set(p, "commandSpyEnabled", CommandSpy.toggledCmdPlayers.contains(p));
        // Social Spy
        set(p, "socialSpyEnabled", Message.socialSpy.contains(p));
        // Save data
        Basics.instance().files.saveData();

    }

    public static void playerLeave(Player p){
        updatePlayer(p);
    }

    public static void playerJoin(Player p){
        updatePlayer(p);
        // If their name is in the data file
        if(nameExists(p)){
            // If their name is in the data file & their nickname doesnt match the one stored
            if(!Basics.instance().files.getData().getString((p.getUniqueId() + "." + "inGameName")).equalsIgnoreCase(p.getName())){
                // Update the nickname in the data folder
                Basics.instance().files.getData().set(p.getUniqueId() + "." + "inGameName", p.getName());
                Basics.instance().files.saveData();
            }


        }else{
            // Name doesnt exist in the data file so we add it
            Basics.instance().files.getData().set(p.getUniqueId() + "." + "inGameName", p.getName());
            Basics.instance().files.saveData();

        }
        // If their ip exists in the data file
        if(ipExists(p)){
            // If their ip exists but doesnt match their current ip
            if(!Basics.instance().files.getData().getString((p.getUniqueId() + "." + "ip")).equalsIgnoreCase(p.getAddress().getAddress().getHostAddress())){
                // Update their ip
                Basics.instance().files.getData().set(p.getUniqueId() + "." + "ip", p.getAddress().getAddress().getHostAddress());
                Basics.instance().files.saveData();
            }


        }else{
            // If it doesnt exist just update their IP
            Basics.instance().files.getData().set(p.getUniqueId() + "." + "ip", p.getAddress().getAddress().getHostAddress());
            Basics.instance().files.saveData();

        }
        Basics.instance().files.saveData();
    }








}
