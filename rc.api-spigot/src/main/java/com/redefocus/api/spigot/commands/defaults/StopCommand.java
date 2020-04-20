package com.redefocus.api.spigot.commands.defaults;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.commands.CustomCommand;
import com.redefocus.api.spigot.commands.enums.CommandRestriction;
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
