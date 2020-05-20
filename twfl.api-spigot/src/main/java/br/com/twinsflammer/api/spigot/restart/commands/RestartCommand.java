package br.com.twinsflammer.api.spigot.restart.commands;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.api.spigot.restart.commands.argument.RestartCancelCommand;
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
public class RestartCommand extends CustomCommand {
    public RestartCommand() {
        super("restart", CommandRestriction.ALL, GroupNames.MANAGER);

        this.addArgument(
                new RestartCancelCommand()
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

        Long time = TimeUnit.MINUTES.toMillis(3);

        Restart restart = new Restart(System.currentTimeMillis(), time);

        restart.start();

        sender.sendMessage(
                Helper.colorize(language.getMessage("restart.started"))
        );
    }
}
