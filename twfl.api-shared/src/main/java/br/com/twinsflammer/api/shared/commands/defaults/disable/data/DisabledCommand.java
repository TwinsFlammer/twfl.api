package br.com.twinsflammer.api.shared.commands.defaults.disable.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class DisabledCommand {
    private final Integer id, userId;
    private final String name;
    private final Long time;
}
