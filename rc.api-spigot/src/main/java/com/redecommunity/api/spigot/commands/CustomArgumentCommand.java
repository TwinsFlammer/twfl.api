package com.redecommunity.api.spigot.commands;

import com.redecommunity.common.shared.permissions.user.data.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public abstract class CustomArgumentCommand {
    private final Integer index;
    private final String name;

    public abstract void onCommand(CommandSender sender, User user, String[] args);
}
