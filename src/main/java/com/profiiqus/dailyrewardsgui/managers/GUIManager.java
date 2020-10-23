package com.profiiqus.dailyrewardsgui.managers;

import com.profiiqus.dailyrewardsgui.CraftConfig;
import com.profiiqus.dailyrewardsgui.DailyRewardsGUI;
import com.profiiqus.dailyrewardsgui.actions.ActionExecutor;
import com.profiiqus.dailyrewardsgui.object.LocalPlayer;
import com.profiiqus.dailyrewardsgui.object.Reward;
import com.profiiqus.dailyrewardsgui.utils.Formatter;
import com.profiiqus.dailyrewardsgui.utils.Utils;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GUIManager {

    private static GUIManager instance;
    private HashMap<Character, ItemStack> items;
    private HashMap<Character, List<Reward>> rewards;
    private String[] layout;
    private String displayName, claimedStatus, unclaimedStatus;

    private GUIManager() {
        this.reload();
    }

    public void reload() {
        FileConfiguration config = CraftConfig.getInstance().getConfig();
        this.displayName = config.getString("gui.display_name");
        List<String> layoutList = (ArrayList <String>) config.get("gui.layout");
        this.layout = Arrays.copyOf(layoutList.toArray(), layoutList.size(), String[].class);
        this.items = new HashMap<>();
        this.rewards = new HashMap<>();
        this.claimedStatus = config.getString("messages.claim_status.claimed");
        this.unclaimedStatus = config.getString("messages.claim_status.unclaimed");

        RewardManager rewardManager = RewardManager.getInstance();
        for(String key: config.getConfigurationSection("gui.items").getKeys(false)) {
            Material material = Material.valueOf(config.getString("gui.items." + key + ".material"));
            String displayName = config.getString("gui.items." + key + ".display_name");

            ItemStack item = new ItemStack(material, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Formatter.colorize(displayName));

            List<String> lore = Arrays.asList(displayName);

            if(config.isSet("gui.items." + key + ".lore")) {
                List<String> loreLines = (ArrayList<String>) config.get("gui.items." + key + ".lore");
                loreLines = Formatter.colorize(loreLines);
                lore.addAll(loreLines);
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
            this.items.put(key.toCharArray()[0], item);
        }

        RewardManager manager = RewardManager.getInstance();
        for(String key: config.getConfigurationSection("gui.rewards").getKeys(false)) {
            List<Reward> rewardList = new ArrayList<>();
            List<String> rewardIDs = (ArrayList<String>) config.get("gui.rewards." + key);
            for(String rewardID: rewardIDs) {
                if(manager.getReward(rewardID) != null) {
                    rewardList.add(manager.getReward(rewardID));
                } else {
                    Utils.warning("Reward '" + rewardID + "' defined in the GUI configuration does not exist.");
                }
            }
            this.rewards.put(key.toCharArray()[0], rewardList);
        }
    }

    public InventoryGui buildGUI(Player player, List<Reward> rewards) {
        InventoryGui gui = new InventoryGui(DailyRewardsGUI.getPlugin(), player, this.displayName, layout);

        for(Map.Entry<Character, ItemStack> entry: this.items.entrySet()) {
            gui.addElement(new StaticGuiElement(entry.getKey(),
                    entry.getValue(),
                    1,
                    click -> {return true;
                    }, entry.getValue().getItemMeta().getDisplayName()));
        }

        LocalPlayer localPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
        for(Map.Entry<Character, List<Reward>> entry: this.rewards.entrySet()) {
            for(Reward reward: entry.getValue()) {
                if(rewards.contains(reward)) {
                    String rewardID = reward.getID();

                    ItemStack unclaimedItem = reward.getUnclaimedItem();
                    gui.addElement(new DynamicGuiElement(entry.getKey(), () -> {
                        ItemStack itemStack;
                        ItemMeta itemMeta;
                        if(localPlayer.canClaim(rewardID)) {
                            itemStack = reward.getUnclaimedItem();
                            itemMeta = itemStack.getItemMeta();
                            List<String> loreList = itemMeta.getLore();
                            String[] loreArray = Arrays.copyOf(loreList.toArray(), loreList.size(), String[].class);
                            loreArray = Utils.replace(loreArray, "{CLAIM}", this.unclaimedStatus);
                            long millis = localPlayer.getRemainingMillis(rewardID);
                            return new StaticGuiElement(entry.getKey(), itemStack,
                                    click -> {
                                        localPlayer.claim(rewardID);
                                        ActionExecutor.getInstance().executeActionList(player, reward.getActionList());
                                        click.getGui().draw();
                                        return true;
                                    },
                                    loreArray);
                        } else {
                            itemStack = reward.getClaimedItem();
                            itemMeta = itemStack.getItemMeta();
                            List<String> loreList = itemMeta.getLore();
                            String[] loreArray = Arrays.copyOf(loreList.toArray(), loreList.size(), String[].class);
                            loreArray = Utils.replace(loreArray, "{CLAIM}", this.claimedStatus);
                            long millis = localPlayer.getRemainingMillis(rewardID);
                            loreArray = Utils.replace(loreArray, "{TIME_REMAINING}", String.format("%02dh %02dm %02ds",
                                    TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
                            loreList.add(0, itemMeta.getDisplayName());
                            return new StaticGuiElement(entry.getKey(), itemStack,
                                    click -> {
                                        click.getGui().draw();
                                        return true;
                                    },
                                    loreArray);
                        }
                    }));
                }
            }
        }
        gui.draw();
        return gui;
    }


    public static GUIManager getInstance() {
        if(instance == null) instance = new GUIManager();
        return instance;
    }
}
