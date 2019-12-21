package com.redecommunity.api.bungeecord.commands;

import com.redecommunity.common.shared.permissions.user.data.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public abstract class CustomArgumentCommand<T> {
    private final String name;

    public abstract void onCommand(User user, String[] args);
}
