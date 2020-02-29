package com.redecommunity.api.spigot.skin.command.argument;

import com.redecommunity.api.spigot.commands.CustomArgumentCommand;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
public class SkinHelpCommand extends CustomArgumentCommand {
    public SkinHelpCommand() {
        super(0, "ajuda");
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        sender.sendMessage(
                language.getMessage("skin.help")
        );
    }
}
