package com.redefocus.api.spigot.commands;

import com.redefocus.common.shared.permissions.user.data.User;
import lombok.Getter;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
public abstract class CustomArgumentCommand<C extends CustomCommand> {
    @Getter
    private final Integer index;
    @Getter
    private final String name, description;

    private C command;

    public CustomArgumentCommand(Integer index, String name) {
        this.index = index;
        this.name = name;
        this.description = "";
    }

    public CustomArgumentCommand(Integer index, String name, String description) {
        this.index = index;
        this.name = name;
        this.description = description;
    }

    public CustomArgumentCommand(Integer index, String name, C command) {
        this.index = index;
        this.name = name;
        this.description = "";

        this.command = command;
    }

    public CustomArgumentCommand(Integer index, String name, String description, C command) {
        this.index = index;
        this.name = name;
        this.description = description;

        this.command = command;
    }


    public abstract void onCommand(CommandSender sender, User user, String[] args);

    public String getUsage() {
        return this.command == null ? "" : String.format("§cUtilize /%s %s.", this.command.getName(), this.name);
    }

    public String getUsage(String arguments) {
        return this.command == null ? "" : String.format("§cUtilize /%s %s <%s>.", this.command.getName(), this.name, arguments);
    }
}
