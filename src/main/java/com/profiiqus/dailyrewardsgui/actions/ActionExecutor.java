package com.profiiqus.dailyrewardsgui.actions;

import com.profiiqus.dailyrewardsgui.actions.types.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class ActionExecutor {

    private static ActionExecutor instance;
    private HashMap<String, Action> actionMap;

    private ActionExecutor() {
        this.actionMap = new HashMap<String, Action>() {
            {
                put("[actionbar]", new Actionbar());
                put("[broadcast]", new Broadcast());
                put("[message]", new Message());
                put("[player-command]", new PlayerCommand());
                put("[server-command]", new ServerCommand());
                put("[sound]", new Sound());
                put("[title]", new Title());
            }
        };
    }

    public static ActionExecutor getInstance() {
        if(instance == null) instance = new ActionExecutor();
        return instance;
    }

    public void executeActionList(Player player, List<String> actionList) {
        String[] array;
        String actionName, actionArgs;
        for(String action: actionList) {
            array = action.split(" ", 2);
            actionName = array[0];
            actionArgs = array[1];
            actionArgs = actionArgs.replace("{PLAYER}", player.getName());

            if(this.actionMap.containsKey(actionName)) {
                this.actionMap.get(actionName).execute(player, actionArgs);
            }
        }
    }
}
