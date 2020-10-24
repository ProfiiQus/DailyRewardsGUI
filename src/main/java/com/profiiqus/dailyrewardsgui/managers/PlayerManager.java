package com.profiiqus.dailyrewardsgui.managers;

import com.profiiqus.dailyrewardsgui.CraftConfig;
import com.profiiqus.dailyrewardsgui.DailyRewardsGUI;
import com.profiiqus.dailyrewardsgui.object.LocalPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private static PlayerManager instance;
    private DailyRewardsGUI plugin;
    private File file;
    private FileConfiguration fileConfig;
    private HashMap<UUID, LocalPlayer> playerData;

    private PlayerManager() {
        this.plugin = DailyRewardsGUI.getPlugin();
        this.file = new File(DailyRewardsGUI.getPlugin().getDataFolder(), "data.yml");
        this.tryCreateFile();
        this.fileConfig = YamlConfiguration.loadConfiguration(file);
        this.scheduleAutomaticSaves();
        this.playerData = this.loadOnlinePlayers();
    }

    public static PlayerManager getInstance() {
        if(instance == null) instance = new PlayerManager();
        return instance;
    }

    private void tryCreateFile() {
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            // -
        }
    }

    private void saveFile(boolean async) {
        if(async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        fileConfig.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(this.plugin);
        } else {
            try {
                fileConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LocalPlayer getPlayer(UUID uniqueId) {
        return this.playerData.get(uniqueId);
    }

    private HashMap<UUID, LocalPlayer> loadOnlinePlayers() {
        HashMap<UUID, LocalPlayer> playerData = new HashMap<>();
        UUID uniqueId;
        for(Player p: Bukkit.getOnlinePlayers()) {
            uniqueId = p.getUniqueId();
            if(this.hasData(p)) {
                HashMap<String, Long> rewardData = new HashMap<>();
                for(String key: fileConfig.getConfigurationSection(uniqueId.toString()).getKeys(false)) {
                    rewardData.put(key, fileConfig.getLong(uniqueId.toString() + "." + key));
                }
                playerData.put(uniqueId, new LocalPlayer(uniqueId, rewardData));
            } else {
                playerData.put(uniqueId, new LocalPlayer(uniqueId));
            }
        }
        return playerData;
    }

    private void scheduleAutomaticSaves() {
        FileConfiguration craftConfig = CraftConfig.getInstance().getConfig();
        int delay = craftConfig.getInt("auto_save") * 1200;
        new BukkitRunnable() {
            @Override
            public void run() {
                saveOnlinePlayers(true);
            }
        }.runTaskTimer(this.plugin, delay, delay);
    }

    public void saveOnlinePlayers(boolean asyncSave) {
        for(Player p: Bukkit.getOnlinePlayers()) {
            UUID uniqueId = p.getUniqueId();
            for(Map.Entry<String, Long> entry: getPlayer(uniqueId).getRewardsData().entrySet()) {
                fileConfig.set(uniqueId.toString() + "." + entry.getKey(), entry.getValue());
            }
        }
        this.saveFile(asyncSave);
    }

    public void loadPlayer(Player player) {
        UUID uniqueId = player.getUniqueId();
        if(hasData(player)) {
            HashMap<String, Long> rewardData = new HashMap<>();
            for(String key: fileConfig.getConfigurationSection(uniqueId.toString()).getKeys(false)) {
                rewardData.put(key, fileConfig.getLong(uniqueId.toString() + "." + key));
            }
            playerData.put(uniqueId, new LocalPlayer(uniqueId, rewardData));
        } else {
            playerData.put(uniqueId, new LocalPlayer(uniqueId));
        }
    }

    public void savePlayer(Player player) {
        UUID uniqueId = player.getUniqueId();
        new BukkitRunnable() {
            @Override
            public void run() {
                // problem is here
                for(Map.Entry<String, Long> entry: getPlayer(uniqueId).getRewardsData().entrySet()) {
                    fileConfig.set(uniqueId.toString() + "." + entry.getKey(), entry.getValue());
                }
                saveFile(true);
                playerData.remove(uniqueId);
            }
        }.runTaskAsynchronously(this.plugin);
    }

    private boolean hasData(Player player) {
        return this.fileConfig.isSet(player.getUniqueId().toString());
    }
}
