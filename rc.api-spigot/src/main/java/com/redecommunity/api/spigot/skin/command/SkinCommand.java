package com.redecommunity.api.spigot.skin.command;

import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.api.spigot.skin.command.argument.SkinHelpCommand;
import com.redecommunity.api.spigot.skin.command.argument.SkinRefreshCommand;
import com.redecommunity.api.spigot.skin.inventory.SkinInventory;
import com.redecommunity.api.spigot.skin.manager.SkinManager;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by @SrGutyerrez
 */
public class SkinCommand extends CustomCommand {
    public SkinCommand() {
        super("skin", CommandRestriction.IN_GAME, "default");

        this.addArgument(
                new SkinRefreshCommand(),
                new SkinHelpCommand()
        );
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        Player player = (Player) sender;

        if (args.length == 0) {
            Inventory inventory = new SkinInventory(user);

            player.openInventory(inventory);
            return;
        } else if (args.length == 1) {
            String skinName = args[0];

            new Thread(() -> {
                SkinManager.change(
                        player,
                        user,
                        skinName
                );
            }).start();
            return;
        } else {
            sender.sendMessage(
                    String.format(
                            language.getMessage("messages.default_commands.invalid_usage"),
                            this.getName(),
                            "<nome>"
                    )
            );
            return;
        }
    }
}
