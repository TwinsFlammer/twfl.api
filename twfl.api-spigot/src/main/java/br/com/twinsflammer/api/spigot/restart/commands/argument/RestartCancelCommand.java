package br.com.twinsflammer.api.spigot.restart.commands.argument;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.commands.CustomArgumentCommand;
import br.com.twinsflammer.api.spigot.restart.data.Restart;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.server.data.Server;
import org.bukkit.command.CommandSender;

/**
 * Created by @SrGutyerrez
 */
public class RestartCancelCommand extends CustomArgumentCommand {
    public RestartCancelCommand() {
        super(0, "cancelar");
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        Restart restart = SpigotAPI.getInstance().getRestart();
        Server server = SpigotAPI.getCurrentServer();

        if (restart == null || !server.isRestarting()) {
            sender.sendMessage(
                    language.getMessage("restart.not_restarting")
            );
            return;
        }

        server.setStatus(SpigotAPI.getDefaultStatus());
        restart.cancel(true);

        sender.sendMessage(
                language.getMessage("restart.cancelled")
        );
    }
}
