package dev.skptical.basics.Storage;

import dev.skptical.basics.Basics;
import org.bukkit.ChatColor;

import static dev.skptical.basics.Basics.files;

public class Config {

    public static String invalidSyntaxMessage;
    public static String reloadMessage;
    public static String noPermissionMessage;
    public static String gamemodeChangeMessage, gamemodeChangeOtherMessage;
    public static String versionMessage;
    public static String staffNotifyMessage;

    public static String smiteMessage, smiteOtherMessage, commandSpy;
    public static String whoisMessage, whoIsNotFoundMessage, toggledReply;

    public static String permissionAlert, commandSpyToggleOff, commandSpyToggleOn;

    public static boolean alertOnNoPermission, commandSpyShowOwn;
    public static boolean staffAlerts;

    public static String noReply;

    public static String messageSent, messageReceived, pmDisabledOtherMessage, socialSpyMessage;

    public static int smiteDistance;


    protected static String trans(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static Object getC(String path){
        Object value = Basics.files.getConfig().get(path);
        if(value instanceof String){
            if(value == null){
                return value;
            }else{
                return trans((String) value);
            }

        }
        return value;
    }


    private static String getM(String path){
        String value = Basics.files.getMessages().getString(path);

        return trans(value);
    }


    public static void setupVars(){
        gamemodeChangeMessage = getM("gamemode-change");
        noPermissionMessage = getM("no-permission");
        invalidSyntaxMessage = getM("invalid-syntax");
        alertOnNoPermission = (boolean) getC("notification-denied-permission");
        permissionAlert = getM("permission-alert");
        toggledReply = getM("toggle-reply");
        versionMessage = getM("version");
        reloadMessage = getM("reload");
        noReply = getM("no-reply");
        staffNotifyMessage = getM("staff-notify");
        gamemodeChangeOtherMessage = getM("gamemode-change-other");
        smiteDistance = (int) getC("maximum-smite-distance");
        smiteMessage = getM("smite");
        smiteOtherMessage = getM("smite-other");
        staffAlerts = files.getConfig().getBoolean("staff-notifications");
        commandSpy = getM("command-spy");
        commandSpyToggleOn = getM("command-spy-toggle-on");
        commandSpyToggleOff = getM("command-spy-toggle-off");
        commandSpyShowOwn = (boolean) getC("command-spy-show-own");
        whoisMessage = getM("whois");
        whoIsNotFoundMessage = getM("whois-not-found");
        messageReceived = getM("message-received");
        messageSent = getM("message-sent");
        pmDisabledOtherMessage = getM("pm-disabled-other");
        socialSpyMessage = getM("social-spy-message");
    }


}
