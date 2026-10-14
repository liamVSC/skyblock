package online.skyblock;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (getCommand("skyblock") == null) {
            throw new IllegalStateException("Required command 'skyblock' is missing from plugin.yml");
        }
        getCommand("skyblock").setExecutor(this::handleSkyblockCommand);
        getLogger().info("Skyblock " + getPluginMeta().getVersion() + " enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Skyblock disabled.");
    }

    private boolean handleSkyblockCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.AQUA + "Skyblock " + getPluginMeta().getVersion());
            sender.sendMessage(ChatColor.GRAY + "Competitive Skyblock is starting up.");
            sender.sendMessage(ChatColor.YELLOW + "/" + label + " status" + ChatColor.GRAY + " - show plugin status");
            return true;
        }

        if (args[0].equalsIgnoreCase("status")) {
            sender.sendMessage(ChatColor.AQUA + "[Skyblock] " + ChatColor.GREEN + "Plugin is running.");
            sender.sendMessage(ChatColor.GRAY + "Gameplay systems are being built in staged, tested releases.");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /" + label + " help.");
        return true;
    }
}
