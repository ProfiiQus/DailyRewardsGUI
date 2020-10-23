package com.profiiqus.dailyrewardsgui.utils;

import com.profiiqus.dailyrewardsgui.DailyRewardsGUI;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void log(String message) {
        DailyRewardsGUI.getPlugin().getLogger().info(message);
    }

    public static void warning(String message) {
        DailyRewardsGUI.getPlugin().getLogger().warning(message);
    }

    public static String[] replace(String[] array, String target, String replacement) {
        String[] result = new String[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = array[i].replace(target, replacement);
        }
        return result;
    }

    public static List<String> expandList(List<String> source, List<String> toAdd) {
        List<String> result = source;
        for(String s: toAdd) {
            result.add(s);
        }
        return result;
    }
}
