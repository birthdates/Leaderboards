package com.birthdates.leaderboards.module;

import com.birthdates.leaderboards.impl.LeaderboardItem;

public interface LeaderboardModule<T extends LeaderboardItem> {

    void handleUpdate(T[] data);
}
