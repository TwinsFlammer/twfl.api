package br.com.twinsflammer.api.spigot.skin.command.argument;

import br.com.twinsflammer.api.spigot.commands.CustomArgumentCommand;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
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
