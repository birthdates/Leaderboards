package com.birthdates.leaderboards;

import com.birthdates.leaderboards.impl.LeaderboardItem;
import org.apache.commons.lang.Validate;

public class LeaderboardTest {

    public LeaderboardTest() {
        TestItem[] data = new TestItem[10];

        //fill with random data
        for (int i = 0; i < data.length; i++) {
            data[i] = new TestItem(Math.round(Math.random() * 100f));
        }

        int leaderboardSize = 3;
        Leaderboard<TestItem> testLeaderboard = new Leaderboard<>(null, "Test", TestItem.class, leaderboardSize, () -> data);

        TestItem[] sortedData = testLeaderboard.getData();

        Validate.isTrue(sortedData.length == leaderboardSize, "Invalid data size");
        Validate.isTrue(isSorted(sortedData), "Data not sorted: " + leaderboardToString(testLeaderboard));

        String[] messages = testLeaderboard.getMessage();
        String message = messages[0];
        Validate.isTrue(message.length() > 7, "Invalid message: " + String.join("\n", messages));
    }

    private String leaderboardToString(Leaderboard<TestItem> testLeaderboard) {
        StringBuilder output = new StringBuilder();
        TestItem[] data = testLeaderboard.getData();
        for (TestItem datum : data) {
            output.append(datum.rank).append(" ");
        }
        return output.toString();
    }

    private boolean isSorted(TestItem[] arr) {
        for (int i = 1; i < arr.length; i++) {
            long current = arr[i].rank;
            long previous = arr[i - 1].rank;

            if (current <= previous) continue;
            return false;
        }
        return true;
    }

    private static class TestItem implements LeaderboardItem, Comparable<TestItem> {

        private final long rank;
        private final String name;

        public TestItem(long rank) {
            this.rank = rank;
            this.name = "Test-" + rank;
        }

        @Override
        public String getLeaderboardDisplayName() {
            return name;
        }

        @Override
        public int compareTo(TestItem item) {
            return Long.compare(item.rank, this.rank);
        }
    }
}
