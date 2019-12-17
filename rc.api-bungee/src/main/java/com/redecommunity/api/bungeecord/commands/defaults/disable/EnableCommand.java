package com.redecommunity.api.bungeecord.commands.defaults.disable;

import com.redecommunity.api.bungeecord.commands.CustomCommand;
import com.redecommunity.api.bungeecord.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.bungeecord.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.api.bungeecord.commands.enums.CommandRestriction;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;

/**
 * Created by @SrGutyerrez
 */
public class EnableCommand extends CustomCommand {
    public EnableCommand() {
        super("enable", CommandRestriction.ALL, "manager");
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

        DisabledCommand disabledCommand = DisabledCommandManager.getDisabledCommand(name);

        if (disabledCommand == null) {
            user.sendMessage(
                    String.format(
                            language.getMessage("messages.default_commands.command_not_disabled"),
                    )
            );
            return;
        }



    }
}
