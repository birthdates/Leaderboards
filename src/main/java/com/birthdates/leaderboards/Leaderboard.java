package com.birthdates.leaderboards;

import com.birthdates.leaderboards.event.LeaderboardUpdateEvent;
import com.birthdates.leaderboards.impl.LeaderboardItem;
import com.birthdates.leaderboards.module.LeaderboardModule;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Leaderboard<T extends LeaderboardItem & Comparable<T>> {

    @Getter
    private final T[] data;
    @Getter
    private final String name;
    private final List<LeaderboardModule<T>> modules = new ArrayList<>();
    private final Plugin owner;
    private final Callable<T[]> allDataCallable;
    private final LeaderboardUpdateEvent leaderboardUpdateEvent;
    private int taskID;

    public Leaderboard(Plugin owner, String name, Class<T> clazz, int capacity, Callable<T[]> callable) {
        //noinspection unchecked
        data = (T[]) Array.newInstance(clazz, capacity);
        allDataCallable = callable;
        this.name = name;
        this.owner = owner;
        leaderboardUpdateEvent = owner != null ? new LeaderboardUpdateEvent(this) : null;
        update();
    }

    public void stopUpdating() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void startUpdating(float interval) {
        startUpdating(interval, true);
    }

    /**
     * Start updating the leaderboard automatically
     *
     * @param interval Time between updates (milliseconds)
     * @param async    Async?
     */
    public void startUpdating(float interval, boolean async) {
        long timeInTicks = Math.round(interval * 20f);
        if (!async) {
            this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(owner, this::update, timeInTicks, timeInTicks);
            return;
        }
        this.taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(owner, this::update, timeInTicks, timeInTicks).getTaskId();
    }

    public String[] getMessage() {
        return getMessage("%s - %d");
    }

    public String[] getMessage(String format) {
        return getMessage(format, 1);
    }

    public String[] getMessage(String format, int spacing) {
        int dataCount = data.length;
        String[] output = new String[dataCount + (spacing * dataCount)]; //make a list of size the leaderboard & for each space
        int count = 0;

        for (T data : this.data) {
            int next = count + 1;
            output[count] = data.format(format, next);
            count = addSpacing(output, next, spacing);
        }
        return output;
    }

    private int addSpacing(String[] arr, int index, int spacing) {
        int max = index + spacing;
        for (int i = index; i < max; i++) {
            arr[i] = ""; //make empty instead of null
        }
        return max; //return next index
    }

    public void registerModule(LeaderboardModule<T> module) {
        this.modules.add(module);
    }

    @SafeVarargs
    public final void registerModules(LeaderboardModule<T>... modules) {
        this.modules.addAll(Arrays.asList(modules));
    }

    public void update() {
        T[] allData = getAllData();
        if (allData == null) return;
        updateData(allData);
    }

    private T[] getAllData() {
        T[] data = null;
        try {
            data = allDataCallable.call();
        } catch (Exception exception) {
            System.out.println("Failed to receive all data for leaderboard \"" + name + "\"");
            exception.printStackTrace();
        }
        return data;
    }

    private void updateData(T[] allData) {
        Stream<T> sortedData = Arrays.stream(allData).sorted().limit(data.length); //sort the list & limit it to the leaderboard capacity
        AtomicInteger count = new AtomicInteger(0);
        sortedData.forEach((data) -> this.data[count.getAndIncrement()] = data);
        updateModules();
        updateEvent();
    }

    private void updateModules() {
        for (LeaderboardModule<T> module : modules) {
            module.handleUpdate(data);
        }
    }

    private void updateEvent() {
        if (leaderboardUpdateEvent != null)
            Bukkit.getPluginManager().callEvent(leaderboardUpdateEvent);
    }
}
