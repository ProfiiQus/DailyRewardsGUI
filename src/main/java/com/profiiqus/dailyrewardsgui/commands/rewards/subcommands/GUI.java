package com.profiiqus.dailyrewardsgui.commands.rewards.subcommands;

import com.profiiqus.dailyrewardsgui.CraftConfig;
import com.profiiqus.dailyrewardsgui.commands.SubCommand;
import com.profiiqus.dailyrewardsgui.managers.GUIManager;
import com.profiiqus.dailyrewardsgui.managers.RewardManager;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import de.themoep.inventorygui.InventoryGui;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GUI implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        FileConfiguration config = CraftConfig.getInstance().getConfig();
        if(!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;
        if(!player.hasPermission("dailyrewardsgui.rewards")) {
            String message = config.getString("messages.not_enough_permissions");
            message = Formatter.colorize(message);
            player.sendMessage(message);
            return;
        }

        RewardManager rewards = RewardManager.getInstance();
        GUIManager guis = GUIManager.getInstance();

        InventoryGui gui = guis.buildGUI(player, rewards.getRewards());
        gui.show(player);
    }
}
