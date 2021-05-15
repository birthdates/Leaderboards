package com.birthdates.leaderboards.impl;

public interface LeaderboardItem {

    String getLeaderboardDisplayName();

    default String format(String str, int index) {
        return String.format(str, getLeaderboardDisplayName(), index);
    }
}
