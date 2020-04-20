package com.redefocus.api.spigot.commands.defaults.tps.runnable;

import com.redefocus.api.spigot.commands.defaults.tps.manager.TPSManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class TicksPerSecond implements Runnable {
    private long millisInSecond;
    private int tickCount;

    @Override
    public void run() {
        final long tick = System.currentTimeMillis();
        final long tickSecond = TimeUnit.MILLISECONDS.toSeconds(tick);

        if (this.millisInSecond == 0L) {
            this.millisInSecond = tickSecond;
            return;
        }

        ++this.tickCount;

        while (this.millisInSecond < tickSecond) {
            TPSManager.addTick(this.tickCount);
            this.tickCount = 0;
            ++this.millisInSecond;
        }
    }

    public TicksPerSecond() {
        this.millisInSecond = 0L;
        this.tickCount = 0;
    }
}
