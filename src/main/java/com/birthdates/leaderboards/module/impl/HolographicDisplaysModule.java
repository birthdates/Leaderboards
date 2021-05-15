package com.birthdates.leaderboards.module.impl;

import com.birthdates.leaderboards.module.LeaderboardModule;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.placeholder.PlaceholderReplacer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

public class HolographicDisplaysModule implements LeaderboardModule<Object> {

    @Setter
    @Getter
    private String[] placeholderNames;

    private Object[] data;

    public HolographicDisplaysModule(Plugin owner, String[] placeholderNames) {
        this(owner, placeholderNames, 300); //5 minutes
    }

    public HolographicDisplaysModule(Plugin owner, String[] placeholderNames, float updateTime) {
        this.placeholderNames = placeholderNames;

        for (int i = 0; i < placeholderNames.length; i++) {
            String placeholderName = placeholderNames[i];
            HologramsAPI.registerPlaceholder(owner, placeholderName, updateTime, getReplacer(i));
        }

    }

    private PlaceholderReplacer getReplacer(int index) {
        return () -> {
            if(data.length <= index) return "N/A";
            return data[index].toString();
        };
    }

    @Override
    public void handleUpdate(Object[] data) {
        this.data = data;
    }
}
