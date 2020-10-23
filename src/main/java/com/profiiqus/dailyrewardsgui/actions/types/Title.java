package com.profiiqus.dailyrewardsgui.actions.types;

import com.cryptomorin.xseries.messages.Titles;
import com.profiiqus.dailyrewardsgui.actions.Action;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import org.bukkit.entity.Player;

public class Title implements Action {

    @Override
    public void execute(Player player, String args) {
        String[] array = args.split(";");
        Titles.sendTitle(player, Formatter.colorize(array[0]), Formatter.colorize(array[1]));
    }
}
