package com.profiiqus.dailyrewardsgui.actions.types;

import com.profiiqus.dailyrewardsgui.actions.Action;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Broadcast implements Action {

    @Override
    public void execute(Player player, String args) {
        Bukkit.getServer().broadcastMessage(Formatter.colorize(args));
    }
}
