package com.redefocus.api.spigot.restart.commands;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.commands.CustomCommand;
import com.redefocus.api.spigot.commands.enums.CommandRestriction;
import com.redefocus.api.spigot.restart.commands.argument.RestartCancelCommand;
import com.redefocus.api.spigot.restart.data.Restart;
import com.redefocus.common.shared.language.enums.Language;
import com.redefocus.common.shared.permissions.group.GroupNames;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.server.data.Server;
import com.redefocus.common.shared.util.Helper;
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
