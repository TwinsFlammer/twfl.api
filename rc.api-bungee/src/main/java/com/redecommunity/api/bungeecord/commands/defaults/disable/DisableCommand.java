package com.redecommunity.api.bungeecord.commands.defaults.disable;

import com.redecommunity.api.bungeecord.commands.CustomCommand;
import com.redecommunity.api.bungeecord.commands.enums.CommandRestriction;
import com.redecommunity.api.shared.commands.defaults.disable.dao.DisabledCommandDao;
import com.redecommunity.api.shared.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.user.data.User;

public class DisableCommand extends CustomCommand {
    public DisableCommand() {
        super("disable", CommandRestriction.ALL, GroupNames.MANAGER);
    }

    @Override
    public void onCommand(User user, String[] args) {
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

        if (DisabledCommandManager.isAlreadyDisabled(name)) {
            user.sendMessage(
                    language.getMessage("messages.default_commands.command_already_disabled")
            );
            return;
        }

        DisabledCommand disabledCommand = new DisabledCommand(
                0,
                user.getId(),
                name,
                System.currentTimeMillis()
        );

        DisabledCommandDao disabledCommandDao = new DisabledCommandDao();

        disabledCommandDao.insert(disabledCommand);

        user.sendMessage(
                language.getMessage("messages.default_commands.command_successfully_disabled")
        );
    }
}