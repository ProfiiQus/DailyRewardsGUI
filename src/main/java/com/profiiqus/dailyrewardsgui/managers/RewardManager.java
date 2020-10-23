package com.profiiqus.dailyrewardsgui.managers;

import com.profiiqus.dailyrewardsgui.CraftConfig;
import com.profiiqus.dailyrewardsgui.object.Reward;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import com.profiiqus.dailyrewardsgui.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RewardManager {

    private static RewardManager instance;
    private List<Reward> rewards;

    private RewardManager() {
        this.reload();
    }

    public void reload() {
        this.rewards = this.parseRewards();
    }

    private List<Reward> parseRewards() {
        FileConfiguration config = CraftConfig.getInstance().getConfig();
        List<Reward> rewards = new ArrayList<>();
        String path;
        for(String key: config.getConfigurationSection("rewards").getKeys(false)) {
            path = "rewards." + key + ".";
            ItemStack claimedItem = new ItemStack(Material.valueOf(config.getString(path + "gui.claimed.material")), 1);
            ItemMeta claimedMeta = claimedItem.getItemMeta();
            claimedMeta.setDisplayName(Formatter.colorize(config.getString(path + "gui.claimed.display_name")));
            List<String> claimedLore = new ArrayList<>(Arrays.asList(claimedMeta.getDisplayName()));
            claimedMeta.setLore(Formatter.colorize(Utils.expandList(claimedLore, (ArrayList<String>) config.get(path + "gui.claimed.lore"))));
            claimedItem.setItemMeta(claimedMeta);

            ItemStack unclaimedItem = new ItemStack(Material.valueOf(config.getString(path + "gui.unclaimed.material")), 1);
            ItemMeta unclaimedMeta = unclaimedItem.getItemMeta();
            unclaimedMeta.setDisplayName(Formatter.colorize(config.getString(path + "gui.unclaimed.display_name")));
            List<String> unclaimedLore = new ArrayList<>(Arrays.asList(unclaimedMeta.getDisplayName()));
            unclaimedMeta.setLore(Formatter.colorize(Utils.expandList(unclaimedLore, (ArrayList<String>) config.get(path + "gui.unclaimed.lore"))));
            unclaimedItem.setItemMeta(unclaimedMeta);

            Reward reward = new Reward(key,
                    config.getString(path + "required_permission"),
                    config.getInt(path + "cooldown"),
                    (ArrayList<String>) config.get(path + "action_list"),
                    claimedItem,
                    unclaimedItem);

            Utils.log("Loaded reward '" + reward.getID() + "'.");
            rewards.add(reward);
        }

        return rewards;
    }

    public static RewardManager getInstance() {
        if(instance == null) instance = new RewardManager();
        return instance;
    }

    public List<Reward> getRewards(Player player) {
        List<Reward> eligibleRewards = new ArrayList<>();
        for(Reward reward: this.rewards) {
            if(player.hasPermission(reward.getRequiredPermission())) {
                eligibleRewards.add(reward);
            }
        }
        return eligibleRewards;
    }

    public Reward getReward(String id) {
        for(Reward reward: this.rewards) {
            if(reward.getID().equals(id)) {
                return reward;
            }
        }
        return null;
    }
}
