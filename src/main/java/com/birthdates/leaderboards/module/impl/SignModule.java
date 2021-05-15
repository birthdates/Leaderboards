package com.birthdates.leaderboards.module.impl;

import com.birthdates.leaderboards.module.LeaderboardModule;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class SignModule implements LeaderboardModule<Object> {

    @Getter
    @Setter
    private Location[] locations;
    private final String[] lines;

    public SignModule(Location[] locations, String format) {
        this.locations = locations;
        lines = format.split("\n");
    }

    @Override
    public void handleUpdate(Object[] data) {
        for (int i = 0; i < data.length && i < locations.length; i++) {
            Object obj = data[i];
            Location location = locations[i];
            BlockState blockState = location.getBlock().getState();

            if(!(blockState instanceof Sign)) {
                System.out.println("WARNING: SignModule (" + location.toString() + ") is not a sign!");
                continue;
            }

            Sign sign = (Sign) blockState;
            for (int j = 0; j < lines.length; j++) {
                String line = lines[j];
                sign.setLine(j, String.format(line, obj.toString()));
            }
        }
    }
}
