package com.profiiqus.dailyrewardsgui.actions.types;

import com.profiiqus.dailyrewardsgui.actions.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerCommand implements Action {

    @Override
    public void execute(Player player, String args) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), args);
    }
}
