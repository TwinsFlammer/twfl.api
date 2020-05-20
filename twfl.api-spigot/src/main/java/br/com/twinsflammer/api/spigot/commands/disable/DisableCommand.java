package br.com.twinsflammer.api.spigot.commands.disable;

import br.com.twinsflammer.api.shared.commands.defaults.disable.dao.DisabledCommandDao;
import br.com.twinsflammer.api.shared.commands.defaults.disable.data.DisabledCommand;
import br.com.twinsflammer.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;

public class DisableCommand extends CustomCommand {
    public DisableCommand() {
        super("disable", CommandRestriction.ALL, GroupNames.MANAGER);
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        if (args.length != 1) {
            sender.sendMessage(
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
            sender.sendMessage(
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

        sender.sendMessage(
                language.getMessage("messages.default_commands.command_successfully_disabled")
        );
    }
}