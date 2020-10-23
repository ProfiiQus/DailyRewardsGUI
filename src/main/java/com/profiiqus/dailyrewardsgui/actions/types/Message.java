package com.profiiqus.dailyrewardsgui.actions.types;

import com.profiiqus.dailyrewardsgui.actions.Action;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import org.bukkit.entity.Player;

public class Message implements Action {

    @Override
    public void execute(Player player, String args) {
        player.sendMessage(Formatter.colorize(args));
    }
}
