package com.birthdates.leaderboards.event;

import com.birthdates.leaderboards.Leaderboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LeaderboardUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Leaderboard<?> leaderboard;

    public LeaderboardUpdateEvent(Leaderboard<?> leaderboard) {
        super(!Bukkit.isPrimaryThread());
        this.leaderboard = leaderboard;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
