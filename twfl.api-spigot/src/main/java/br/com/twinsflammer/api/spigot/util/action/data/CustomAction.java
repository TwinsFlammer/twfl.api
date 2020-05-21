package br.com.twinsflammer.api.spigot.util.action.data;

import br.com.twinsflammer.api.spigot.TwinsPlugin;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import br.com.twinsflammer.common.spigot.packet.wrapper.WrapperPlayServerChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by @SrGutyerrez
 */
public class CustomAction {

    private String text;

    private final Spigot spigot;

    public CustomAction() {
        this.spigot = new Spigot();
    }

    public CustomAction text(String text) {
        this.text = ChatColor.translateAlternateColorCodes('&', text);
        return this;
    }

    @Deprecated
    public Spigot getSpigot() {
        return this.spigot;
    }

    public Spigot spigot() {
        return this.spigot;
    }

    public class Spigot {
        private BukkitTask task;
        private int stayCounter;

        public Spigot send(Player... players) {
            WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromText(CustomAction.this.text);
            WrapperPlayServerChat wrapperPlayServerChat = new WrapperPlayServerChat();
            wrapperPlayServerChat.setMessage(wrappedChatComponent);
            wrapperPlayServerChat.setPosition((byte) 2);

            Arrays.asList(players)
                    .forEach(wrapperPlayServerChat::sendPacket);

            return this;
        }

        public Spigot send(Collection<? extends Player> players) {
            players.forEach(Spigot.this::send);

            return this;
        }

        public Spigot sendAndStay(TwinsPlugin plugin, int ticks, Collection<? extends Player> players) {
            Spigot.this.task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

                Spigot.this.stayCounter++;
                Spigot.this.send(players);

                if (Spigot.this.stayCounter >= ticks)
                    Spigot.this.task.cancel();
            }, 0L, 1L);
            return this;
        }

        public Spigot cancelStay() {
            Spigot.this.task.cancel();
            return this;
        }
    }
}
