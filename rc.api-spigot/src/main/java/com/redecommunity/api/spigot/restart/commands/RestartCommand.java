package com.redecommunity.api.spigot.restart.commands;

import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.api.spigot.restart.data.Restart;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.util.Helper;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class RestartCommand extends CustomCommand {
    public RestartCommand() {
        super("restart", CommandRestriction.ALL, GroupNames.MANAGER);
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        Server server = SpigotAPI.getCurrentServer();

        if (server.isRestarting()) {
            sender.sendMessage(
                    Helper.colorize(language.getMessage("restart.already_restarting"))
            );
            return;
        }

        Long time = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3);

        Restart restart = new Restart(time, 3);

        restart.start();

        SpigotAPI.getInstance().setRestart(restart);

        sender.sendMessage(
                Helper.colorize(language.getMessage("restart.restarting"))
        );
    }
}
