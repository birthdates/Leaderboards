package com.birthdates.leaderboards.module.impl;

import com.birthdates.leaderboards.impl.LeaderboardItem;
import com.birthdates.leaderboards.module.LeaderboardModule;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.placeholder.PlaceholderReplacer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

public class HolographicDisplaysModule implements LeaderboardModule<LeaderboardItem> {

    private final String format;
    @Setter
    @Getter
    private String[] placeholderNames;
    private LeaderboardItem[] data;


    public HolographicDisplaysModule(Plugin owner, String[] placeholderNames, String format) {
        this(owner, placeholderNames, 300 /*5 minutes*/, format);
    }

    public HolographicDisplaysModule(Plugin owner, String[] placeholderNames, float updateTime, String format) {
        this.placeholderNames = placeholderNames;
        this.format = format;
        for (int i = 0; i < placeholderNames.length; i++) {
            String placeholderName = placeholderNames[i];
            HologramsAPI.registerPlaceholder(owner, placeholderName, updateTime, getReplacer(i));
        }

    }

    private PlaceholderReplacer getReplacer(int index) {
        return () -> {
            if (data.length <= index) return "N/A";
            LeaderboardItem item = data[index];
            return item.format(format, index);
        };
    }

    @Override
    public void handleUpdate(LeaderboardItem[] data) {
        this.data = data;
    }
}
