package com.profiiqus.dailyrewardsgui.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    void execute(CommandSender sender, String[] args);
}
