package com.redecommunity.api.spigot.skin.command;

import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.api.spigot.skin.inventory.SkinInventory;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by @SrGutyerrez
 */
public class SkinCommand extends CustomCommand {
    public SkinCommand() {
        super("skin", CommandRestriction.IN_GAME, "default");
    }

    @Override
    public void onCommand(User user, String[] args) {
        Inventory inventory = new SkinInventory(user);

        Player player = Bukkit.getPlayer(user.getUniqueId());

        player.openInventory(inventory);
    }
}
