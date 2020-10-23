package com.profiiqus.dailyrewardsgui.object;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Reward {

    private String id, requiredPermission;
    private int coolDownSeconds;
    private ArrayList<String> actionList;
    private ItemStack claimed, unclaimed;

    public Reward(String id, String requiredPermission, int coolDownSeconds, ArrayList<String> actionList, ItemStack claimed, ItemStack unclaimed) {
        this.id = id;
        this.requiredPermission = requiredPermission;
        this.coolDownSeconds = coolDownSeconds;
        this.actionList = actionList;
        this.claimed = claimed;
        this.unclaimed = unclaimed;
    }

    public String getID() {
        return this.id;
    }

    public String getRequiredPermission() {
        return this.requiredPermission;
    }

    public ItemStack getClaimedItem() {
        return this.claimed;
    }

    public ItemStack getUnclaimedItem() {
        return this.unclaimed;
    }

    public int getCoolDownSeconds() {
        return this.coolDownSeconds;
    }
}
