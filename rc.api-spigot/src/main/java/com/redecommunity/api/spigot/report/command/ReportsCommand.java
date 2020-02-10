package com.redecommunity.api.spigot.report.command;

import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
public class ReportsCommand extends CustomCommand {
    public ReportsCommand() {
        super("reports", CommandRestriction.IN_GAME, GroupNames.MODERATOR);
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {

    }
}
