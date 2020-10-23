package com.profiiqus.dailyrewardsgui.object;

import com.profiiqus.dailyrewardsgui.managers.RewardManager;

import java.util.HashMap;
import java.util.UUID;

public class LocalPlayer {

    private UUID uniqueId;
    private HashMap<String, Long> rewardsData;

    public LocalPlayer(UUID uniqueId, HashMap<String, Long> rewardsData) {
        this.uniqueId = uniqueId;
        this.rewardsData = rewardsData;
    }

    public LocalPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.rewardsData = new HashMap<>();
    }

    public long getRemainingMillis(String rewardID) {
        RewardManager rewardMan = RewardManager.getInstance();
        Reward reward = rewardMan.getReward(rewardID);

        long lastClaim = this.rewardsData.get(rewardID);
        long currentMillis = System.currentTimeMillis();

        return (lastClaim + reward.getCoolDownSeconds()*1000) - currentMillis;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public boolean canClaim(String rewardID) {
        if(!this.rewardsData.containsKey(rewardID)) {
            return true;
        }

        RewardManager rewardMan = RewardManager.getInstance();
        if(rewardMan.getReward(rewardID) == null) {
            return false;
        }

        Reward reward = rewardMan.getReward(rewardID);
        long lastClaim = this.rewardsData.get(rewardID);
        long currentMillis = System.currentTimeMillis();

        return (lastClaim + (reward.getCoolDownSeconds()*1000) <= currentMillis);
    }

    public void claim(String reward) {
        if(this.rewardsData.containsKey(reward)) {
            this.rewardsData.remove(reward);
        }
        this.rewardsData.put(reward, System.currentTimeMillis());
    }

    public HashMap<String, Long> getRewardsData() {
        return this.rewardsData;
    }
}