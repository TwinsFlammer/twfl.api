package com.redecommunity.api.spigot.restart.data;

import com.google.common.collect.Queues;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.util.TimeFormatter;
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

    private final Integer maxWarnings;

    private Integer currentWarning;
    private Long[] warnTimes;

    private static ScheduledFuture<?> scheduledFuture;

    public void start() {
        Server server = SpigotAPI.getCurrentServer();

        server.setStatus(4);

        this.currentWarning = this.maxWarnings;

        this.warnTimes = new Long[maxWarnings];

        Long startTime = Restart.this.restartTime - System.currentTimeMillis();

        for (int i = 0; i < warnTimes.length; i++) {
            warnTimes[i] = startTime / currentWarning + 3;

            currentWarning--;
        }

        Restart.scheduledFuture = Common.getInstance().getScheduler().scheduleAtFixedRate(
                () -> {
                    try {
                        if (System.currentTimeMillis() >= this.restartTime) {
                            System.out.println("É pra cancelar essa porra aqui...");
                            this.cancel();

                            this.shutdown();
                        }
                        for (Long time : this.warnTimes) {
                            System.out.println(time + " => " + TimeFormatter.format(time));
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                },
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    public void cancel() {
        System.out.println("cancelar...");
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

    private void warn() {

    }
}
