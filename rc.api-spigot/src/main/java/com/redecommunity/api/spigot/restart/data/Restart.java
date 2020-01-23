package com.redecommunity.api.spigot.restart.data;

import com.google.common.collect.Queues;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Restart {
    @Getter
    private final Long restartTime;

    private static ScheduledFuture<?> scheduledFuture;

    public void start() {
        Server server = SpigotAPI.getCurrentServer();

        server.setStatus(4);

        Restart.scheduledFuture = Common.getInstance().getScheduler().scheduleAtFixedRate(
                () -> {
                    if (System.currentTimeMillis() >= this.restartTime) {
                        this.cancel();

                        this.shutdown();
                    }
                },
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    public void cancel() {
        Server server = SpigotAPI.getCurrentServer();

        Integer defaultStatus = SpigotAPI.getDefaultStatus();

        server.setStatus(defaultStatus);

        ScheduledFuture<?> scheduledFuture = Restart.scheduledFuture;

        scheduledFuture.cancel(true);
    }

    private void shutdown() {
        Queue<Player> players = Queues.newLinkedBlockingDeque(Bukkit.getOnlinePlayers());

        Common.getInstance().getScheduler().scheduleAtFixedRate(
                () -> {
                    if (players.isEmpty()) {
                        Bukkit.getServer().shutdown();
                        return;
                    }

                    Player player = players.poll();

                    User user = UserManager.getUser(player.getUniqueId());

                    Language language = user.getLanguage();

                    player.kickPlayer(
                            language.getMessage("restart.kick_message")
                    );
                },
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    private void broadcast() {

    }
}
