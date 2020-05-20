package br.com.twinsflammer.api.spigot.commands.defaults;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.api.spigot.restart.data.Restart;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.server.data.Server;
import br.com.twinsflammer.common.shared.util.Helper;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class StopCommand extends CustomCommand {
    public StopCommand() {
        super(
                "stop",
                CommandRestriction.ALL,
                GroupNames.MANAGER
        );
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        Server server = SpigotAPI.getCurrentServer();

        assert server != null;

        if (server.isRestarting()) {
            sender.sendMessage(
                    Helper.colorize(language.getMessage("restart.already_restarting"))
            );
            return;
        }

        Long time = TimeUnit.SECONDS.toMillis(30);

        Restart restart = new Restart(System.currentTimeMillis(), time);

        restart.setWarnTime(1);
        restart.start();

        sender.sendMessage(
                Helper.colorize(language.getMessage("restart.started"))
        );
    }
}
