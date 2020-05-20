package br.com.twinsflammer.api.spigot.spawn.command;

import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.api.spigot.spawn.storage.SpawnStorage;
import br.com.twinsflammer.api.spigot.spawn.manager.SpawnManager;
import br.com.twinsflammer.api.spigot.util.serialize.LocationSerialize;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public class SetSpawnCommand extends CustomCommand {
    public SetSpawnCommand() {
        super("setspawn", CommandRestriction.IN_GAME, GroupNames.MANAGER);
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        Player player = (Player) sender;

        Location location = player.getLocation();

        String serializedLocation = LocationSerialize.toString(location);

        SpawnStorage spawnStorage = new SpawnStorage();

        SpawnManager.DEFAULT_SPAWN = location;

        spawnStorage.update(
                "location",
                serializedLocation
        );

        sender.sendMessage(
                language.getMessage("spawn.changed")
        );
    }
}
