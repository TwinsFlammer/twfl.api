package br.com.twinsflammer.api.bungeecord.commands;

import br.com.twinsflammer.common.shared.permissions.user.data.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public abstract class CustomArgumentCommand {
    private final Integer index;
    private final String name;

    public abstract void onCommand(User user, String[] args);
}
