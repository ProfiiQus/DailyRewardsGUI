package com.profiiqus.dailyrewardsgui.actions.types;

import com.profiiqus.dailyrewardsgui.actions.Action;
import org.bukkit.entity.Player;

public class Sound implements Action {

    @Override
    public void execute(Player player, String args) {
        player.playSound(player.getLocation(), org.bukkit.Sound.valueOf(args), 4.0f, 1.0f);
    }
}
