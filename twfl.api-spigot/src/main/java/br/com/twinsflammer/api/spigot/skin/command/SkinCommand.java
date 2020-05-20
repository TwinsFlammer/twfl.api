package br.com.twinsflammer.api.spigot.skin.command;

import br.com.twinsflammer.api.spigot.skin.command.argument.SkinHelpCommand;
import br.com.twinsflammer.api.spigot.skin.inventory.SkinInventory;
import br.com.twinsflammer.api.spigot.skin.manager.SkinManager;
import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.api.spigot.skin.command.argument.SkinRefreshCommand;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by @SrGutyerrez
 */
public class SkinCommand extends CustomCommand {
    public SkinCommand() {
        super("skin", CommandRestriction.IN_GAME, GroupNames.DEFAULT);

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
            Inventory inventory = new SkinInventory(user).build();

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
