package br.com.twinsflammer.api.bungeecord.commands.defaults.disable;

import br.com.twinsflammer.api.bungeecord.commands.CustomCommand;
import br.com.twinsflammer.api.bungeecord.commands.enums.CommandRestriction;
import br.com.twinsflammer.api.shared.commands.defaults.disable.dao.DisabledCommandDao;
import br.com.twinsflammer.api.shared.commands.defaults.disable.data.DisabledCommand;
import br.com.twinsflammer.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;

/**
 * Created by @SrGutyerrez
 */
public class EnableCommand extends CustomCommand {
    public EnableCommand() {
        super("enable", CommandRestriction.ALL, GroupNames.MANAGER);
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
