package com.profiiqus.dailyrewardsgui;

import com.profiiqus.dailyrewardsgui.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CraftConfig {

    private static CraftConfig instance;
    private DailyRewardsGUI plugin;
    private File file;
    private FileConfiguration config;

    private CraftConfig() {
        this.plugin = DailyRewardsGUI.getPlugin();
        this.reload();
    }

    public void reload() {
        Utils.log("Loading configuration...");
        this.file = new File(this.plugin.getDataFolder(), "config.yml");

        if(!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            this.plugin.saveResource("config.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
        Utils.log("Configuration successfully loaded!");
    }

    public static CraftConfig getInstance() {
        if(instance == null) instance = new CraftConfig();
        return instance;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
