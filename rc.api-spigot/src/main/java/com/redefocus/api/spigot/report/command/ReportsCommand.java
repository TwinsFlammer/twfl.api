package com.redefocus.api.spigot.report.command;

import com.redefocus.api.spigot.commands.CustomCommand;
import com.redefocus.api.spigot.commands.enums.CommandRestriction;
import com.redefocus.api.spigot.report.inventory.ReportsInventory;
import com.redefocus.common.shared.permissions.group.GroupNames;
import com.redefocus.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public class ReportsCommand extends CustomCommand {
    public ReportsCommand() {
        super("reports", CommandRestriction.IN_GAME, GroupNames.MODERATOR);
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Player player = (Player) sender;

        player.openInventory(
                new ReportsInventory()
                        .build()
        );
    }
}
