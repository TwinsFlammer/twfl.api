package com.redecommunity.api.bungeecord.commands.defaults.disable.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.bungeecord.commands.defaults.disable.data.DisabledCommand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class DisabledCommandManager {
    private static List<DisabledCommand> disabledCommands = Lists.newArrayList();

    public static final String CHANNEL_NAME = "disabled_command";

    public static List<DisabledCommand> getDisabledCommands() {
        return DisabledCommandManager.disabledCommands;
    }

    public static DisabledCommand getDisabledCommand(String name) {
        return DisabledCommandManager.disabledCommands
                .stream()
                .filter(disabledCommand -> disabledCommand.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static void removeDisabledCommand(String name) {
        DisabledCommandManager.disabledCommands.removeIf(disabledCommand -> disabledCommand.getName().equalsIgnoreCase(name));
    }

    public static DisabledCommand toDisabledCommand(ResultSet resultSet) throws SQLException {
        return new DisabledCommand(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("name"),
                resultSet.getLong("time")
        );
    }
}
