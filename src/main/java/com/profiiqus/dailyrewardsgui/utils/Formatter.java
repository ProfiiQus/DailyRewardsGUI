package com.profiiqus.dailyrewardsgui.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Formatter {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colorize(List<String> lines) {
        List<String> coloredLines = new ArrayList<>();
        for(String line: lines) {
            coloredLines.add(colorize(line));
        }
        return coloredLines;
    }
}
