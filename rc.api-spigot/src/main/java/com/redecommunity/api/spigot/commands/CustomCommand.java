package com.redecommunity.api.spigot.commands;

import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * Created by @SrGutyerrez
 */
@Getter
public abstract class CustomCommand extends Command {
    private final CommandRestriction commandRestriction;
    private final Group group;

    public CustomCommand(String name, CommandRestriction commandRestriction, String groupName, String... aliases) {
        super(name);

        this.commandRestriction = commandRestriction;
        this.group = GroupManager.getGroup(groupName);

        this.setAliases(Arrays.asList(aliases));
    }

    public void execute(CommandSender sender, String[] args) {
        if (this.commandRestriction != null && !this.commandRestriction.isValid(sender)) return;

        User user = UserManager.getUser(sender.getName());

        if (!user.hasGroup(this.group)) {
            Language language = user.getLanguage();

            String message = language.getMessage("messages.default_commands.invalid_group");

            user.sendMessage(message);
            return;
        }

        this.onCommand(user, args);
    }

    public abstract void onCommand(User user, String[] args);
}
