package com.redefocus.api.spigot.preference.command;

import com.redefocus.api.spigot.commands.CustomCommand;
import com.redefocus.api.spigot.commands.enums.CommandRestriction;
import com.redefocus.api.spigot.preference.inventory.ToggleInventory;
import com.redefocus.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public class ToggleCommand extends CustomCommand {
    public ToggleCommand() {
        super("toggle", CommandRestriction.IN_GAME, "default");
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Player player = (Player) sender;

        player.openInventory(
                new ToggleInventory(user, 1)
                        .build()
        );
    }
}
