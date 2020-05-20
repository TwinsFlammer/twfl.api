package br.com.twinsflammer.api.spigot.restart.data;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.restart.channel.RestartChannel;
import com.google.common.collect.Queues;
import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.server.data.Server;
import br.com.twinsflammer.common.shared.util.TimeFormatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    private final Long[] warnTimes = new Long[] {
            TimeUnit.SECONDS.toMillis(10),
            TimeUnit.SECONDS.toMillis(30),
            TimeUnit.SECONDS.toMillis(60),
            TimeUnit.SECONDS.toMillis(90),
            TimeUnit.SECONDS.toMillis(120),
            TimeUnit.SECONDS.toMillis(180)
    };

    @Setter
    private Integer warnTime = warnTimes.length - 1;

    private static ScheduledFuture<?> scheduledFuture;

    public void start() {
        SpigotAPI.getInstance().setRestart(this);

        Server server = SpigotAPI.getCurrentServer();

        assert server != null;

        server.setStatus(4);

        Restart.scheduledFuture = Common.getInstance().getScheduler().scheduleAtFixedRate(
                () -> {
                    try {
                        Long currentTime = System.currentTimeMillis(),
                                restartTime = this.startTime + this.restartTime;

                        if (currentTime >= restartTime) {
                            this.cancel(false);

                            this.shutdown();
                        }

                        if (this.warnTime <= -1) return;

                        Long currentWarningTime = currentTime + this.warnTimes[this.warnTime];

                        if (restartTime <= currentWarningTime) {
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

    public void cancel(Boolean force) {
        Server server = SpigotAPI.getCurrentServer();

        Integer defaultStatus = SpigotAPI.getDefaultStatus();

        server.setStatus(defaultStatus);

        ScheduledFuture<?> scheduledFuture = Restart.scheduledFuture;

        scheduledFuture.cancel(true);

        if (force) Bukkit.broadcastMessage(
                    "\n" +
                            "§c * O reinício automatico foi cancelado." +
                            "\n" +
                            "§c * Um membro da equipe cancelou o reinício." +
                            "\n\n§r "
            );
    }

    private void shutdown() {
        Bukkit.broadcastMessage(
                "\n" +
                "§e * O servidor será reiniciado automaticamente." +
                "\n" +
                "§e * Você será desconectado em breve para proseguirmos a reinicialização." +
                "\n\n§r "
        );

        Queue<Player> players = Queues.newLinkedBlockingDeque(Bukkit.getOnlinePlayers());

        Bukkit.getScheduler().runTaskTimer(
                SpigotAPI.getInstance(),
                () -> {
                    try {
                        if (players.isEmpty()) {
                            JSONObject jsonObject = SpigotAPI.getConfiguration();

                            JSONArray jsonArray = (JSONArray) jsonObject.get("servers");

                            RestartChannel restartChannel = new RestartChannel();

                            restartChannel.sendMessage(jsonArray.toString());

                            Bukkit.getServer().shutdown();
                            return;
                        }

                        Player player = players.poll();

                        User user = UserManager.getUser(player.getUniqueId());

                        Language language = user.getLanguage();

                        player.kickPlayer(
                                language.getMessage("restart.kick_message")
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                0,
                20L
        );
    }

    private void warn() {
        Bukkit.broadcastMessage(
                String.format(
                        "\n" +
                        "§e * O servidor está reiniciando automaticamente" +
                        "\n" +
                        "§e * Restam apenas %s para o servidor ser desligado." +
                        "\n\n§r ",
                        TimeFormatter.format(this.warnTimes[this.warnTime+1])
                )
        );
    }
}
