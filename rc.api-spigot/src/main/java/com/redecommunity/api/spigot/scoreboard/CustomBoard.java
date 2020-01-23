package com.redecommunity.api.spigot.scoreboard;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.redecommunity.common.shared.util.Helper;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by @SrGutyerrez
 */
public class CustomBoard {

    private String title;

    private final TreeMap<Integer, Team> teams = Maps.newTreeMap();
    private final Map<Integer, String> entries = Maps.newHashMap();

    @Getter
    protected Scoreboard scoreboard;

    protected Objective objective;

    public CustomBoard() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("customboard", "dummy");

        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public String title() {
        return this.title;
    }

    public CustomBoard title(String title) {
        this.title = Helper.colorize(title);

        this.objective.setDisplayName(this.title);

        return this;
    }

    public CustomBoard reset(Integer score) {
        if (this.entries.get(score) == null) return this;

        Team team = this.teams.remove(score);

        if (team != null) {
            this.scoreboard.resetScores(this.entries.remove(score));

            team.unregister();
        }

        return this;
    }

    public CustomBoard clear() {
        this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        this.teams.values().forEach((Team t) -> t.unregister());

        this.entries.clear();
        this.teams.clear();

        return this;
    }

    public CustomBoard add(String text) {
        return this.teams.isEmpty() ? set(1, text) : set(this.teams.lastKey() + 1, text);
    }

    public CustomBoard set(Integer score, String text) {
        text = Helper.colorize(text);

        Iterator<String> iterator = Splitter.fixedLength(16).split(text).iterator();
        StringBuilder prefixBuilder = new StringBuilder(iterator.next());
        StringBuilder suffixBuilder = iterator.hasNext() ? new StringBuilder(iterator.next()) : new StringBuilder();

        int index = prefixBuilder.length() - 1;
        if (prefixBuilder.charAt(index) == ChatColor.COLOR_CHAR) {
            prefixBuilder.deleteCharAt(index);
            suffixBuilder.insert(0, ChatColor.COLOR_CHAR);
        }

        suffixBuilder.insert(0, ChatColor.getLastColors(prefixBuilder.toString()));

        String prefix = prefixBuilder.toString();
        String suffix = suffixBuilder.toString();

        Team team = this.teams.get(score);

        Boolean fresh = team == null;

        String entry = (fresh ? hash(score) : this.entries.get(score));

        if (fresh) {
            team = this.scoreboard.getTeam(String.format("%d-t", score));
            if (team == null) {
                team = this.scoreboard.registerNewTeam(String.format("%d-t", score));
            }

            team.addEntry(entry);

            this.teams.put(score, team);
            this.entries.put(score, entry);
        }

        if (!Objects.equals(team.getPrefix(), prefix)) team.setPrefix(prefix);

        if (!Objects.equals(team.getSuffix(), suffix)) team.setSuffix(StringUtils.substring(suffix, 0, 15));

        if (fresh) this.objective.getScore(entry).setScore(score);

        return this;
    }

    public String get(int score) {
        return this.entries.get(score);
    }

    public boolean exists(int score) {
        return this.entries.containsKey(score);
    }

    private String hash(int score) {
        StringBuilder builder = new StringBuilder();

        for (char character : Integer.toHexString(score).toCharArray()) {
            ChatColor color = ChatColor.getByChar(character);

            if (color != null) builder.append(color.toString());
        }

        return builder.toString();
    }

    public void send(Player... players) {
        for (Player player : players) player.setScoreboard(this.scoreboard);
    }

    public void send(Collection<Player> players) {
        players.forEach(player -> player.setScoreboard(this.scoreboard));
    }
}
