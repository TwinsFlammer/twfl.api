package br.com.twinsflammer.api.shared.log.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Log {
    @Getter
    private final Integer userId;
    private final Boolean staff;
    @Getter
    private final Long time;
    @Getter
    private final String network;
    @Getter
    private final Integer serverId;
    @Getter
    private final LogType type;
    @Getter
    private final LogType subType;
    @Getter
    private final String value;

    public Boolean isStaff() {
        return this.staff;
    }

    @RequiredArgsConstructor
    public enum LogType {
        CHAT("_chat", true),
        COMMAND("_commands", true),
        ERROR("_errors", true),
        TEAM("_team", false),
        FACTION("_factions", false),
        TELL("_tell", false),
        GLOBAL("_global", false),
        LOCAL("_local", false),
        STAFF("_staff", false),
        PLACE("_place", false),
        BREAK("_break", false),
        SHOP("_shop", false),
        BUY("_buy", false),
        SELL("_sell", false),
        MARKET("_market", false),
        GENERATOR("_generators", false),
        KITS("_kits", true),
        BOXES("_boxes", true),
        BLOCKS("_blocks", true),
        DEATHS("_deaths", true),
        KILLS("_kills", true),
        DEFAULT("_default", false);

        @Getter
        private final String tableName;
        private final Boolean table;

        public Boolean isTable() {
            return this.table;
        }
    }
}
