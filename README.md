# Leaderboards
A module-based leaderboard system for Spigot

# Entries
Each entry in the leaderboard must be of type `LeaderboardItem` & `Comparable` to assist in sorting & formatting

# Modules
I've currently implemented some modules including:
* A sign module to update signs with a format (`SignModule`)
* A HolographicDisplays module to have a hologram placeholder update with a format (`HolographicDisplaysModule`)

To make your own module you can simply implement `LeaderboardModule`

To register one of these modules, use `Leaderboard.registerModule` or `Leaderboard.registerModules`

# Events
* `LeaderboardUpdateEvent` called on leader board update & provides type `Leaderboard`

# Maven
```xml
<dependency>
    <groupId>com.birthdates</groupId>
    <artifactId>leaderboards</artifactId>
    <version>1.0.7</version>
    <scope>compile</scope>
</dependency>
```

# Example
For a code example see [here](https://github.com/birthdates/Leaderboards/blob/master/src/test/java/com/birthdates/leaderboards/LeaderboardTest.java)