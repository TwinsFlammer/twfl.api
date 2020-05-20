package br.com.twinsflammer.api.spigot.report.command;

import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.api.spigot.report.inventory.ReportsInventory;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
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
