package com.profiiqus.dailyrewardsgui.actions.types;

import com.cryptomorin.xseries.messages.ActionBar;
import com.profiiqus.dailyrewardsgui.actions.Action;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import org.bukkit.entity.Player;

public class Actionbar implements Action {

    @Override
    public void execute(Player player, String args) {
        ActionBar.sendActionBar(player, Formatter.colorize(args));
    }
}
