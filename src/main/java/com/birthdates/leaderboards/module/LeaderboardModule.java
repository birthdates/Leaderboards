package com.birthdates.leaderboards.module;

public interface LeaderboardModule<E> {

    void handleUpdate(E[] data);

}
