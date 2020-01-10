package com.redecommunity.api.bungeecord.commands;

import com.google.common.collect.Lists;
import com.redecommunity.api.bungeecord.commands.enums.CommandRestriction;
import com.redecommunity.api.shared.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.api.shared.log.dao.LogDao;
import com.redecommunity.api.shared.log.data.Log;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.group.manager.GroupManager;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.util.Helper;
import lombok.Getter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
@Getter
public abstract class CustomCommand extends Command {
    private final CommandRestriction commandRestriction;
    private final Group group;
    private final String[] aliases;
    private final List<CustomArgumentCommand> arguments = Lists.newArrayList();

    private final String[] blacklisted = new String[]{
            "login",
            "logar",
            "register",
            "registrar",
            "changepassword",
            "mudarsenha",
    };

    public CustomCommand(String name, CommandRestriction commandRestriction, String groupName, String... aliases) {
        super(name);

        this.commandRestriction = commandRestriction;
        this.group = GroupManager.getGroup(groupName);
        this.aliases = aliases;
    }

    public void addArgument(CustomArgumentCommand... customArgumentCommands) {
        List<CustomArgumentCommand> customArgumentCommands1 = Arrays.asList(customArgumentCommands);

        this.arguments.addAll(customArgumentCommands1);
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

        if (!user.isConsole() && !Arrays.asList(this.blacklisted).contains(this.getName())) {
            Server server = user.getServer();

            Log log = new Log(
                    user.getId(),
                    user.getHighestGroup().getPriority() > 80,
                    System.currentTimeMillis(),
                    "SURVIVAL",
                    server.getId(),
                    Log.LogType.COMMAND,
                    Log.LogType.DEFAULT,
                    Helper.toMessage(args)
            );

            LogDao logDao = new LogDao();

            logDao.insert(log);
        }

        for (int i = 0; i < args.length; i++) {
            String argumentName = args[i];

            int finalI = i;

            List<CustomArgumentCommand> customArgumentCommands = this.arguments
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(customArgumentCommand1 -> customArgumentCommand1.getIndex() == finalI)
                    .collect(Collectors.toList());

            if (customArgumentCommands.isEmpty()) continue;

            for (CustomArgumentCommand customArgumentCommand : customArgumentCommands) {
                if (customArgumentCommand.getName().equalsIgnoreCase(argumentName)) {
                    customArgumentCommand.onCommand(user, Helper.removeFirst(args));
                    return;
                }
            }
        }

        this.onCommand(user, args);
    }

    public abstract void onCommand(User user, String[] args);
}
