package br.com.twinsflammer.api.spigot.commands.defaults.tps.command;

import br.com.twinsflammer.api.spigot.commands.defaults.tps.manager.TPSManager;
import com.google.common.collect.Lists;
import br.com.twinsflammer.api.spigot.commands.CustomCommand;
import br.com.twinsflammer.api.spigot.commands.enums.CommandRestriction;
import br.com.twinsflammer.common.shared.permissions.group.GroupNames;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.spigot.util.CUtil;
import br.com.twinsflammer.common.spigot.util.Progressbar;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class TPSCommand extends CustomCommand {
    protected static Progressbar PROGRESS_BAR;

    static {
        PROGRESS_BAR = Progressbar.valueOf(0.75, 7, null, "{c}#", null, ChatColor.BLACK.toString() + "#", null, 1.0, "{c}", CUtil.map(1.0, "&a", 0.8, "&e", 0.7, "&6", 0.5, "&c"));
    }

    public TPSCommand() {
        super(
                "tps",
                CommandRestriction.ALL,
                GroupNames.MANAGER
        );
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        List<Integer> tpsHistory = Lists.newArrayList(TPSManager.TICKS);

        List<String> barChart = getBarChart(tpsHistory);

        sender.sendMessage("                  §eHistórico de TPS do servidor");
        sender.sendMessage(barChart.toArray(new String[0]));
    }

    public static double getQuota(int tps) {
        double ret = tps / 20.0;
        ret = Progressbar.limit(ret);
        return ret;
    }

    public static List<Double> getQuotas(Collection<Integer> tps) {
        List<Double> ret = Lists.newArrayList();
        for (Integer value : tps) {
            ret.add(getQuota(value));
        }
        return ret;
    }

    public static String formatTps(int tps) {
        return String.format("%02d", tps);
    }

    public static List<String> getBarChart(List<Integer> tpsHistory) {
        List<String> ret = Lists.newArrayList();
        List<Double> quotas = getQuotas(tpsHistory);
        List<List<String>> barPartsLines = Lists.newArrayList();

        for (Double quota : quotas) {
            barPartsLines.add(TPSCommand.PROGRESS_BAR.withQuota(quota).renderList());
        }

        barPartsLines = CUtil.rotateLeft(barPartsLines);

        for (List<String> barParts : barPartsLines) {
            StringBuilder line = new StringBuilder();
            for (String barPart : barParts) {
                line.append(barPart);
            }
            ret.add(line.toString());
        }

        List<List<String>> tpsPartLines = Lists.newArrayList();

        boolean even = true;

        for (Integer tps : tpsHistory) {
            if (tps > 20) tps = 20;

            even = !even;
            ChatColor color = even ? ChatColor.AQUA : ChatColor.DARK_AQUA;
            String tpsDesc = formatTps(tps);
            String one = color.toString() + tpsDesc.substring(0, 1);

            String two = color.toString() + tpsDesc.substring(1, 2);
            tpsPartLines.add(CUtil.list(one, two));
        }

        tpsPartLines = CUtil.rotateLeft(tpsPartLines);

        CUtil.flipVertically(tpsPartLines);

        for (List<String> parts : tpsPartLines) {
            StringBuilder line2 = new StringBuilder();
            for (String part : parts) {
                line2.append(part);
            }
            ret.add(line2.toString());
        }

        return ret;
    }
}
