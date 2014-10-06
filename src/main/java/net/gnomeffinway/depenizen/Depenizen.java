package net.gnomeffinway.depenizen;

import net.aufdemrand.denizen.utilities.debugging.dB;
import net.gnomeffinway.depenizen.support.Supported;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Depenizen extends JavaPlugin {

    @Override
    public void onEnable() {
        checkPlugins();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("depenizen")) {
            sender.sendMessage(ChatColor.UNDERLINE + "Depenizen");
            sender.sendMessage(ChatColor.GRAY + "Developers: " + ChatColor.AQUA + "Morphan1" + ChatColor.GRAY + ", " + ChatColor.GREEN + "Jeebiss" + ChatColor.GRAY + ", and GnomeffinWay");
            sender.sendMessage(ChatColor.GRAY + "Current version: "+ ChatColor.GOLD + this.getDescription().getVersion());
            return true;
        }
        return false;
    }

    public void checkPlugins() {

        PluginManager pm = getServer().getPluginManager();

        if (pm.getPlugin("Denizen") != null) {
            depenizenLog("Denizen hooked");
        } else {
            getServer().getLogger().severe("[Depenizen] Denizen not found, disabling");
            getPluginLoader().disablePlugin(this);
            return;
        }

        Supported.setup(this, pm, getClassLoader());

    }

    public static void depenizenLog(String message) {
        dB.log(message);
    }
}
