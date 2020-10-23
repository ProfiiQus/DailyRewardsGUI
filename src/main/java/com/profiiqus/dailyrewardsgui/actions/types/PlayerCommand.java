package com.profiiqus.dailyrewardsgui.actions.types;

import com.profiiqus.dailyrewardsgui.actions.Action;
import org.bukkit.entity.Player;

public class PlayerCommand implements Action {

    @Override
    public void execute(Player player, String args) {
        player.performCommand(args);
    }
}
