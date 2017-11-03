package nl.jessegeerts.publicplugins.broadcast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Plugin plugin;
    public static Plugin getPlugin(){
        return plugin;
    }

    public void onEnable(){
        plugin=this;
        initConfig();
    }

    public void onDisable(){
        plugin=null;
    }

    public boolean onCommand(CommandSender sender, Command cmd,String commandLabel, String[] args) {
        final FileConfiguration config = this.getConfig();
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (!sender.hasPermission(config.getString("Broadcast.permission"))) {
                sender.sendMessage(config.getString("Broadcast.nopermission").replace("&","§"));
                return true;
            }
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "You need to type something!");
                    sender.sendMessage(ChatColor.AQUA + "This plugin was made by Jesse Geerts : www.jessegeerts.nl");
                    return true;
                }

                String msg = "";
                String[] arrayOfString;
                int j = (arrayOfString = args).length;
                for (int i = 0; i < j; i++) {
                    String a = arrayOfString[i];
                    msg = msg + " " + a;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(msg);
                }
for(Player p : Bukkit.getOnlinePlayers()){
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Broadcast.prefix") +msg.replace("%player%", p.getName().toString())));
}
return true;


        }
            if(cmd.getName().equalsIgnoreCase("setbroadcastprefix")){
                if(sender.hasPermission(config.getString("SetBroadCastPrefix.permission"))){
                    String prefix ="";
                    for (String arg : args) {
                        prefix = prefix + " " + arg;
                    }
                    if (prefix.startsWith(" ")) prefix = prefix.replaceFirst(" ", "");
                    if(args.length==0){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SetBroadCastPrefix.noargs")));
                        return true;
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getString("SetBroadCastPrefix.newprefix").replace("%prefix%", prefix)));
                        config.set("Broadcast.prefix", prefix);
                        this.saveConfig();
                        this.reloadConfig();
return true;


                }else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Broadcast.nopermission")));
                    return true;
                }
            }
            if(cmd.getName().equalsIgnoreCase("reloadbroadcast")){
                reloadConfig();
                sender.sendMessage("The config has been reloaded. Check the console to make sure if there aren't any errors of this plugin.");
                return false;
            }

        return true;
    }
    private void initConfig(){
        getConfig().addDefault("Broadcast.permission", "broadcast.broadcast");
        getConfig().addDefault("Broadcast.nopermission", "&4&lFOUT: &cJe bent hiervoor niet gemachtigd.");
        getConfig().addDefault("Broadcast.prefix", "&6&lPREFIX:");
        getConfig().addDefault("Broadcast.noargs", "&4&lFOUT: Gebruik /broadcast JE BERICHT");
        getConfig().addDefault("SetBroadCastPrefix.noargs", "&4&lFOUT: Gebruik /setbroadcast PREFIX");
        getConfig().addDefault("SetBroadCastPrefix.permission", "setbroadcastprefix.allow");
        getConfig().addDefault("SetBroadCastPrefix.newprefix", "&a&lNieuwe prefix is ingesteld naar: %prefix%");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
