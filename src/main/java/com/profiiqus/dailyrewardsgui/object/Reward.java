package com.profiiqus.dailyrewardsgui.object;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Reward {

    private String id, viewPermission, claimPermission;
    private int coolDownSeconds;
    private List<String> actionList;
    private ItemStack claimed, unclaimed, view;

    public Reward(String id, String viewPermission, String claimPermission, int coolDownSeconds, List<String> actionList, ItemStack claimed, ItemStack unclaimed, ItemStack view) {
        this.id = id;
        this.viewPermission = viewPermission;
        this.claimPermission = claimPermission;
        this.coolDownSeconds = coolDownSeconds;
        this.actionList = actionList;
        this.claimed = claimed;
        this.unclaimed = unclaimed;
        this.view = view;
    }

    public String getID() {
        return this.id;
    }

    public String getClaimPermission() {
        return this.claimPermission;
    }

    public String getViewPermission() { return this.viewPermission; }

    public ItemStack getClaimedItem() {
        return this.claimed;
    }

    public ItemStack getUnclaimedItem() {
        return this.unclaimed;
    }

    public ItemStack getViewItem() { return this.view; }

    public List<String> getActionList() {
        return this.actionList;
    }

    public int getCoolDownSeconds() {
        return this.coolDownSeconds;
    }
}
