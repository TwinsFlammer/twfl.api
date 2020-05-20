package br.com.twinsflammer.api.shared.commands.defaults.disable.manager;

import com.google.common.collect.Lists;
import br.com.twinsflammer.api.shared.commands.defaults.disable.dao.DisabledCommandDao;
import br.com.twinsflammer.api.shared.commands.defaults.disable.data.DisabledCommand;
import br.com.twinsflammer.common.shared.Common;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by @SrGutyerrez
 */
public class DisabledCommandManager {
    private static List<DisabledCommand> disabledCommands = Lists.newArrayList();

    public static final String CHANNEL_NAME = "disabled_command";

    public DisabledCommandManager() {
        DisabledCommandDao disabledCommandDao = new DisabledCommandDao();

        Set<DisabledCommand> disabledCommands = disabledCommandDao.findAll();

        DisabledCommandManager.disabledCommands.addAll(disabledCommands);
    }

    public static boolean setCommandDisabled(DisabledCommand disabledcommand) {
        return DisabledCommandManager.disabledCommands.add(disabledcommand);
    }

    public static Boolean isAlreadyDisabled(String name) {
        return DisabledCommandManager.disabledCommands
                .stream()
                .anyMatch(disabledCommand -> disabledCommand.getName().equalsIgnoreCase(name));
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

    public static void publish(DisabledCommand disabledCommand, Action action) {
        if (action == Action.ENABLE) DisabledCommandManager.disabledCommands.remove(disabledCommand);
        else DisabledCommandManager.disabledCommands.add(disabledCommand);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("action", action.getName());
        jsonObject.put("id", disabledCommand.getId());
        jsonObject.put("name", disabledCommand.getName());
        jsonObject.put("user_id", disabledCommand.getUserId());
        jsonObject.put("time", disabledCommand.getTime());

        Common.getInstance().getDatabaseManager().getRedisManager().getDatabases().values().forEach(redis -> redis.sendMessage(DisabledCommandManager.CHANNEL_NAME, jsonObject.toString()));
    }

    public static DisabledCommand toDisabledCommand(ResultSet resultSet) throws SQLException {
        return new DisabledCommand(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("name"),
                resultSet.getLong("time")
        );
    }

    @RequiredArgsConstructor
    @Getter
    public enum Action {
        ENABLE("enable"),
        DISABLE("disable");

        private final String name;
    }
}
