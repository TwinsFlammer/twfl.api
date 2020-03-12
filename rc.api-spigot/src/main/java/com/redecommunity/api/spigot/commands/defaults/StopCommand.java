package com.redecommunity.api.spigot.commands.defaults;

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
