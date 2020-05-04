package com.redefocus.api.spigot.spawn.command;

import com.redefocus.api.spigot.commands.CustomCommand;
import com.redefocus.api.spigot.commands.enums.CommandRestriction;
import com.redefocus.api.spigot.spawn.manager.SpawnManager;
import com.redefocus.api.spigot.spawn.storage.SpawnStorage;
import com.redefocus.api.spigot.util.serialize.LocationSerialize;
import com.redefocus.common.shared.language.enums.Language;
import com.redefocus.common.shared.permissions.group.GroupNames;
import com.redefocus.common.shared.permissions.user.data.User;
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
