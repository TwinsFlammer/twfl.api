package com.redecommunity.api.bungeecord.commands;

import com.redecommunity.api.bungeecord.commands.enums.CommandRestriction;
import com.redecommunity.api.shared.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by @SrGutyerrez
 */
@Getter
public abstract class CustomCommand extends Command {
    private final CommandRestriction commandRestriction;
    private final Group group;
    private final String[] aliases;

    public CustomCommand(String name, CommandRestriction commandRestriction, String groupName, String... aliases) {
        super(name);

        this.commandRestriction = commandRestriction;
        this.group = GroupManager.getGroup(groupName);
        this.aliases = aliases;
    }

    @Override
    public final void execute(CommandSender sender, String[] args) {
        if (this.commandRestriction != null && !this.commandRestriction.isValid(sender)) return;

        User user = UserManager.getUser(sender.getName());

        Language language = user.getLanguage();

        if (!user.hasGroup(this.group)) {
            user.sendMessage(
                    String.format(
                            language.getMessage("messages.default_commands.invalid_group"),
                            this.group.getColor() + this.group.getName()
                    )
            );
            return;
        }

        String name = this.getName();

        DisabledCommand disabledCommand = DisabledCommandManager.getDisabledCommand(name);

        if (disabledCommand != null) {
            user.sendMessage(
                    language.getMessage("messages.default_commands.command_disabled")
            );
            return;
        }

        this.onCommand(user, args);
    }

    public abstract void onCommand(User user, String[] args);
}
