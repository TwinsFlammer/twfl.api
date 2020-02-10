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
    private final Long startTime;
    @Getter
    private final Long restartTime;

    private final Long[] warnTimes = new Long[]{
            TimeUnit.SECONDS.toMillis(30),
            TimeUnit.SECONDS.toMillis(60),
            TimeUnit.SECONDS.toMillis(90)
    };

    private Integer warnTime = warnTimes.length - 1;

    private static ScheduledFuture<?> scheduledFuture;

    public void start() {
        Server server = SpigotAPI.getCurrentServer();

        assert server != null;

        server.setStatus(4);

        Restart.scheduledFuture = Common.getInstance().getScheduler().scheduleAtFixedRate(
                () -> {
                    try {
                        Long currentTime = System.currentTimeMillis(),
                                restartTime = this.startTime + this.restartTime;

                        if (currentTime >= restartTime) {
                            System.out.println("É pra cancelar essa porra aqui...");
                            this.cancel();

                            this.shutdown();
                        }

                        Long currentWarningTime = this.startTime + this.warnTimes[this.warnTime];

                        if (currentWarningTime <= restartTime) {
                            this.warnTime--;

                            this.warn();
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
        Bukkit.broadcastMessage(
                String.format(
                        "\n" +
                                "§e * O servidor está reiniciando automaticamente" +
                                "\n" +
                                "§e * Restam apenas %s para o servidor ser desligado." +
                                "\n\n",
                        TimeFormatter.format(this.warnTimes[this.warnTime+1])
                )
        );
    }
}
