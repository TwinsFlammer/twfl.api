package com.redefocus.api.spigot.skin.command.argument;

import com.redefocus.api.spigot.commands.CustomArgumentCommand;
import com.redefocus.common.shared.language.enums.Language;
import com.redefocus.common.shared.permissions.user.data.User;
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
