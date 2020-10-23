package com.profiiqus.dailyrewardsgui.commands.subcommands;

import com.profiiqus.dailyrewardsgui.CraftConfig;
import com.profiiqus.dailyrewardsgui.commands.SubCommand;
import com.profiiqus.dailyrewardsgui.managers.GUIManager;
import com.profiiqus.dailyrewardsgui.managers.RewardManager;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Command handler for Reloading configuration
 * @author ProfiiQus
 */
public class Reload implements SubCommand {

    /**
     * Performs configuration reload, checks for permissions
     * @param sender The sender of the command
     * @param args The command args
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        FileConfiguration config = CraftConfig.getInstance().getConfig();
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission("dailyrewardsgui.reload")) {
                String message = config.getString("messages.not_enough_permissions");
                message = Formatter.colorize(message);
                player.sendMessage(message);
                return;
            }

            this.reload();
            String message = config.getString("messages.reloaded");
            message = Formatter.colorize(message);
            player.sendMessage(message);
            return;
        } else {
            this.reload();
        }
    }

    /**
     * Reloads all managers and configuration objects
     */
    private void reload() {
        GUIManager.getInstance().reload();
        RewardManager.getInstance().reload();
    }
}
