package com.profiiqus.dailyrewardsgui.commands.rewards;

import com.profiiqus.dailyrewardsgui.commands.SubCommand;
import com.profiiqus.dailyrewardsgui.commands.rewards.subcommands.GUI;
import com.profiiqus.dailyrewardsgui.commands.rewards.subcommands.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class RewardsCommand implements CommandExecutor {

    private HashMap<String, SubCommand> subCommands;

    public RewardsCommand() {
        this.subCommands = new HashMap<String, SubCommand>() {
            {
                put("reload", new Reload());
                put("gui", new GUI());
            }
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            this.subCommands.get("gui").execute(sender, args);
            return true;
        }

        String cmd = args[0].toLowerCase();
        if(this.subCommands.containsKey(cmd)) {
            this.subCommands.get(cmd).execute(sender, args);
            return true;
        }

        this.subCommands.get("gui").execute(sender, args);
        return false;
    }
}
