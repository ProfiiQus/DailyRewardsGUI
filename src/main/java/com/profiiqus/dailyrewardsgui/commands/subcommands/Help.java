package com.profiiqus.dailyrewardsgui.commands.subcommands;

import com.profiiqus.dailyrewardsgui.CraftConfig;
import com.profiiqus.dailyrewardsgui.commands.SubCommand;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Help implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        FileConfiguration config = CraftConfig.getInstance().getConfig();
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission("dailyrewardsgui.help")) {
                String message = config.getString("messages.not_enough_permissions");
                message = Formatter.colorize(message);
                player.sendMessage(message);
                return;
            }

            List<String> helpMessages = (ArrayList<String>) config.get("messages.help");
            helpMessages = Formatter.colorize(helpMessages);
            for(String msg: helpMessages) {
                player.sendMessage(msg);
            }
        }

        // todo: print help
    }
}
