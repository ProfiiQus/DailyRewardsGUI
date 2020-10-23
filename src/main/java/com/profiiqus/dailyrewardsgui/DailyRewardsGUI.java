package com.profiiqus.dailyrewardsgui;

import com.profiiqus.dailyrewardsgui.actions.ActionExecutor;
import com.profiiqus.dailyrewardsgui.commands.RewardsCommand;
import com.profiiqus.dailyrewardsgui.listeners.PlayerDataHandler;
import com.profiiqus.dailyrewardsgui.managers.GUIManager;
import com.profiiqus.dailyrewardsgui.managers.PlayerManager;
import com.profiiqus.dailyrewardsgui.managers.RewardManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DailyRewardsGUI extends JavaPlugin {

    public static DailyRewardsGUI plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getCommand("rewards").setExecutor(new RewardsCommand());
        this.getServer().getPluginManager().registerEvents(new PlayerDataHandler(), this);
        RewardManager.getInstance();
        GUIManager.getInstance();
        PlayerManager.getInstance();
        ActionExecutor.getInstance();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        PlayerManager.getInstance().saveOnlinePlayers(false);
    }

    public static DailyRewardsGUI getPlugin() {
        return plugin;
    }
}
