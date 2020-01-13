package com.redecommunity.api.spigot.commands.disable;

import com.redecommunity.api.shared.commands.defaults.disable.dao.DisabledCommandDao;
import com.redecommunity.api.shared.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
public class EnableCommand extends CustomCommand {
    public EnableCommand() {
        super("enable", CommandRestriction.ALL, "manager");
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        if (args.length != 1) {
            user.sendMessage(
                    String.format(
                            language.getMessage("messages.default_commands.invalid_usage"),
                            this.getName(),
                            "<name>"
                    )
            );
            return;
        }

        String name = args[0];

        DisabledCommand disabledCommand = DisabledCommandManager.getDisabledCommand(name);

        if (disabledCommand == null) {
            user.sendMessage(
                    language.getMessage("messages.default_commands.command_not_disabled")
            );
            return;
        }

        DisabledCommandDao disabledCommandDao = new DisabledCommandDao();

        disabledCommandDao.delete("id", disabledCommand.getId());

        DisabledCommandManager.publish(
                disabledCommand,
                DisabledCommandManager.Action.ENABLE
        );

        user.sendMessage(
                language.getMessage("messages.default_commands.command_successfully_enabled")
        );
    }
}
